package syntax.statements;

import syntax.Expression;
import visitor.Visitor;

public class Assignment extends AbstractStatement {

	private Expression left;
	private Expression right;
	
	public Assignment(Expression left, Expression right) {
		super(left.getLine(), left.getColumn());
		this.left = left;
		this.right = right;
	}
	
	public Expression getLeft() {
		return left;
	}
	
	public Expression getRight() {
		return right;
	}

	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
