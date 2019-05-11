import parser.*;
import semantics.SemanticAnalysis;
import syntax.*;

import org.antlr.v4.runtime.*;

import code.CodeGeneration;
import errors.ErrorHandler;
import errors.MyAntlrErrorListener;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;

public class Main {
	
	public static void main(String... args) throws Exception {
		
		if (args.length < 1) {
			System.err.println("Please, pass me the input file.");
			return;
		}
		String filename = args[0];	
		ErrorHandler.getInstance().clear();

		// create a lexer that feeds off of input CharStream
		CharStream input = CharStreams.fromFileName(filename);
		CmmLexer lexer = new CmmLexer(input);

		// create a parser that feeds off the tokens buffer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmmParser parser = new CmmParser(tokens);
		
		
		ANTLRErrorListener error = new MyAntlrErrorListener();
		parser.removeErrorListeners();
	    lexer.removeErrorListeners();
	    parser.addErrorListener(error);
	    lexer.addErrorListener(error);
		
	    
		Program ast = parser.program().ast;
		
		
		if (ErrorHandler.getInstance().anyError()) {
			ErrorHandler.getInstance().showErrors(System.err);
			
			// * The AST is shown
			IntrospectorModel model = new IntrospectorModel("Program", ast);
			new IntrospectorTree("Introspector", model);
			
			return;
		}
		
		
		new SemanticAnalysis(ast).run();
		
		if (ErrorHandler.getInstance().anyError())
			ErrorHandler.getInstance().showErrors(System.err);
		
		// * The AST is shown
		IntrospectorModel model = new IntrospectorModel("Program", ast);
		new IntrospectorTree("Introspector", model);
		
		
		new CodeGeneration(ast).run(filename);
			
		System.out.println();
		System.out.println("- - - - - CODE GENERATED - - - - -");
		System.out.println();
		System.out.println(ast.cgCode);
		
	}
	
}
