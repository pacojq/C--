package syntax.statements;

import java.util.LinkedList;
import java.util.List;

import semantics.util.Visitor;
import syntax.AbstractASTNode;
import syntax.Expression;
import syntax.Statement;

public class WhileLoop extends AbstractStatement {

	private Expression condition;
	private List<Statement> statements;
	
	public WhileLoop(int line, int column, Expression condition, List<Statement> statements) {
		super(line, column);
		this.condition = condition;
		this.statements = new LinkedList<Statement>(statements);
	}
	
	public Expression getCondition() {
		return condition;
	}
	
	public List<Statement> getStatements() {
		return new LinkedList<Statement>(statements);
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}

	
	
}
