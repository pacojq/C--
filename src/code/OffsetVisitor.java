package code;

import java.util.List;

import syntax.Statement;
import syntax.statements.FunctionDefinition;
import syntax.statements.VariableDefinition;
import syntax.types.FunctionType;
import syntax.types.StructType;
import visitor.AbstractVisitor;


public class OffsetVisitor extends AbstractVisitor<Void, Void> {

	private int globalOffset;
	
	
	public OffsetVisitor() {
		this.globalOffset = 0;
	}
	
		
	@Override
	public Void visit(StructType structType, Void params) {
		//super.visit(structType, params);
		int sum = 0;
		
		for (VariableDefinition vd : structType.getVariables()) {
			vd.accept(this, params);
			vd.setOffset(sum);
			sum += vd.getType().numberOfBytes();
		}
		return null;
	}
	
	
	
	@Override
	public Void visit(VariableDefinition variableDefinition, Void params) {
		super.visit(variableDefinition, params);
		
		// Global variable
		if (variableDefinition.getScope() == 0) {
			variableDefinition.setOffset(globalOffset);
			globalOffset += variableDefinition.getType().numberOfBytes();
			System.out.println(
					String.format("Global variable %s with offset %s", variableDefinition.getName(), variableDefinition.getOffset())
				);
		}
		
		return null;
	}
	
	
	
	@Override
	public Void visit(FunctionDefinition functionDefinition, Void params) {
		
		functionDefinition.getType().accept(this, params);
		
		int localBytesSum = 0;
		
		for (Statement st : functionDefinition.getStatements()) {
			st.accept(this, params);
			
			if (st instanceof VariableDefinition) {
				VariableDefinition vd = (VariableDefinition) st;
				localBytesSum += vd.getType().numberOfBytes();
				vd.setOffset(-localBytesSum);
				functionDefinition.localBytesSum = localBytesSum;
			}
		}
		
		return null;
	}
	
	
	@Override
	public Void visit(FunctionType functionType, Void params) {
		//super.visit(functionType, params);
		
		List<VariableDefinition> p = functionType.getParams();
		
		int localParamsSum = 4;
		for (int i = p.size()-1; i >= 0; i --) {
			VariableDefinition vd = p.get(i);
			vd.accept(this, params);
			vd.setOffset(localParamsSum);
			localParamsSum += vd.getType().numberOfBytes();
		}
		
		return null;
	}
	
	
	
	
}
