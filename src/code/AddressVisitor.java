package code;

import syntax.Definition;
import syntax.expressions.ArrayAccess;
import syntax.expressions.AttributeAccess;
import syntax.expressions.Variable;
import syntax.statements.Read;
import syntax.types.StructType;

public class AddressVisitor extends CGVisitor<Void, Void> {

	
	
	// - - - - - - - - WE ONLY DEAL WITH L-VALUE EXPRESSIONS - - - - - - - - //
	
	
	@Override
	public Void visit(Variable variable, Void params) {

		Definition def = variable.getDefinition();		
		variable.cgSetAddress("\tpushi\t%s", def.getOffset());
		
		// Global
		if (def.getScope() == 0) {
			return null;
		}
		// Local
		else {
			variable.cgAppendAddress("\tpush\tbp");
			variable.cgAppendAddress("\taddi");
		}
		return null;
	}
	
	
	@Override
	public Void visit(ArrayAccess arrayAccess, Void params) {
		arrayAccess.cgSetAddress(arrayAccess.getArray().cgGetAddress());
		arrayAccess.cgAppendAddress("pusha %s", arrayAccess.getIndex().cgGetValue());
		arrayAccess.cgAppendAddress("addi");
		return null;
	}
	
	
	@Override
	public Void visit(AttributeAccess attributeAccess, Void params) {
		
		StructType t = (StructType) attributeAccess.getExpression().getType();
		int offset = t.findVariable(attributeAccess.getAttributeName()).getOffset();
		
		attributeAccess.cgSetAddress(attributeAccess.getExpression().cgGetAddress());
		attributeAccess.cgAppendAddress("pusha %s", offset);
		attributeAccess.cgAppendAddress("addi");
		
		return null;
	}
	
	
	
	@Override
	public Void visit(Read read, Void params) {
		/*for (Expression expr : read.getExpressions()) {
			expr.accept(this, params);
		
			if (!expr.getLValue()) {
				ErrorHandler.getInstance().raiseError(
						expr.getLine(), 
						expr.getColumn(), 
						"Cannot perform a read statement with an expression whose lvalue is false: " 
								+ expr.toString());
			}
		}
		*/
		return null;
	}
	
	
/*	
	@Override
	public Void visit(Assignment assignment, Void params) {
//		super.visit(assignment, params);
//		
//		if (!assignment.getLeft().getLValue()) {
//			ErrorHandler.getInstance().raiseError(
//					assignment.getLine(), 
//					assignment.getColumn(), 
//					"Cannot perform an assignment over an expression whose lvalue is false.");
//		}
		
		return null;
	}
*/	
	
	
	
	
	
	
}
