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

	private CodeGenerator cg;
	
	private AddressVisitor addressVisitor;
	private ValueVisitor valueVisitor;
	
	public ExecuteVisitor() {
		
		cg = CodeGenerator.getInstance();
		
		addressVisitor = new AddressVisitor();
		valueVisitor = new ValueVisitor();

		valueVisitor.addressVisitor = addressVisitor;
		addressVisitor.valueVisitor = valueVisitor;
	}
	
	
	@Override
	public Void visit(Program p, Void params) {
		
		for (Definition def : p.getDefinitions())
			def.accept(this, params);
		
		
		StringBuilder code = new StringBuilder();
		
		for (Definition def : p.getDefinitions())
			if (def instanceof VariableDefinition)
				code.append( def.cgGetExecute() );
		
		code.append("\n' Invocation to the main function\n");
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
		
		functionDefinition.cgSetExecute(cg.function(functionDefinition));
		
		functionDefinition.cgAppendExecute(cg.comment("Parameters\n"));
		int argBytesSum = 0;
		FunctionType ft = (FunctionType) functionDefinition.getType();
		for (VariableDefinition vd : ft.getParams()) {
			vd.accept(this, params);
			functionDefinition.cgAppendExecute(vd.cgGetExecute());
			argBytesSum += vd.getType().numberOfBytes();
		}
		
		
		
		functionDefinition.cgAppendExecute(cg.comment("Local variables\n"));
		for (Statement st : functionDefinition.getStatements()) {
			if (st instanceof VariableDefinition) {
				st.accept(this, params);
				functionDefinition.cgAppendExecute(st.cgGetExecute());
			}
		}
		
		functionDefinition.cgAppendExecute(cg.enter(functionDefinition.localBytesSum));
		
		for (Statement st : functionDefinition.getStatements()) {
			if (st instanceof VariableDefinition)
				continue;
			
			st.accept(this, params);
			functionDefinition.cgAppendExecute(st.cgGetExecute());
		}
		
		
		//The first constant represents the bytes to return; 
		//the second one, the bytes of all the local variables; 	
		//and the last one, the bytes of all the arguments.
		functionDefinition.cgAppendExecute(cg.ret(
				ft.getReturnType().numberOfBytes(),
				functionDefinition.localBytesSum,
				argBytesSum
			));
		
		return null;
	}
	
	
	@Override
	public Void visit(VariableDefinition variableDefinition, Void params) {		
		variableDefinition.cgSetExecute(cg.comment(variableDefinition.toString()));
		return null;
	}
	
	
	
	
	@Override
	public Void visit(Assignment assignment, Void params) {
		
		assignment.cgSetExecute(cg.line(assignment));
		
		assignment.getLeft().accept(addressVisitor, null);
		assignment.cgAppendExecute(assignment.getLeft().cgGetAddress());
		
		assignment.getRight().accept(valueVisitor, null);
		assignment.cgAppendExecute(assignment.getRight().cgGetValue());
		
		assignment.cgAppendExecute(cg.store(assignment.getLeft().getType()));
		return null;
	}
	
	
	@Override
	public Void visit(IfStatement ifStatement, Void params) {
		
		ifStatement.cgSetExecute(cg.line(ifStatement));
		
		int labelNumber = CodeGenerator.getInstance().getLabels(2);
		
		Expression condition = ifStatement.getCondition();
		
		// Check condition
		condition.accept(valueVisitor, null);
		ifStatement.cgAppendExecute(condition.cgGetValue());
		
		// Jump to else if false (0)
		ifStatement.cgAppendExecute(cg.jumpZero(labelNumber));
		
		
		// Execute all the "if" part...
		ifStatement.cgAppendExecute(cg.comment("Body of the if branch"));
		for (Statement st : ifStatement.getIfPart()) {
			st.accept(this, params);
			ifStatement.cgAppendExecute(st.cgGetExecute());
		}
		
		// ...and jump over the else part
		ifStatement.cgAppendExecute(cg.jump(labelNumber + 1));
		
		
		// Execute all the "else" part
		ifStatement.cgAppendExecute(cg.label(labelNumber));
		ifStatement.cgAppendExecute(cg.comment("Body of the else branch"));
		for (Statement st : ifStatement.getElsePart()) {
			st.accept(this, params);
			ifStatement.cgAppendExecute(st.cgGetExecute());
		}
		
		// End
		ifStatement.cgAppendExecute(cg.label(labelNumber + 1));
		
		return null;
	}
	
	@Override
	public Void visit(Read read, Void params) {
		
		read.cgSetExecute(cg.line(read));
		
		for (Expression expr : read.getExpressions()) {
			read.cgAppendExecute(cg.comment(" Read %s" + expr.toString()));
			expr.accept(addressVisitor, null);
			read.cgAppendExecute(expr.cgGetAddress());
			read.cgAppendExecute(cg.in(expr.getType()));
		}
		
		return null;
	}
	
	@Override
	public Void visit(Return ret, Void params) {
		
		ret.cgSetExecute(cg.line(ret));
		
		// Just push the value
		ret.getValue().accept(valueVisitor, null);
		ret.cgAppendExecute(ret.getValue().cgGetValue());
		
		return null;
	}
	
	@Override
	public Void visit(WhileLoop whileLoop, Void params) {
		
		whileLoop.cgSetExecute(cg.line(whileLoop));
		
		int labelNumber = CodeGenerator.getInstance().getLabels(2);
		
		// Set first label
		whileLoop.cgAppendExecute(cg.jump(labelNumber));
		
		
		// Check condition
		Expression condition = whileLoop.getCondition();
		condition.accept(valueVisitor, null);
		whileLoop.cgAppendExecute(condition.cgGetValue());
		
		// Jump to the end if false (0)
		whileLoop.cgAppendExecute(cg.jumpZero(labelNumber+1));
		
		
		// Execute all the loop...
		for (Statement st : whileLoop.getStatements()) {
			st.accept(this, params);
			whileLoop.cgAppendExecute(st.cgGetExecute());
		}
		
		// ...and jump back to the condition check
		whileLoop.cgAppendExecute(cg.jump(labelNumber));
		
		// End
		whileLoop.cgAppendExecute(cg.label(labelNumber + 1));
				
		return null;
	}
	
	@Override
	public Void visit(Write write, Void params) {
		
		write.cgSetExecute(cg.line(write));
		
		for (Expression expr : write.getExpressions()) {
			write.cgAppendExecute(cg.comment(" Write %s" + expr.toString()));
			expr.accept(valueVisitor, null);
			write.cgAppendExecute(expr.cgGetValue());
			write.cgAppendExecute(cg.out(expr.getType()));
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
			funcInvocation.cgAppendExecute(cg.pop(t.getReturnType()));
		
		return null;
	}
	
}
