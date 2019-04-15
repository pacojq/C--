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

public abstract class CGVisitor<TP, TR> implements Visitor<TP, TR> {

	@Override
	public TR visit(Program p, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(ErrorType error, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(ArrayType arrayType, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(CharType charType, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(DoubleType doubleType, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(FunctionType functionType, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(IntType intType, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(StructType structType, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(VoidType voidType, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(Assignment assignment, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(FunctionDefinition functionDefinition, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(IfStatement ifStatement, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(Read read, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(Return returnStmnt, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(VariableDefinition variableDefinition, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(WhileLoop whileLoop, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(Write write, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(Arithmetic arithmetic, TP params) {
		throw new IllegalStateException("ar");
	}

	@Override
	public TR visit(Comparison comparison, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(LogicalOperation logicalOperation, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(ArrayAccess arrayAccess, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(AttributeAccess attributeAccess, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(Cast cast, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(CharLiteral charLiteral, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(DoubleLiteral doubleLiteral, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(FunctionInvocation functionInvocation, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(IntLiteral intLiteral, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(NotSign notSign, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(UnaryMinus unaryMinus, TP params) {
		throw new IllegalStateException();
	}

	@Override
	public TR visit(Variable variable, TP params) {
		throw new IllegalStateException();
	}

}
