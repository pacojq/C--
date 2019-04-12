package code;

import errors.ErrorType;
import syntax.Program;
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
import syntax.statements.Assignment;
import syntax.statements.FunctionDefinition;
import syntax.statements.IfStatement;
import syntax.statements.Read;
import syntax.statements.Return;
import syntax.statements.VariableDefinition;
import syntax.statements.WhileLoop;
import syntax.statements.Write;
import syntax.types.ArrayType;
import syntax.types.CharType;
import syntax.types.DoubleType;
import syntax.types.FunctionType;
import syntax.types.IntType;
import syntax.types.StructType;
import syntax.types.VoidType;
import visitor.Visitor;

public abstract class CGVisitor implements Visitor<Void, Void> {

	@Override
	public Void visit(Program p, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(ErrorType error, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(ArrayType arrayType, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(CharType charType, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(DoubleType doubleType, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(FunctionType functionType, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(IntType intType, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(StructType structType, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(VoidType voidType, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(Assignment assignment, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(FunctionDefinition functionDefinition, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(IfStatement ifStatement, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(Read read, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(Return returnStmnt, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(VariableDefinition variableDefinition, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(WhileLoop whileLoop, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(Write write, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(Arithmetic arithmetic, Void params) {
		throw new IllegalStateException("ar");
	}

	@Override
	public Void visit(Comparison comparison, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(LogicalOperation logicalOperation, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(ArrayAccess arrayAccess, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(AttributeAccess attributeAccess, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(Cast cast, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(CharLiteral charLiteral, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(DoubleLiteral doubleLiteral, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(FunctionInvocation functionInvocation, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(IntLiteral intLiteral, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(NotSign notSign, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(UnaryMinus unaryMinus, Void params) {
		throw new IllegalStateException();
	}

	@Override
	public Void visit(Variable variable, Void params) {
		throw new IllegalStateException();
	}

}
