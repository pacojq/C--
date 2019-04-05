package syntax.statements;

import syntax.AbstractASTNode;
import syntax.Definition;
import syntax.Statement;

public abstract class AbstractStatement extends AbstractASTNode implements Statement {

	private Definition container;
	
	public AbstractStatement(int line, int column) {
		super(line, column);
	}
	
	
	@Override
	public Definition getContainer() {
		return container;
	}

	@Override
	public void setContainer(Definition definition) {
		this.container = definition;
	}

}
