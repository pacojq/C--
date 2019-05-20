package code;

import syntax.Type;
import syntax.expressions.binary.Arithmetic;

public class CodeGenerator {

	private static CodeGenerator instance;
	public static CodeGenerator getInstance() {
		if (instance == null)
			instance = new CodeGenerator();
		return instance;
	}
	
	
	
	private int label;
	
	
	private CodeGenerator() {
		
	}

	
	
	public int getLabels(int numberOfLabels) {
		
		int temp = this.label;
		this.label += numberOfLabels;
		return temp;		
	}
	
	
	
	public String arithmetic(Arithmetic arithmetic) {
		
		String op = arithmetic.getOperator();
		Type t = arithmetic.getType();
		
		String cgOperator = "";
		switch (op) {
			case "+": cgOperator = "sum"; break;
			case "-": cgOperator = "sub"; break;
			case "*": cgOperator = "mul"; break;
			case "/": cgOperator = "div"; break;
		
			default: return "INVALID ARITHMETIC OPERATOR " + op;
		}
		
		// TODO type promotion ?
		
		return "\t" + cgOperator + t.cgSuffix() + "\n";
	}

	
	public String convert(Type a, Type b) {
		
		// TODO
		
		return "";
	}
	
	
	
}
