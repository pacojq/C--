package syntax.statements;

import semantics.util.Visitor;
import syntax.Definition;
import syntax.Type;

public class VariableDefinition extends AbstractStatement implements Definition {

	private int scope;
	
	private String name;
	private Type type;
	
	public VariableDefinition(int line, int column, String name, Type type) {
		super(line, column);
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return String.format("VariableDefinition [%s : %s]: %s %s", 
				getLine(),
				getColumn(),
				type,
				name);
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
	
	@Override
	public int getScope() {
		return scope;
	}
	
	@Override
	public void setScope(int scope) {
		this.scope = scope;
	}
	
}
