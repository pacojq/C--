package syntax.expressions.binary;

import syntax.Expression;
import syntax.expressions.BinaryOperation;
import visitor.Visitor;

public class Arithmetic extends BinaryOperation {

	public Arithmetic(String operator, Expression left, Expression right) {
		super(operator, left, right);
	}
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}

}
