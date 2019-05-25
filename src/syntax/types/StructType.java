package syntax.types;

import java.util.ArrayList;
import java.util.List;

import errors.ErrorHandler;
import syntax.Type;
import syntax.statements.VariableDefinition;
import visitor.Visitor;


public class StructType extends AbstractType {

	public final static String NAME = "StructType";
	
	private List<VariableDefinition> variables;
	
	public StructType(int line, int column, List<VariableDefinition> variables) {
		super(line, column);
		this.variables = new ArrayList<VariableDefinition>(variables);
	}
	
	public List<VariableDefinition> getVariables() {
		return new ArrayList<VariableDefinition>(variables);
	}
	
	
	public VariableDefinition findVariable(String name) {
		for (VariableDefinition vd : variables)
			if (vd.getName().equals(name))
				return vd;
		return null;
	}
	
	
	@Override
	public Type attributeAccess(String attribute) {
		
		VariableDefinition def = findVariable(attribute);
		if (def == null) {
			return ErrorHandler.getInstance().raiseError(
					getLine(), 
					getColumn(), 
					String.format("Trying to acces an unexistent struct field: %s .", attribute));
		}		
		return def.getType();
	}	
	
	
	@Override
	public String getName() {
		return NAME;
	}
	
	
	@Override
	public boolean isEquivalent(Type other) {
		return this.toString().equals(other.toString());
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		if (variables.size() > 0)
			str.append(variables.get(0).toString());
		
		for (int i = 1; i < variables.size(); i ++)
			str.append(", " + variables.get(i).toString());
		
		return String.format("struct{ %s }", str.toString());
	}
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}

	@Override
	public int numberOfBytes() {
		int sum = 0;
		for (VariableDefinition vd : this.variables)
			sum += vd.getType().numberOfBytes();
		return sum;
	}
}
