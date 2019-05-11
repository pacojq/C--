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
		
		// Global
		if (def.getScope() == 0) {
			variable.cgSetAddress("\tpushi\t%s", def.getOffset());
		}
		// Local
		else {
			variable.cgSetAddress("\tpush\tbp");
			variable.cgAppendAddress("\tpushi\t%s", def.getOffset());
			variable.cgAppendAddress("\taddi");
		}
		return null;
	}
	
	
	@Override
	public Void visit(ArrayAccess arrayAccess, Void params) {
		arrayAccess.cgSetAddress(arrayAccess.getArray().cgGetAddress());
		arrayAccess.cgAppendAddress("\tpusha %s", arrayAccess.getIndex().cgGetValue());
		arrayAccess.cgAppendAddress("\taddi");
		return null;
	}
	
	
	@Override
	public Void visit(AttributeAccess attributeAccess, Void params) {
		
		StructType t = (StructType) attributeAccess.getExpression().getType();
		int offset = t.findVariable(attributeAccess.getAttributeName()).getOffset();
		
		attributeAccess.cgSetAddress(attributeAccess.getExpression().cgGetAddress());
		attributeAccess.cgAppendAddress("\tpusha %s", offset);
		attributeAccess.cgAppendAddress("\taddi");
		
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
