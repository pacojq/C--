package visitor;

import errors.ErrorType;
import syntax.*;
import syntax.expressions.*;
import syntax.expressions.binary.*;
import syntax.statements.*;
import syntax.types.*;

public abstract class AbstractVisitor<TP, TR> implements Visitor<TP, TR> {

	
	@Override
	public TR visit(Program p, TP params) {
		for (Definition def : p.getDefinitions())
			def.accept(this, params);
		return null;
	}


	@Override
	public TR visit(ErrorType error, TP params) {
		return null;
	}
	
	
	
	
	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/*														 */
	/*						  TYPES							 */
	/*														 */
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	

	@Override
	public TR visit(ArrayType arrayType, TP params) {
		arrayType.getTypeOf().accept(this, params);
		return null;
	}

	@Override
	public TR visit(CharType charType, TP params) {
		return null;
	}

	@Override
	public TR visit(DoubleType doubleType, TP params) {
		return null;
	}

	@Override
	public TR visit(FunctionType functionType, TP params) {
		functionType.getReturnType().accept(this, params);
		for (VariableDefinition def : functionType.getParams())
			def.accept(this, params);
		return null;
	}

	@Override
	public TR visit(IntType intType, TP params) {
		return null;
	}

	@Override
	public TR visit(StructType structType, TP params) {
		for (VariableDefinition def : structType.getVariables())
			def.accept(this, params);
		return null;
	}

	@Override
	public TR visit(VoidType voidType, TP params) {
		return null;
	}

	
	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/*														 */
	/*					   STATEMENTS						 */
	/*														 */
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	
	@Override
	public TR visit(Assignment assignment, TP params) {
		assignment.getLeft().accept(this, params);
		assignment.getRight().accept(this, params);
		return null;
	}

	@Override
	public TR visit(FunctionDefinition functionDefinition, TP params) {
		functionDefinition.getType().accept(this, params);
		for (Statement st : functionDefinition.getStatements())
			st.accept(this, params);
		return null;
	}

	@Override
	public TR visit(IfStatement ifStatement, TP params) {
		ifStatement.getCondition().accept(this, params);
		for (Statement st : ifStatement.getIfPart())
			st.accept(this, params);
		for (Statement st : ifStatement.getElsePart())
			st.accept(this, params);
		return null;
	}

	@Override
	public TR visit(Read read, TP params) {
		for (Expression expr : read.getExpressions())
			expr.accept(this, params);
		return null;
	}

	@Override
	public TR visit(Return returnStmnt, TP params) {
		returnStmnt.getValue().accept(this, params);
		return null;
	}

	@Override
	public TR visit(VariableDefinition variableDefinition, TP params) {
		variableDefinition.getType().accept(this, params);
		return null;
	}

	@Override
	public TR visit(WhileLoop whileLoop, TP params) {
		whileLoop.getCondition().accept(this, params);
		for (Statement st : whileLoop.getStatements())
			st.accept(this, params);
		return null;
	}

	@Override
	public TR visit(Write write, TP params) {
		for (Expression expr : write.getExpressions())
			expr.accept(this, params);
		return null;
	}

	
	
	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/*														 */
	/*					   EXPRESSIONS						 */
	/*														 */
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	
	@Override
	public TR visit(Arithmetic arithmetic, TP params) {
		arithmetic.getLeft().accept(this, params);
		arithmetic.getRight().accept(this, params);
		return null;
	}

	@Override
	public TR visit(Comparison comparison, TP params) {
		comparison.getLeft().accept(this, params);
		comparison.getRight().accept(this, params);
		return null;
	}

	@Override
	public TR visit(LogicalOperation logicalOperation, TP params) {
		logicalOperation.getLeft().accept(this, params);
		logicalOperation.getRight().accept(this, params);
		return null;
	}

	@Override
	public TR visit(ArrayAccess arrayAccess, TP params) {
		arrayAccess.getArray().accept(this, params);
		arrayAccess.getIndex().accept(this, params);
		return null;
	}

	@Override
	public TR visit(AttributeAccess attributeAccess, TP params) {
		attributeAccess.getExpression().accept(this, params);
		return null;
	}

	@Override
	public TR visit(Cast cast, TP params) {
		cast.getTargetType().accept(this, params);
		cast.getOperand().accept(this, params);
		return null;
	}

	@Override
	public TR visit(CharLiteral charLiteral, TP params) {
		return null;
	}

	@Override
	public TR visit(DoubleLiteral doubleLiteral, TP params) {
		return null;
	}

	@Override
	public TR visit(FunctionInvocation functionInvocation, TP params) {
		functionInvocation.getFunction().accept(this, params);
		for (Expression expr : functionInvocation.getArguments())
			expr.accept(this, params);
		return null;
	}

	@Override
	public TR visit(IntLiteral intLiteral, TP params) {
		return null;
	}

	@Override
	public TR visit(NotSign notSign, TP params) {
		notSign.getOperand().accept(this, params);
		return null;
	}

	@Override
	public TR visit(UnaryMinus unaryMinus, TP params) {
		unaryMinus.getOperand().accept(this, params);
		return null;
	}

	@Override
	public TR visit(Variable variable, TP params) {
		return null;
	}


	

	
}
