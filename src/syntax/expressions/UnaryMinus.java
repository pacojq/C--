package syntax.expressions;

import semantics.util.Visitor;
import syntax.Expression;

public class UnaryMinus extends AbstractExpression {

	private Expression operand;
	
	public UnaryMinus(int line, int column, Expression operand) {
		super(line, column);
		this.operand = operand;
	}
	
	public Expression getOperand() {
		return operand;
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
