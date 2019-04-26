package code;

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


	
	
	
	
}
