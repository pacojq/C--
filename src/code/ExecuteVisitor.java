package code;

import syntax.Definition;
import syntax.Program;
import syntax.Statement;
import syntax.statements.*;

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
		return null;
	}
		
	
	
	// We only execute statements and definitions //
	
	
	@Override
	public Void visit(FunctionDefinition functionDefinition, Void params) {
		functionDefinition.cgSetExecute(
				String.format("' FUNCTION: %s \n", functionDefinition.getName())
			);
		
		for (Statement st : functionDefinition.getStatements()) {
			st.accept(this, params);
			functionDefinition.cgAppendExecute(st.cgGetExecute());
		}
		return null;
	}
	
	
	@Override
	public Void visit(VariableDefinition variableDefinition, Void params) {		
		/*variableDefinition.cgSetExecute(
				String.format("' VARIABLE: %s \n", variableDefinition.getName())
			);*/
		return null;
	}
	
	
	
	
	@Override
	public Void visit(Assignment assignment, Void params) {
		
		assignment.cgSetExecute("\t'%s", assignment.toString());
		
		assignment.getLeft().accept(addressVisitor, null);
		assignment.cgAppendExecute(assignment.getLeft().cgGetAddress());
		
		assignment.getRight().accept(addressVisitor, null);
		assignment.getRight().accept(valueVisitor, null);
		assignment.cgAppendExecute(assignment.getRight().cgGetValue());
		
		assignment.cgAppendExecute("load%s", assignment.getLeft().getType().cgSufix());
		return null;
	}
	
	
	@Override
	public Void visit(IfStatement ifStatement, Void params) {
		
		
		
		return null;
	}
	
	@Override
	public Void visit(Read read, Void params) {
		
		return null;
	}
	
	@Override
	public Void visit(Return ret, Void params) {
		
		return null;
	}
	
	@Override
	public Void visit(WhileLoop whileLoop, Void params) {
		
		return null;
	}
	
	@Override
	public Void visit(Write write, Void params) {
		
		return null;
	}
	
}
