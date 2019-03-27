package syntax.expressions;

import syntax.AbstractASTNode;
import syntax.Expression;
import syntax.Type;

public abstract class AbstractExpression extends AbstractASTNode implements Expression {

	private Type type;
	private boolean lvalue;

	public AbstractExpression(int line, int column) {
		super(line, column);
		lvalue = false;
		type = null;
	}

	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public boolean getLValue() {
		return lvalue;
	}

	@Override
	public void setLValue(boolean lvalue) {
		this.lvalue = lvalue;
	}

}
