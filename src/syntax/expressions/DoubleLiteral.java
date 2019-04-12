package syntax.expressions;

import syntax.types.DoubleType;
import visitor.Visitor;

public class DoubleLiteral extends AbstractExpression {

	private double value;
	
	public DoubleLiteral(int line, int column, double value) {
		super(line, column);
		this.value = value;
		this.setType(new DoubleType(line, column));
	}
	
	public double getValue() {
		return value;
	}
	
	
	@Override
	public String toString() {
		return String.format("DoubleLiteral [%s : %s]: %s", 
				getLine(),
				getColumn(),
				value);
	}
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
}
