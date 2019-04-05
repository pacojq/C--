package semantics;

import java.util.ArrayList;
import java.util.List;

import errors.ErrorHandler;
import errors.ErrorType;
import semantics.util.AbstractVisitor;
import syntax.Definition;
import syntax.Expression;
import syntax.Type;
import syntax.expressions.ArrayAccess;
import syntax.expressions.AttributeAccess;
import syntax.expressions.Cast;
import syntax.expressions.FunctionInvocation;
import syntax.expressions.Variable;
import syntax.expressions.binary.Arithmetic;
import syntax.expressions.binary.Comparison;
import syntax.expressions.binary.LogicalOperation;
import syntax.statements.Assignment;
import syntax.statements.FunctionDefinition;
import syntax.statements.IfStatement;
import syntax.statements.Return;
import syntax.statements.WhileLoop;
import syntax.types.FunctionType;

/**
 * TP -> Type: the type of the parent node
 * TR -> Void
 */
public class TypeCheckingVisitor extends AbstractVisitor<Type, Void> {

	

	
	@Override
	public Void visit(Variable variable, Type params) {
		super.visit(variable, variable.getType());
		variable.setType(variable.getDefinition().getType());		
		return null;
	}
	
	
	@Override
	public Void visit(FunctionInvocation functionInvocation, Type params) {
		
		super.visit(functionInvocation, params);
		
		Definition def = functionInvocation.getFunction().getDefinition();
		List<Type> arguments = new ArrayList<Type>();
		for (Expression expr : functionInvocation.getArguments())
			arguments.add(expr.getType());
		
		functionInvocation.setType( 
				def.getType().parenthesesOperator(
						functionInvocation.getLine(),
						functionInvocation.getColumn(),
						arguments) 
				);
		return null;
	}
	
	
	
	
	
	
	
	@Override
	public Void visit(Arithmetic arithmetic, Type params) {
		super.visit(arithmetic, params);
		arithmetic.setType( 
				arithmetic.getLeft().getType()
				.arithmetic(arithmetic.getRight().getType()) 
			);		
		return null;
	}
	
	
	@Override
	public Void visit(LogicalOperation logicalOperation, Type params) {
		super.visit(logicalOperation, params);
		logicalOperation.setType( 
				logicalOperation.getLeft().getType()
				.logicalOperation(logicalOperation.getRight().getType()) 
			);		
		return null;
	}
	
	
	@Override
	public Void visit(Comparison comparison, Type params) {
		
		super.visit(comparison, params);
		comparison.setType( 
				comparison.getLeft().getType()
				.comparison(comparison.getRight().getType()) 
			);
		return null;
	}
	
	
	
	
	
	
	
	@Override
	public Void visit(Assignment assignment, Type params) {
		super.visit(assignment, params);
		Type t1 = assignment.getLeft().getType();
		Type t2 = assignment.getRight().getType();
		
		if (!t1.isEquivalent(t2)) {
			ErrorHandler.getInstance().raiseError(
				assignment.getLine(), 
				assignment.getColumn(),
				String.format(
					"Cannot assign type %s to an expression declared as %s.", t2, t1)
				);
		}
		return null;
	}
	
	
	
	@Override
	public Void visit(Cast cast, Type params) {
		
		super.visit(cast, params);
		cast.setType( 
				cast.getOperand().getType().cast(cast.getTargetType())
			);
		return null;
	}
	
	
	
	@Override
	public Void visit(AttributeAccess attributeAccess, Type params) {
		super.visit(attributeAccess, params);
		attributeAccess.setType( 
				attributeAccess.getExpression().getType()
				.attributeAccess(attributeAccess.getAttributeName()) 
			);
		
		return null;
	}
	
	
	
	@Override
	public Void visit(ArrayAccess arrayAccess, Type params) {
		super.visit(arrayAccess, params);
		arrayAccess.setType( 
				arrayAccess.getArray().getType()
				.squareBrackets(arrayAccess.getIndex().getType()) 
			);
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	// STATEMENTS HAVE NO TYPE!!!! WE'LL JUST CHECK, NOT ASSIGN.
	
	
	
	@Override
	public Void visit(IfStatement ifStatement, Type params) {
		super.visit(ifStatement, params);
		Type t = ifStatement.getCondition().getType();
		if (!t.isLogical()) {
			ErrorHandler.getInstance().raiseError(
					ifStatement.getLine(), 
					ifStatement.getColumn(),
					String.format(
						"Cannot evaluate the type %s as a logical expression.", t)
					);
		}
		return null;
	}
	
	
	@Override
	public Void visit(WhileLoop whileLoop, Type params) {
		super.visit(whileLoop, params);
		Type t = whileLoop.getCondition().getType();
		if (!t.isLogical()) {
			ErrorHandler.getInstance().raiseError(
					whileLoop.getLine(), 
					whileLoop.getColumn(),
					String.format(
						"Cannot evaluate the type %s as a logical expression.", t)
					);
		}
		return null;
	}
	
	
	

	
	@Override
	public Void visit(FunctionDefinition functionDefinition, Type params) {		
		super.visit(functionDefinition, functionDefinition.getType());
		return null;
	}
	
	
	@Override
	public Void visit(Return returnStmnt, Type params) {
		super.visit(returnStmnt, params);
		
		FunctionType ft = (FunctionType) params;
		Type retType = ft.getReturnType();
		Type actualRetType = returnStmnt.getValue().getType();
		
		if (actualRetType.getName().equals(ErrorType.NAME))
			return null; // We already have an error
		
		if (!actualRetType.isEquivalent(retType)) {
			ErrorHandler.getInstance().raiseError(
					returnStmnt.getLine(), 
					returnStmnt.getColumn(),
					String.format("Inconsistent return type. Actual: %s; Expected: %s",
							actualRetType, retType));	
		}
		
		return null;
	}
	
}
