package syntax.statements;

import java.util.ArrayList;
import java.util.List;

import semantics.util.Visitor;
import syntax.Expression;
import syntax.Statement;


public class IfStatement extends AbstractStatement {

	private Expression condition;
	private List<Statement> ifPart;
	private List<Statement> elsePart;
	
	public IfStatement(int line, int column, Expression condition,
			List<Statement> ifPart) {
		
		super(line, column);
		this.condition = condition;
		this.ifPart = new ArrayList<Statement>(ifPart);
	}
	
	public Expression getCondition() {
		return condition;
	}
	
	public List<Statement> getIfPart() {
		return new ArrayList<Statement>(ifPart);
	}
	
	public List<Statement> getElsePart() {
		if (elsePart == null)
			return new ArrayList<Statement>();
		return new ArrayList<Statement>(elsePart);
	}
	
	public void setElsePart(List<Statement> elsePart) {
		this.elsePart = new ArrayList<Statement>(elsePart);
	}
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
