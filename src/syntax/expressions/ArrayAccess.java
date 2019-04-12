package syntax.expressions;

import syntax.Expression;
import visitor.Visitor;

public class ArrayAccess extends AbstractExpression {

	private Expression array;
	private Expression index;
	
	public ArrayAccess(Expression array, Expression index) {
		super(array.getLine(), array.getColumn());
		this.array = array;
		this.index = index;
	}
	
	public Expression getArray() {
		return array;
	}
	
	public Expression getIndex() {
		return index;
	}

	
	@Override
	public String toString() {
		return String.format("ArrayAccess [%s : %s] : array=[ %s ] index=[ %s ]", 
				getLine(),
				getColumn(),
				array,
				index);
	}
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
