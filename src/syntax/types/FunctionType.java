package syntax.types;

import java.util.ArrayList;
import java.util.List;

import semantics.util.Visitor;
import syntax.Type;
import syntax.statements.VariableDefinition;

public class FunctionType extends AbstractType {

	private Type returnType;
	private List<VariableDefinition> params;
	
	
	public FunctionType(Type returnType) {
		super(returnType.getLine(), returnType.getColumn());
		this.returnType = returnType;
		this.params = new ArrayList<VariableDefinition>();
	}
	
	public FunctionType(Type returnType, List<VariableDefinition> params) {
		this(returnType);
		this.params.addAll(params);
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	public List<VariableDefinition> getParams() {
		return new ArrayList<VariableDefinition>(params);
	}
	

	
	@Override
	public String getName() {
		return "FunctionType";
	}
	
	@Override
	public String toString() {
		
		String strParams = "";
		if (params.size() > 0) {
			strParams += params.get(0).toString();
			for (int i = 1; i < params.size(); i ++)
				strParams += (", " + params.get(i).toString());
		}
			
		return String.format("FunctionType: returnType=[ %s ] params=[ %s ]", 
				returnType, strParams);
	}
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
