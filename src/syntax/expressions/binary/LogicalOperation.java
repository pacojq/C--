package syntax.expressions.binary;

import syntax.Expression;
import syntax.expressions.BinaryOperation;
import visitor.Visitor;

public class LogicalOperation extends BinaryOperation {

	public LogicalOperation(String operator, Expression left, Expression right) {
		super(operator, left, right);
	}

	
	@Override
	public String toString() {
		return String.format("Logical [%s : %s]: %s %s %s", 
				getLine(),
				getColumn(),
				getLeft(),
				getOperator(),
				getRight());
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}

}
