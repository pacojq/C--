package code;

import syntax.Program;

public class CodeGeneration {

	private Program program;
	
	public CodeGeneration(Program program) {
		this.program = program;
	}
	
	
	public void run() {
		this.program.accept(new OffsetVisitor(), null);
		this.program.accept(new ExecuteVisitor(), null);
	}
	
}
