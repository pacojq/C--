package syntax.statements;

import semantics.util.Visitor;
import syntax.Expression;
import syntax.Statement;

public class Assignment implements Statement {

	private Expression left;
	private Expression right;
	
	public Assignment(Expression left, Expression right) {
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
	public int getLine() {
		return left.getLine();
	}

	@Override
	public int getColumn() {
		return left.getColumn();
	}
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
