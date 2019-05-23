package code;

import syntax.Definition;
import syntax.Expression;
import syntax.Type;
import syntax.expressions.*;
import syntax.expressions.binary.*;

public class ValueVisitor extends CGVisitor<Void, Void> {

	
	public AddressVisitor addressVisitor;
	
	private CodeGenerator cg = CodeGenerator.getInstance();
	
	
	// WE ONLY DEAL WITH EXPRESSIONS //
	
	
	@Override
	public Void visit(Arithmetic arithmetic, Void params) {
		
		arithmetic.getLeft().accept(this, params);
		arithmetic.getRight().accept(this,  params);		
		
		arithmetic.cgSetValue(cg.arithmetic(arithmetic));
		
		return null;
	}

	
	@Override
	public Void visit(Comparison comparison, Void params) {
		
		// TODO comparison
		
		return null;
	}
	

	@Override
	public Void visit(LogicalOperation logicalOperation, Void params) {
		
		// TODO LogicalOperation
		
		return null;
	}
	

	@Override
	public Void visit(ArrayAccess arrayAccess, Void params) {
		
		// TODO ArrayAccess
		
		return null;
	}

	@Override
	public Void visit(AttributeAccess attributeAccess, Void params) {
		
		// TODO AttributeAccess
		return null;
	}

	@Override
	public Void visit(Cast cast, Void params) {
		
		Expression operand = cast.getOperand();
		
		operand.accept(this, params);
		cast.cgSetValue(operand.cgGetValue());
		cast.cgAppendValue(operand.getType().cgConvert(cast.getTargetType()));
		
		return null;
	}

	
	
	@Override
	public Void visit(CharLiteral charLiteral, Void params) {
		charLiteral.cgSetValue(cg.push(charLiteral.getType(), (int) charLiteral.getValue()));
		return null;
	}
	
	@Override
	public Void visit(IntLiteral intLiteral, Void params) {
		intLiteral.cgSetValue(cg.push(intLiteral.getType(), intLiteral.getValue()));
		return null;
	}
	
	@Override
	public Void visit(DoubleLiteral doubleLiteral, Void params) {
		doubleLiteral.cgSetValue(cg.push(doubleLiteral.getType(), doubleLiteral.getValue()));
		return null;
	}
	
	
	

	@Override
	public Void visit(FunctionInvocation functionInvocation, Void params) {
		
		// TODO
		return null;
	}

	

	@Override
	public Void visit(NotSign notSign, Void params) {
		
		// TODO
		return null;
	}

	@Override
	public Void visit(UnaryMinus unaryMinus, Void params) {
		Type t = unaryMinus.getType();
		
		unaryMinus.cgSetValue(cg.push(t, 0));
		
		unaryMinus.getOperand().accept(this, params);
		unaryMinus.cgAppendValue(unaryMinus.getOperand().cgGetValue());
		
		unaryMinus.cgAppendValue(cg.sub(t));
		
		return null;
	}

	
	
	@Override
	public Void visit(Variable variable, Void params) {

		variable.accept(addressVisitor, null);
		Definition def = variable.getDefinition();
		
		variable.cgSetValue(variable.cgGetAddress());
		variable.cgAppendValue(cg.load(def.getType()));
		
		return null;
	}
	
	
	
	
	
}
