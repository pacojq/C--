package syntax.statements;

import syntax.AbstractASTNode;
import syntax.Statement;

public abstract class AbstractStatement extends AbstractASTNode implements Statement {

	
	private String cgExecute = "";
	
	
	public AbstractStatement(int line, int column) {
		super(line, column);
	}
	
	
	
	
	@Override
	public String cgGetExecute() {
		return cgExecute;
	}


	@Override
	public void cgAppendExecute(String execute, Object... format) {
		if (execute == null) execute = ""; // TODO remove this line
		if (!execute.endsWith("\n"))
			execute += "\n";
		cgExecute += String.format(execute, format);
	}


	@Override
	public void cgSetExecute(String execute, Object... format) {
		if (execute == null) execute = ""; // TODO remove this line
		if (!execute.endsWith("\n"))
			execute += "\n";
		cgExecute = String.format(execute, format);
	}

}
