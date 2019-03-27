package syntax.statements;

import semantics.util.Visitor;
import syntax.AbstractASTNode;
import syntax.Expression;
import syntax.Statement;

public class Return extends AbstractASTNode implements Statement {

	private Expression value;
	
	public Return(int line, int column, Expression value) {
		super(line, column);
		this.value = value;
	}
	
	public Expression getValue() {
		return value;
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
