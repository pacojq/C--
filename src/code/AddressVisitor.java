package code;

import errors.ErrorHandler;
import syntax.Definition;
import syntax.Expression;
import syntax.Program;
import syntax.Statement;
import syntax.expressions.ArrayAccess;
import syntax.expressions.AttributeAccess;
import syntax.expressions.Variable;
import syntax.statements.Assignment;
import syntax.statements.FunctionDefinition;
import syntax.statements.Read;

public class AddressVisitor extends CGVisitor {

	
	// * * * * * * * * * * * * * * 
	//
	// Address Visitor works for Expressions with LValue
	//
	// * * * * * * * * * * * * * * 
	
	
	@Override
	public Void visit(Program p, Void params) {
		for (Definition def : p.getDefinitions())
			def.accept(this, params);
		return null;
	}
	
	
	
	@Override
	public Void visit(FunctionDefinition functionDefinition, Void params) {
		
		//functionDefinition.getType().accept(this, params);		
		
		for (Statement st : functionDefinition.getStatements()) {
			st.accept(this, params);
			
			
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public Void visit(Variable variable, Void params) {

		variable.cgAdress = "";
		Definition def = variable.getDefinition();
		
		variable.cgAdress = String.format("pusha %s\n", def.getOffset());
		
		// Global
		if (def.getScope() == 0) {			
			return null;
		}
		// Local
		else {
			variable.cgAdress += "pusha BP\n";
			variable.cgAdress += "addi\n";
		}		
		
		return null;
	}
	
	
	@Override
	public Void visit(ArrayAccess arrayAccess, Void params) {
		
		// TODO
		
		return null;
	}
	
	
	@Override
	public Void visit(AttributeAccess attributeAccess, Void params) {
		super.visit(attributeAccess, params);
		attributeAccess.setLValue(true);
		return null;
	}
	
	
	
	@Override
	public Void visit(Read read, Void params) {
		for (Expression expr : read.getExpressions()) {
			expr.accept(this, params);
		
			if (!expr.getLValue()) {
				ErrorHandler.getInstance().raiseError(
						expr.getLine(), 
						expr.getColumn(), 
						"Cannot perform a read statement with an expression whose lvalue is false: " 
								+ expr.toString());
			}
		}
		
		return null;
	}
	
	
	
	@Override
	public Void visit(Assignment assignment, Void params) {
		super.visit(assignment, params);
		
		if (!assignment.getLeft().getLValue()) {
			ErrorHandler.getInstance().raiseError(
					assignment.getLine(), 
					assignment.getColumn(), 
					"Cannot perform an assignment over an expression whose lvalue is false.");
		}
			
		return null;
	}
	
}
