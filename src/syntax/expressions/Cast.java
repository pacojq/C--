package syntax.expressions;

import semantics.util.Visitor;
import syntax.Expression;
import syntax.Type;

public class Cast extends AbstractExpression {

	private Expression operand;
	private Type type;
	
	public Cast(int line, int column, Expression operand, Type type) {
		super(line, column);
		this.operand = operand;
		this.type = type;
	}
	
	public Expression getOperand() {
		return operand;
	}
	
	public Type getType() {
		return type;
	}
	
	
	@Override
	public String toString() {
		return String.format("Cast [%s : %s]: ( %s ) %s", 
				getLine(),
				getColumn(),
				type,
				operand);
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}

	
}
