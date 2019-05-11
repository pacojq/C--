package code;

import syntax.Definition;
import syntax.expressions.*;
import syntax.expressions.binary.*;

public class ValueVisitor extends CGVisitor<Void, Void> {

	
	public AddressVisitor addressVisitor;
	
	
	
	// WE ONLY DEAL WITH EXPRESSIONS //
	
	
	@Override
	public Void visit(Arithmetic arithmetic, Void params) {
		
		arithmetic.getLeft().accept(this, params);
		arithmetic.getRight().accept(this,  params);		
		
		arithmetic.cgSetValue(arithmetic.getLeft().cgGetValue());
		arithmetic.cgAppendValue(arithmetic.getRight().cgGetValue());
		arithmetic.cgAppendValue(
				CodeGenerator.getInstance().arithmetic(arithmetic)
			);		
				
		return null;
	}

	
	@Override
	public Void visit(Comparison comparison, Void params) {
		
		return null;
	}
	

	@Override
	public Void visit(LogicalOperation logicalOperation, Void params) {
		
		return null;
	}
	

	@Override
	public Void visit(ArrayAccess arrayAccess, Void params) {
		
		return null;
	}

	@Override
	public Void visit(AttributeAccess attributeAccess, Void params) {
		
		return null;
	}

	@Override
	public Void visit(Cast cast, Void params) {
		
		return null;
	}

	
	
	@Override
	public Void visit(CharLiteral charLiteral, Void params) {
		charLiteral.cgSetValue("\tpush%s\t%s", 
				charLiteral.getType().cgSuffix(), 
				(int) charLiteral.getValue());
		return null;
	}
	
	@Override
	public Void visit(IntLiteral intLiteral, Void params) {
		intLiteral.cgSetValue("\tpush%s\t%s", 
				intLiteral.getType().cgSuffix(), 
				(int) intLiteral.getValue());
		return null;
	}
	
	@Override
	public Void visit(DoubleLiteral doubleLiteral, Void params) {
		doubleLiteral.cgSetValue("\tpush%s\t%s", 
				doubleLiteral.getType().cgSuffix(), 
				doubleLiteral.getValue());
		return null;
	}
	
	
	

	@Override
	public Void visit(FunctionInvocation functionInvocation, Void params) {
		
		return null;
	}

	

	@Override
	public Void visit(NotSign notSign, Void params) {
		
		return null;
	}

	@Override
	public Void visit(UnaryMinus unaryMinus, Void params) {
		
		return null;
	}

	
	
	@Override
	public Void visit(Variable variable, Void params) {

		variable.accept(addressVisitor, null);
		Definition def = variable.getDefinition();
		
		variable.cgSetValue(variable.cgGetAddress());
		variable.cgAppendValue("\tload%s", def.getType().cgSuffix());
		
		return null;
	}
	
	
	
	
	
}
