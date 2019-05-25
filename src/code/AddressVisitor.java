package code;

import syntax.Definition;
import syntax.Expression;
import syntax.Type;
import syntax.expressions.ArrayAccess;
import syntax.expressions.AttributeAccess;
import syntax.expressions.Variable;
import syntax.statements.Read;
import syntax.types.ArrayType;
import syntax.types.IntType;
import syntax.types.StructType;

public class AddressVisitor extends CGVisitor<Void, Void> {

	
	private CodeGenerator cg = CodeGenerator.getInstance();
	public ValueVisitor valueVisitor;
	
	
	// - - - - - - - - WE ONLY DEAL WITH L-VALUE EXPRESSIONS - - - - - - - - //
	
	
	@Override
	public Void visit(Variable variable, Void params) {

		Definition def = variable.getDefinition();		
		
		// Global
		if (def.getScope() == 0) {
			variable.cgSetAddress(cg.push("a", def.getOffset()));
		}
		// Local
		else {
			variable.cgSetAddress(cg.pushbp());
			variable.cgAppendAddress(cg.push("i", def.getOffset()));
			variable.cgAppendAddress(cg.add("i"));
		}
		return null;
	}
	
	
	@Override
	public Void visit(ArrayAccess arrayAccess, Void params) {
				
		Expression array = arrayAccess.getArray();
		Expression index = arrayAccess.getIndex();
		Type typeOf = ((ArrayType) array.getType()).getTypeOf();
		
		// First take the address of the whole array
		array.accept(this, null);
		arrayAccess.cgSetAddress(array.cgGetAddress());
		
		
		// Now calculate the offset
		// offset = index * numberOfBytes
		index.accept(valueVisitor, null);
		arrayAccess.cgAppendAddress(index.cgGetValue());
		
		arrayAccess.cgAppendAddress( index.getType().cgConvert(new IntType(0, 0)) );
		arrayAccess.cgAppendAddress( cg.push("i", typeOf.numberOfBytes()) );
		
		arrayAccess.cgAppendAddress(cg.mul("i"));
		
		
		// Now, add it all up
		arrayAccess.cgAppendAddress(cg.add("i"));
		
		return null;
	}
	
	
	@Override
	public Void visit(AttributeAccess attributeAccess, Void params) {
		
		StructType t = (StructType) attributeAccess.getExpression().getType();
		int offset = t.findVariable(attributeAccess.getAttributeName()).getOffset();
		
		attributeAccess.cgSetAddress(attributeAccess.getExpression().cgGetAddress());
		attributeAccess.cgAppendAddress(cg.push("a", offset));
		attributeAccess.cgAppendAddress(cg.add("i"));
		
		return null;
	}
	
	
	
	@Override
	public Void visit(Read read, Void params) {
		
		// TODO
		
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
