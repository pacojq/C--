import parser.*;
import semantics.SemanticAnalysis;
import syntax.*;

import org.antlr.v4.runtime.*;

import errors.ErrorHandler;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;

public class Main {
	
	public static void main(String... args) throws Exception {
		
		if (args.length < 1) {
			System.err.println("Please, pass me the input file.");
			return;
		}
		
		ErrorHandler.getInstance().clear();

		// create a lexer that feeds off of input CharStream
		CharStream input = CharStreams.fromFileName(args[0]);
		CmmLexer lexer = new CmmLexer(input);

		// create a parser that feeds off the tokens buffer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmmParser parser = new CmmParser(tokens);
		Program ast = parser.program().ast;
		
		new SemanticAnalysis(ast).run();
		
		if (ErrorHandler.getInstance().anyError())
			ErrorHandler.getInstance().showErrors(System.err);

		// * The AST is shown
		IntrospectorModel model = new IntrospectorModel("Program", ast);
		new IntrospectorTree("Introspector", model);
	}
	
}
