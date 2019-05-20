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

		valueVisitor.addressVisitor = addressVisitor;
	}
	
	
	@Override
	public Void visit(Program p, Void params) {
		
		for (Definition def : p.getDefinitions())
			def.accept(this, params);
		
		
		StringBuilder code = new StringBuilder();
		//code.append("\n#source TODO\n\n"); // TODO source ...
		
		for (Definition def : p.getDefinitions())
			if (def instanceof VariableDefinition)
				code.append( def.cgGetExecute() );
		
		code.append("\n' Invocation to the main fucntion\n");
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
		int argBytesSum = 0;
		FunctionType ft = (FunctionType) functionDefinition.getType();
		for (VariableDefinition vd : ft.getParams()) {
			vd.accept(this, params);
			functionDefinition.cgAppendExecute(vd.cgGetExecute());
			argBytesSum += vd.getType().numberOfBytes();
		}
		
		
		
		functionDefinition.cgAppendExecute("\t' * Local Variables:\n");
		for (Statement st : functionDefinition.getStatements()) {
			if (st instanceof VariableDefinition) {
				st.accept(this, params);
				functionDefinition.cgAppendExecute(st.cgGetExecute());
			}
		}
		
		functionDefinition.cgAppendExecute("\tenter\t%s", functionDefinition.localBytesSum);
		
		for (Statement st : functionDefinition.getStatements()) {
			if (st instanceof VariableDefinition)
				continue;
			
			st.accept(this, params);
			functionDefinition.cgAppendExecute(st.cgGetExecute());
		}
		
		
		//The first constant represents the bytes to return; 
		//the second one, the bytes of all the local variables; 	
		//and the last one, the bytes of all the arguments.
		functionDefinition.cgAppendExecute("\tret %s, %s, %s",
				ft.getReturnType().numberOfBytes(),
				functionDefinition.localBytesSum,
				argBytesSum
			);
		
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
		// TODO
		
		int labelNumber = CodeGenerator.getInstance().getLabels(2);
		
		Expression condition = ifStatement.getCondition();
		
		// Check condition
		condition.accept(valueVisitor, null);
		ifStatement.cgSetExecute(condition.cgGetValue());
		
		// Jump to else if false (0)
		ifStatement.cgAppendExecute("\tjz label%s", labelNumber);
		
		
		// Execute all the "if" part...
		for (Statement st : ifStatement.getIfPart()) {
			st.accept(this, params);
			ifStatement.cgAppendExecute(st.cgGetExecute());
		}
		
		// ...and jump over the else part
		ifStatement.cgAppendExecute("\tjmp label%s", labelNumber + 1);
		
		
		// Execute all the "else" part
		ifStatement.cgAppendExecute("\tlabel%s", labelNumber);
		for (Statement st : ifStatement.getElsePart()) {
			st.accept(this, params);
			ifStatement.cgAppendExecute(st.cgGetExecute());
		}
		
		// End
		ifStatement.cgAppendExecute("\tlabel%s", labelNumber + 1);
		
		return null;
	}
	
	@Override
	public Void visit(Read read, Void params) {
		
		read.cgSetExecute("\n#line %s \t' * %s", read.getLine(), read);
		// TODO
		
		return null;
	}
	
	@Override
	public Void visit(Return ret, Void params) {
		
		ret.cgSetExecute("\n#line %s \t' * %s", ret.getLine(), ret);
		// TODO
		
		return null;
	}
	
	@Override
	public Void visit(WhileLoop whileLoop, Void params) {
		
		whileLoop.cgSetExecute("\n#line %s \t' * %s", whileLoop.getLine(), whileLoop);
		// TODO
				
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
			funcInvocation.cgAppendExecute("\tpop%s", t.getReturnType().cgSuffix());
		
		return null;
	}
	
}
