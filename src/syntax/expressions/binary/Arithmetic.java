package syntax.expressions.binary;

import semantics.util.Visitor;
import syntax.Expression;
import syntax.expressions.BinaryOperation;

public class Arithmetic extends BinaryOperation {

	public Arithmetic(String operator, Expression left, Expression right) {
		super(operator, left, right);
	}
	
	
	@Override
	public String toString() {
		return String.format("Arithmetic [%s : %s]: %s %s %s", 
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
