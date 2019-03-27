package semantics;

import errors.ErrorHandler;
import semantics.util.AbstractVisitor;
import syntax.Type;
import syntax.expressions.Variable;
import syntax.expressions.binary.Arithmetic;
import syntax.expressions.binary.Comparison;
import syntax.statements.Assignment;

public class TypeCheckingVisitor extends AbstractVisitor<Void, Void> {

	// TODO

	
	@Override
	public Void visit(Variable variable, Void params) {
		
		variable.setType(variable.getDefinition().getType());		
		return null;
	}
	
	
	@Override
	public Void visit(Arithmetic arithmetic, Void params) {
		
		super.visit(arithmetic, params);
		arithmetic.setType( 
				arithmetic.getLeft().getType()
				.arithmetic(arithmetic.getRight().getType()) 
			);		
		return null;
	}
	
	
	@Override
	public Void visit(Comparison comparison, Void params) {
		
		super.visit(comparison, params);
		comparison.setType( 
				comparison.getLeft().getType()
				.comparison(comparison.getRight().getType()) 
			);		
		return null;
	}
	
	
	
	@Override
	public Void visit(Assignment assignment, Void params) {
		super.visit(assignment, params);
		Type t1 = assignment.getLeft().getType();
		Type t2 = assignment.getRight().getType();
		
		if (!t1.isEquivalent(t2)) {
			ErrorHandler.getInstance().raiseError(
				assignment.getLine(), 
				assignment.getColumn(),
				String.format(
					"Cannot assign type %s to an expression declared as %s.", t2, t1)
				);
		}
		return null;
	}
	
}
