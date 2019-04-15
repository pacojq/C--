package syntax.expressions;

import syntax.Definition;
import visitor.Visitor;

public class Variable extends AbstractExpression {
	
	private Definition definition;
	
	private String name;
	
	public Variable(int line, int column, String name) {
		super(line, column);
		this.name = new String(name);
	}
	
	public String getName() {
		return new String(name);
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
	
	public Definition getDefinition() {
		return definition;
	}
	
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	
	
	@Override
	public String toString() {
		String type = "";
		if (this.definition != null) {
			type += definition.getType().toString();
		}
		else {
			type += "UNDEFINED";
		}
		return type + " " + this.name;
	}
	
}
