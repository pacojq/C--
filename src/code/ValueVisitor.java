package code;

import syntax.Definition;
import syntax.Expression;
import syntax.Type;
import syntax.expressions.*;
import syntax.expressions.binary.*;

public class ValueVisitor extends CGVisitor<Void, Void> {


	private CodeGenerator cg = CodeGenerator.getInstance();
	public AddressVisitor addressVisitor;
	
	
	
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
		
		comparison.getLeft().accept(this, params);
		comparison.getRight().accept(this,  params);		
		
		comparison.cgSetValue(cg.comparison(comparison));
		
		return null;
	}
	

	@Override
	public Void visit(LogicalOperation logicalOperation, Void params) {
		
		logicalOperation.getLeft().accept(this, params);
		logicalOperation.getRight().accept(this,  params);		
		
		logicalOperation.cgSetValue(cg.logicalOperation(logicalOperation));
		
		return null;
	}
	

	@Override
	public Void visit(ArrayAccess arrayAccess, Void params) {
		
		arrayAccess.accept(addressVisitor, null);
		arrayAccess.cgSetValue(arrayAccess.cgGetAddress());
		
		arrayAccess.cgAppendValue(cg.load(arrayAccess.getType()));
		
		return null;
	}

	@Override
	public Void visit(AttributeAccess attributeAccess, Void params) {
		
		attributeAccess.accept(addressVisitor, null);
		attributeAccess.cgSetValue(attributeAccess.cgGetAddress());
		
		attributeAccess.cgAppendValue(cg.load(attributeAccess.getType()));
		
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
		
		functionInvocation.cgSetValue(cg.line(functionInvocation));
		
		for (Expression arg : functionInvocation.getArguments()) {
			arg.accept(this, null);
			functionInvocation.cgAppendValue(arg.cgGetValue());
		}
		
		functionInvocation.cgAppendValue(cg.call(functionInvocation));		
		return null;
	}

	

	@Override
	public Void visit(NotSign notSign, Void params) {
		
		Expression operand = notSign.getOperand();
		operand.accept(this, null);
		notSign.cgSetValue(operand.cgGetValue());
		notSign.cgAppendValue(cg.not());
		
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
