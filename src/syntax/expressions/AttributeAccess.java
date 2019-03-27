package syntax.expressions;

import semantics.util.Visitor;
import syntax.Expression;

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
	
	
}
