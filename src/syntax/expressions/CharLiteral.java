package syntax.expressions;

import syntax.types.CharType;
import visitor.Visitor;

public class CharLiteral extends AbstractExpression {

	private char value;
	
	public CharLiteral(int line, int column, char value) {
		super(line, column);
		this.value = value;
		this.setType(new CharType(line, column));
	}
	
	public char getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return String.format("'%s'", value);
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
}
