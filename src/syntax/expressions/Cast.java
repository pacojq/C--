package syntax.expressions;

import syntax.Expression;
import syntax.Type;
import visitor.Visitor;

public class Cast extends AbstractExpression {

	private Expression operand;
	private Type targetType;
	
	public Cast(int line, int column, Expression operand, Type type) {
		super(line, column);
		this.operand = operand;
		this.targetType = type;
	}
	
	public Expression getOperand() {
		return operand;
	}
	
	public Type getTargetType() {
		return targetType;
	}
	
	
	@Override
	public String toString() {
		return String.format("(%s) %s", targetType, operand);
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}

	
}
