package code;

import syntax.Definition;
import syntax.Program;
import syntax.Statement;
import syntax.statements.FunctionDefinition;

public class CodeGenerator {

	private static CodeGenerator instance;
	public static CodeGenerator getInstance() {
		if (instance == null)
			instance = new CodeGenerator();
		return instance;
	}
	
	
	private CodeGenerator() {
		
	}
	
	
	
	public String getAllCode(Program p) {		
		StringBuilder code = new StringBuilder();		
		for (Definition def : p.getDefinitions())
			code.append( def.cgGetExecute() );
		return code.toString();
	}
	
	
}
