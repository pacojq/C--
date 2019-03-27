package syntax.types;

import java.util.ArrayList;
import java.util.List;

import semantics.util.Visitor;
import syntax.statements.VariableDefinition;


public class StructType extends AbstractType {

	private List<VariableDefinition> variables;
	
	public StructType(int line, int column, List<VariableDefinition> variables) {
		super(line, column);
		this.variables = new ArrayList<VariableDefinition>(variables);
	}
	
	public List<VariableDefinition> getVariables() {
		return new ArrayList<VariableDefinition>(variables);
	}
	
	
	
	
	@Override
	public String getName() {
		return "StructType";
	}
	
	// TODO to string
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
}
