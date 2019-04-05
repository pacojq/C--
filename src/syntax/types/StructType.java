package syntax.types;

import java.util.ArrayList;
import java.util.List;

import errors.ErrorHandler;
import semantics.util.Visitor;
import syntax.Type;
import syntax.statements.VariableDefinition;


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
	
	
	private VariableDefinition findVariable(String name) {
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
	public String toString() {
		String strVariables = "";
		
		return String.format("struct { %s }", strVariables);
	}
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
}
