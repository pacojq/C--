package semantics;

import errors.ErrorHandler;
import semantics.symboltable.SymbolTable;
import semantics.util.AbstractVisitor;
import syntax.Definition;
import syntax.Statement;
import syntax.expressions.FunctionInvocation;
import syntax.expressions.Variable;
import syntax.statements.FunctionDefinition;
import syntax.statements.VariableDefinition;


public class RecognitionVisitor extends AbstractVisitor<Void, Void> {

	private SymbolTable table;
	
	
	public RecognitionVisitor() {
		this.table = new SymbolTable();
	}
	
	
	@Override
	public Void visit(VariableDefinition varDefinition, Void params) {
		
		boolean success = table.insert(varDefinition);
		if (!success) {
			ErrorHandler.getInstance().raiseError(
					varDefinition.getLine(), 
					varDefinition.getColumn(),
					"Trying to declare a variable which already exists.");
		}
		return null;
	}
	
	
	@Override
	public Void visit(FunctionDefinition funcDefinition, Void params) {
		
		// Insert and set the scope
		boolean success = table.insert(funcDefinition);
		if (!success) {
			ErrorHandler.getInstance().raiseError(
					funcDefinition.getLine(), 
					funcDefinition.getColumn(),
					"Trying to declare a function which already exists.");
		}
		table.set();
		
		// Normal visitor stuff
		funcDefinition.getType().accept(this, params);
		for (Statement st : funcDefinition.getStatements())
			st.accept(this, params);
		
		// Reset the scope
		table.reset();
		
		return null;
	}
	
	
	
	
	
	
	@Override
	public Void visit(Variable var, Void params) {
		
		Definition def = table.find(var.getName());
		
		if (def == null) {
			ErrorHandler.getInstance().raiseError(
					var.getLine(), 
					var.getColumn(),
					"Cannot use a variable which is not declared.");
		}
		var.setDefinition(def);		
		return null;
	}
	
	
	
	@Override
	public Void visit(FunctionInvocation func, Void params) {
		
		// There's no need to call visit on func.getFunction.
		// We're gonna do that job here.
		Variable var = func.getFunction();
		Definition def = table.find(var.getName());
		
		if (def == null) {
			ErrorHandler.getInstance().raiseError(
					var.getLine(), 
					var.getColumn(),
					"Cannot invoke a function which is not declared.");
		}		
		var.setDefinition(def);		
		
		return null;
	}
	
}
