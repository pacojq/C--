package errors;

import java.util.List;

import semantics.util.Visitor;
import syntax.Type;
import syntax.types.AbstractType;

public class ErrorType extends AbstractType {

	public final static String NAME = "ErrorType";
	
	
	private String description;
	
	
	public ErrorType(int line, int column, String description) {
		super(line, column);
		this.description = new String(description);
	}
	
	public String getDescription() {
		return new String(description);
	}
	
	

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String toString() {
		return String.format("ERROR | line %s:%s\t%s", getLine(), getColumn(), description);
	}
	
	@Override
	public boolean isEquivalent(Type other) {
		return true;
	}
	
	
	
	@Override
	public Type arithmetic(Type other) {
		return this;
	}
	
	@Override
	public Type comparison(Type other) {
		return this;
	}
	
	@Override
	public Type logicalOperation(Type other) {
		return this;
	}

	@Override
	public Type squareBrackets(Type other) {
		return this;
	}
	
	@Override
	public Type cast(Type other) {
		return this;
	}


	@Override
	public Type unaryMinus() {
		return this;
	}


	@Override
	public Type not() {
		return this;
	}
	
	@Override
	public Type attributeAccess(String attribute) {
		return this;
	}
	
	@Override
	public Type parenthesesOperator(int line, int column, List<Type> arguments) {
		return this;
	}

}
