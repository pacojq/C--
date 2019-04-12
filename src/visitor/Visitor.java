package visitor;

import errors.ErrorType;
import syntax.Program;
import syntax.expressions.*;
import syntax.expressions.binary.*;
import syntax.types.*;
import syntax.statements.*;

public interface Visitor<TP, TR> /* TP = inherited; TR = synthesized */ {

	
	public TR visit(Program p, TP params);
	
	public TR visit(ErrorType error, TP params);
	
	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/*														 */
	/*						  TYPES							 */
	/*														 */
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public TR visit(ArrayType arrayType, TP params);
	public TR visit(CharType charType, TP params);
	public TR visit(DoubleType doubleType, TP params);
	public TR visit(FunctionType functionType, TP params);
	public TR visit(IntType intType, TP params);
	public TR visit(StructType structType, TP params);
	public TR visit(VoidType voidType, TP params);
	
	
	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/*														 */
	/*					   STATEMENTS						 */
	/*														 */
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public TR visit(Assignment assignment, TP params);
	public TR visit(FunctionDefinition functionDefinition, TP params);
	public TR visit(IfStatement ifStatement, TP params);
	public TR visit(Read read, TP params);
	public TR visit(Return returnStmnt, TP params);
	public TR visit(VariableDefinition variableDefinition, TP params);
	public TR visit(WhileLoop whileLoop, TP params);
	public TR visit(Write write, TP params);
	
	
	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/*														 */
	/*					   EXPRESSIONS						 */
	/*														 */
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public TR visit(Arithmetic arithmetic, TP params);
	public TR visit(Comparison comparison, TP params);
	public TR visit(LogicalOperation logicalOperation, TP params);
	public TR visit(ArrayAccess arrayAccess, TP params);
	public TR visit(AttributeAccess attributeAccess, TP params);
	public TR visit(Cast cast, TP params);
	public TR visit(CharLiteral charLiteral, TP params);
	public TR visit(DoubleLiteral doubleLiteral, TP params);
	public TR visit(FunctionInvocation functionInvocation, TP params);
	public TR visit(IntLiteral intLiteral, TP params);
	public TR visit(NotSign notSign, TP params);
	public TR visit(UnaryMinus unaryMinus, TP params);
	public TR visit(Variable variable, TP params);
	
	
}
