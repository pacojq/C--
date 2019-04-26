package syntax;

import java.util.ArrayList;
import java.util.List;

import visitor.Visitor;

public class Program implements ASTNode {

	private int line;
	private int column;
	
	public String cgCode;
	
	private List<Definition> definitions;

	
	public Program(int line, int column) {
		this.line = line;
		this.column = column;
		// Empty program
	}
	
	public Program(int line, int column, List<Definition> definitions) {
		this(line, column);
		this.definitions = new ArrayList<Definition>(definitions);
	}
	
	
	public List<Definition> getDefinitions() {
		return new ArrayList<Definition>(definitions);
	}
	
	
	@Override
	public int getLine() {
		return this.line;
	}

	@Override
	public int getColumn() {
		return this.column;
	}
	
	
	@Override
	public String toString() {
		return String.format("Program [%s : %s] : %s",
				getLine(),
				getColumn(),
				definitions.size());
	}

	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}

	
}
