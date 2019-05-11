package syntax.expressions;

import syntax.Expression;
import visitor.Visitor;

public class AttributeAccess extends AbstractExpression {

	private Expression expression;
	private String attributeName;
	
	
	public AttributeAccess(Expression expression, String attributeName) {
		super(expression.getLine(), expression.getColumn());
		this.expression = expression;
		this.attributeName = new String(attributeName);
	}
	
	
	
	public Expression getExpression() {
		return expression;
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
	
	@Override
	public String toString() {
		return String.format("%s.%s", expression.toString(), attributeName);
	}
	
	
}
