package code;

import syntax.Definition;
import syntax.Expression;
import syntax.Program;
import syntax.Statement;
import syntax.expressions.FunctionInvocation;
import syntax.statements.*;
import syntax.types.FunctionType;
import syntax.types.VoidType;

public class ExecuteVisitor extends CGVisitor<Void, Void> {

	private AddressVisitor addressVisitor;
	private ValueVisitor valueVisitor;
	
	public ExecuteVisitor() {
		addressVisitor = new AddressVisitor();
		valueVisitor = new ValueVisitor();
	}
	
	
	@Override
	public Void visit(Program p, Void params) {
		
		for (Definition def : p.getDefinitions())
			def.accept(this, params);
		
		
		
		StringBuilder code = new StringBuilder();
		code.append("\n#source TODO\n\n"); // TODO
		
		for (Definition def : p.getDefinitions())
			if (def instanceof VariableDefinition)
				code.append( def.cgGetExecute() );
		
		code.append("' Invocation to the main fucntion\n");
		code.append("call main\n");
		code.append("halt\n\n");
		
		for (Definition def : p.getDefinitions())
			if (def instanceof FunctionDefinition)
				code.append( def.cgGetExecute() );
		
		p.cgCode = code.toString();
		
		return null;
	}
		
	
	
	// We only execute statements and definitions //
	
	
	@Override
	public Void visit(FunctionDefinition functionDefinition, Void params) {
		
		functionDefinition.cgSetExecute("\n#line %s", functionDefinition.getLine());
		functionDefinition.cgAppendExecute(" %s:\n\n", functionDefinition.getName());
		
		functionDefinition.cgAppendExecute("\t' * Parameters:\n");
		FunctionType ft = (FunctionType) functionDefinition.getType();
		for (VariableDefinition vd : ft.getParams()) {
			vd.accept(this, params);
			functionDefinition.cgAppendExecute(vd.cgGetExecute());
		}
		
		functionDefinition.cgAppendExecute("\t' * Local Variables:\n");
		functionDefinition.cgAppendExecute("\tenter\t%s", functionDefinition.localBytesSum);
		
		for (Statement st : functionDefinition.getStatements()) {
			st.accept(this, params);
			functionDefinition.cgAppendExecute(st.cgGetExecute());
		}
		return null;
	}
	
	
	@Override
	public Void visit(VariableDefinition variableDefinition, Void params) {		
		variableDefinition.cgSetExecute("\t' * %s", variableDefinition.toString());
		return null;
	}
	
	
	
	
	@Override
	public Void visit(Assignment assignment, Void params) {
		
		assignment.cgSetExecute("\n#line %s \t' * %s", assignment.getLine(), assignment);
		
		assignment.getLeft().accept(addressVisitor, null);
		assignment.cgAppendExecute(assignment.getLeft().cgGetAddress());
		
		//assignment.getLeft().accept(addressVisitor, null);
		assignment.getRight().accept(valueVisitor, null);
		assignment.cgAppendExecute(assignment.getRight().cgGetValue());
		
		assignment.cgAppendExecute("\tstore%s", assignment.getLeft().getType().cgSuffix());
		return null;
	}
	
	
	@Override
	public Void visit(IfStatement ifStatement, Void params) {
		
		ifStatement.cgSetExecute("\n#line %s \t' * %s", ifStatement.getLine(), ifStatement);
		
		return null;
	}
	
	@Override
	public Void visit(Read read, Void params) {
		
		read.cgSetExecute("\n#line %s \t' * %s", read.getLine(), read);
		
		return null;
	}
	
	@Override
	public Void visit(Return ret, Void params) {
		
		ret.cgSetExecute("\n#line %s \t' * %s", ret.getLine(), ret);
		
		return null;
	}
	
	@Override
	public Void visit(WhileLoop whileLoop, Void params) {
		
		whileLoop.cgSetExecute("\n#line %s \t' * %s", whileLoop.getLine(), whileLoop);
		
		return null;
	}
	
	@Override
	public Void visit(Write write, Void params) {
		
		write.cgSetExecute("\n#line %s", write.getLine());
		
		for (Expression expr : write.getExpressions()) {
			write.cgAppendExecute("\t' * Write %s", expr.toString());
			write.cgAppendExecute(expr.cgGetValue());
			write.cgAppendExecute("\tout%s", expr.getType().cgSuffix());
		}
		
		return null;
	}
	
	
	@Override
	public Void visit(FunctionInvocation funcInvocation, Void p) {
		
		funcInvocation.accept(valueVisitor, null);
		funcInvocation.cgSetExecute(funcInvocation.cgGetValue());
		
		Definition def = funcInvocation.getFunction().getDefinition();
		FunctionType t = (FunctionType) def.getType();
		if (!(t.getReturnType() instanceof VoidType))
			funcInvocation.cgAppendExecute("pop%s", t.getReturnType().cgSuffix());
		
		return null;
	}
	
}
