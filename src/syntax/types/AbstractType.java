package syntax.types;

import java.util.List;

import errors.ErrorHandler;
import errors.ErrorType;
import syntax.AbstractASTNode;
import syntax.Type;

public abstract class AbstractType extends AbstractASTNode implements Type {

	
	public AbstractType(int line, int column) {
		super(line, column);
	}

	

	@Override
	public boolean isLogical() {
		return false;
	}
	
	@Override
	public boolean isEquivalent(Type other) {
		return getName().equals(other.getName());
	}
	
	
	
	
	
	// Helper methods
	
	private Type defaultOperation(Type other, String opperation) {
		if (other.getName().equals(ErrorType.NAME))
			return other;
		return defaultOperation(opperation);
	}
	
	private Type defaultOperation(String opperation) {
		return ErrorHandler.getInstance().raiseError(
					getLine(), 
					getColumn(), 
					String.format("The type %s does not support %s.", this.toString(), opperation));
	}
	
	
	
	
	// - - - - - - - - - OPERATIONS - - - - - - - - - 

	@Override
	public Type arithmetic(Type other) {
		return defaultOperation(other, "arithmetic operations with " + other.toString());
	}
	
	@Override
	public Type comparison(Type other) {
		return defaultOperation(other, "comparisons with " + other.toString());
	}
	
	@Override
	public Type logicalOperation(Type other) {
		return defaultOperation(other, "logical operations with " + other.toString());
	}

	@Override
	public Type squareBrackets(Type other) {
		return defaultOperation(other, "indexing");
	}
	
	@Override
	public Type cast(Type other) {
		return defaultOperation(other, "casts to " + other.toString());
	}


	@Override
	public Type unaryMinus() {
		return defaultOperation("a unary minus operation");
	}


	@Override
	public Type not() {
		return defaultOperation("a not operation");
	}
	
	@Override
	public Type attributeAccess(String attribute) {
		return defaultOperation("an attribute access");
	}
	
	@Override
	public Type parenthesesOperator(int line, int column, List<Type> arguments) {
		return defaultOperation("parentheses operator");
	}
	
	
	
	// - - - - - - - - - CODE GENERATION - - - - - - - - - 

	

	@Override
	public String cgSufix() {
		return "";
	}
}
