package code;

import syntax.Definition;
import syntax.Expression;
import syntax.Type;
import syntax.expressions.ArrayAccess;
import syntax.expressions.AttributeAccess;
import syntax.expressions.Variable;
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
		Type typeOf = arrayAccess.getType();
		
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
		
		Expression struct = attributeAccess.getExpression();
		
		StructType t = (StructType) struct.getType();
		int offset = t.findVariable(attributeAccess.getAttributeName()).getOffset();
		
		struct.accept(this, null);
		attributeAccess.cgSetAddress(struct.cgGetAddress());
		attributeAccess.cgAppendAddress(cg.push("i", offset));
		attributeAccess.cgAppendAddress(cg.add("i"));
		
		return null;
	}
	
	
	
	
	
	
	
	
}
