package syntax.statements;

import syntax.AbstractASTNode;
import syntax.Statement;

public abstract class AbstractStatement extends AbstractASTNode implements Statement {

	
	private String cgExecute = "EXECUTE-PLACEHOLDER";
	
	
	public AbstractStatement(int line, int column) {
		super(line, column);
	}
	
	
	
	
	@Override
	public String cgGetExecute() {
		return cgExecute;
	}


	@Override
	public void cgAppendExecute(String execute) {
		if (execute.isEmpty())
			return;
		if (!execute.endsWith("\n"))
			execute += "\n";
		cgExecute += execute;
	}


	@Override
	public void cgSetExecute(String execute) {
		if (!execute.endsWith("\n"))
			execute += "\n";
		cgExecute = execute;
	}

}
