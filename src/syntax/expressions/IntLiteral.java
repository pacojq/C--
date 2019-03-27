package syntax.expressions;

import semantics.util.Visitor;
import syntax.types.IntType;

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
		return String.format("IntLiteral [%s : %s]: %s", 
				getLine(),
				getColumn(),
				value);
	}
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
