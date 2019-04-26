package syntax.expressions;

import syntax.types.IntType;
import visitor.Visitor;

public class IntLiteral extends AbstractExpression {

	private int value;
	
	public IntLiteral(int line, int column, int value) {
		super(line, column);
		this.value = value;
		this.setType(new IntType(line, column));
	}
	
	public int getValue() {
		return value;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s", value);
	}
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
