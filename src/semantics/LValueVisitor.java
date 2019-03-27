package semantics;

import errors.ErrorHandler;
import semantics.util.AbstractVisitor;
import syntax.Expression;
import syntax.expressions.Variable;
import syntax.statements.Assignment;
import syntax.statements.Read;

public class LValueVisitor extends AbstractVisitor<Void, Void> {

	
	@Override
	public Void visit(Variable var, Void params) {
		var.setLValue(true);
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
		assignment.getLeft().accept(this, params);
		assignment.getRight().accept(this, params);
		
		if (!assignment.getLeft().getLValue()) {
			ErrorHandler.getInstance().raiseError(
					assignment.getLine(), 
					assignment.getColumn(), 
					"Cannot perform an assignment over an expression whose lvalue is false.");
		}
			
		return null;
	}
	
	
}
