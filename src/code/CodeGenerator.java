package code;

import syntax.Definition;
import syntax.Statement;
import syntax.Type;
import syntax.expressions.binary.Arithmetic;
import syntax.statements.FunctionDefinition;

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
	
	
	
	
	
	
	
	
	
	public String comment(String comment) {
		return "\t' * " + comment;
	}
	
	
	
	public String line(Statement st) {
		return String.format("\n#line\t%s\t' * %s", st.getLine(), st.toString());
	}
	
	public String line(Definition def) {
		return String.format("\n#line\t%s\t' * %s", def.getLine(), def.toString());
	}
	
	
	

	// ========================= Jumping ========================= //
	
	public String label(int label) {
		return String.format("label%s:", label);
	}	
	
	public String jump(int toLabel) {
		return String.format("\tjmp label%s", toLabel);
	}
	
	public String jumpZero(int toLabel) {
		return String.format("\tjz label%s", toLabel);
	}
	
	
	
	
	
	

	// ===================== In/Out Store/Load ===================== //
	
	public String out(Type type) {
		return String.format("\tout%s", type.cgSuffix());
	}
	
	public String in(Type type) {
		return String.format("\tin%s", type.cgSuffix());
	}
	
	
	
	
	public String store(Type type) {
		return String.format("\tstore%s", type.cgSuffix());
	}
	
	public String load(Type type) {
		return String.format("\tload%s", type.cgSuffix());
	}
	
	
	
	
	
	
	// ====================== Pushing/Popping ====================== //
	
	
	
	public String pop(Type type) {
		return String.format("\tpop%s", type.cgSuffix());
	}
	
	
	
	
	
	public String pushbp() {
		return "\tpush\tbp";
	}
	
	public String push(String type, int value) {
		return String.format("\tpush%s\t%s", type, value);		
	}
	
	public String push(Type type, int value) {
		return String.format("\tpush%s\t%s", type.cgSuffix(), value);		
	}
	
	public String push(Type type, double value) {
		return String.format("\tpush%s\t%s", type.cgSuffix(), value);		
	}
	
	
	
	
	
	
	// ======================= Arithmetics ======================= //
	
	public String arithmetic(Arithmetic arithmetic) {
		
		Type t = arithmetic.getType();
		
		StringBuilder str = new StringBuilder();
		
		str.append( arithmetic.getLeft().cgGetValue() );
		str.append( arithmetic.getLeft().getType().cgConvert(t) );
		
		str.append( arithmetic.getRight().cgGetValue() );
		str.append( arithmetic.getRight().getType().cgConvert(t) );
		
		switch (arithmetic.getOperator()) {
			case "+": str.append(this.add(t)); break;
			case "-": str.append(this.sub(t)); break;
			case "*": str.append(this.mul(t)); break;
			case "/": str.append(this.div(t)); break;
		
			default: return "INVALID ARITHMETIC OPERATOR " + arithmetic.getOperator();
		}
		
		return str.toString();
	}

	public String add(Type type) {
		return String.format("\tadd%s", type.cgSuffix());
	}
	
	public String add(String type) {
		return String.format("\tadd%s", type);
	}
	
	public String sub(Type type) {
		return String.format("\tsub%s", type.cgSuffix());
	}
	
	public String mul(Type type) {
		return String.format("\tmul%s", type.cgSuffix());
	}
	
	public String div(Type type) {
		return String.format("\tdiv%s", type.cgSuffix());
	}


	
	
	
	// ======================= Functions ======================= //
	
	
	public String function(FunctionDefinition functionDefinition) {
		return String.format("%s\n %s:\n\n", 
				this.line(functionDefinition), 
				functionDefinition.getName());
	}

	public String enter(int localBytesSum) {
		return String.format("\tenter\t%s", localBytesSum);
	}


	public String ret(int bytesReturn, int bytesLocals, int bytesArgs) {
		return String.format("\tret\t%s, %s, %s", bytesReturn, bytesLocals, bytesArgs);
	}

	
	
	
	
	// ======================= Type Conversion ======================= //

	public String f2i() {
		return "\tf2i\n";
	}
	
	public String i2f() {
		return "\ti2f\n";
	}
	
	
	public String b2i() {
		return "\tb2i\n";
	}
	
	public String i2b() {
		return "\ti2b\n";
	}
	
	
}
