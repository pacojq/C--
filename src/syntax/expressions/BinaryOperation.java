package syntax.expressions;

import syntax.Expression;

public abstract class BinaryOperation extends AbstractExpression {

	private String operator;
	private Expression left;
	private Expression right;
	
	
	public BinaryOperation(String operator, Expression left, Expression right) {
		super(left.getLine(), left.getColumn());
		this.operator = new String(operator);
		this.left = left;
		this.right = right;
	}
	
	public String getOperator() {
		return new String(operator);
	}
	
	public Expression getLeft() {
		return left;
	}
	
	public Expression getRight() {
		return right;
	}

	
	
}
