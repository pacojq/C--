package syntax.statements;

import java.util.LinkedList;
import java.util.List;

import syntax.AbstractASTNode;
import syntax.Expression;
import syntax.Statement;
import visitor.Visitor;

public class Write extends AbstractStatement {

	private List<Expression> expressions;
	
	public Write(int line, int column, List<Expression> expressions) {
		super(line, column);
		this.expressions = new LinkedList<Expression>(expressions);
	}
	
	
	public List<Expression> getExpressions() {
		return new LinkedList<Expression>(expressions);
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
