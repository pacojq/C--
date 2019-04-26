package code;

import syntax.Definition;
import syntax.Expression;
import syntax.expressions.ArrayAccess;
import syntax.expressions.AttributeAccess;
import syntax.expressions.Cast;
import syntax.expressions.CharLiteral;
import syntax.expressions.DoubleLiteral;
import syntax.expressions.FunctionInvocation;
import syntax.expressions.IntLiteral;
import syntax.expressions.NotSign;
import syntax.expressions.UnaryMinus;
import syntax.expressions.Variable;
import syntax.expressions.binary.Arithmetic;
import syntax.expressions.binary.Comparison;
import syntax.expressions.binary.LogicalOperation;
import visitor.AbstractVisitor;

public class ValueVisitor extends CGVisitor<Void, Void> {

	
	// WE ONLY DEAL WITH EXPRESSIONS //
	
	
	@Override
	public Void visit(Arithmetic arithmetic, Void params) {
		
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
				charLiteral.getType().cgSufix(), 
				(int) charLiteral.getValue());
		return null;
	}
	
	@Override
	public Void visit(IntLiteral intLiteral, Void params) {
		intLiteral.cgSetValue("\tpush%s\t%s", 
				intLiteral.getType().cgSufix(), 
				(int) intLiteral.getValue());
		return null;
	}
	
	@Override
	public Void visit(DoubleLiteral doubleLiteral, Void params) {
		doubleLiteral.cgSetValue("\tpush%s\t%s", 
				doubleLiteral.getType().cgSufix(), 
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

		variable.cgSetValue("");
		Definition def = variable.getDefinition();
		
		variable.cgSetValue("push%s\t%s", def.getType().cgSufix(), variable.cgGetAddress());
		//variable.cgSetValue("pusha %s", def.getOffset());
		
		
		return null;
	}
	
	
	
	
	
}
