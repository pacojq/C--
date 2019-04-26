package semantics;

import syntax.Program;

public class SemanticAnalysis {

	private Program program;
	
	public SemanticAnalysis(Program program) {
		this.program = program;
	}
	
	
	public void run() {
		this.program.accept(new RecognitionVisitor(), null);
		this.program.accept(new TypeCheckingVisitor(), null);
		this.program.accept(new ReturnTypeVisitor(), null);
		this.program.accept(new LValueVisitor(), null);
	}
	
}
