package syntax.expressions;

import syntax.AbstractASTNode;
import syntax.Expression;
import syntax.Type;

public abstract class AbstractExpression extends AbstractASTNode implements Expression {

	private Type type;
	private boolean lvalue;
	
	
	// Code Generation
	private String cgAddress = "ADDRESS-PLACEHOLDER";
	private String cgValue = "VALUE-PLACEHOLDER";
	

	public AbstractExpression(int line, int column) {
		super(line, column);
		lvalue = false;
		type = null;
	}

	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public boolean getLValue() {
		return lvalue;
	}

	@Override
	public void setLValue(boolean lvalue) {
		this.lvalue = lvalue;
	}
	
	
	
	
	
	
	
	// Code generation //
	
	@Override
	public String cgGetAddress() {
		return cgAddress;
	}

	@Override
	public void cgAppendAddress(String address) {
		if (!getLValue())
			throw new IllegalStateException("You cannot set address to an expression which is not L-Value.");
		if (address.isEmpty())
			return;
		if (!address.endsWith("\n"))
			address += "\n";
		cgAddress += address;
	}

	@Override
	public void cgSetAddress(String address) {
		if (!getLValue())
			throw new IllegalStateException("You cannot set address to an expression which is not L-Value.");
		if (!address.endsWith("\n"))
			address += "\n";
		cgAddress = address;
	}

	@Override
	public String cgGetValue() {
		return cgValue;
	}

	@Override
	public void cgAppendValue(String value) {
		if (value.isEmpty())
			return;
		if (!value.endsWith("\n"))
			value += "\n";
		cgValue += value;
	}

	@Override
	public void cgSetValue(String value) {
		if (!value.endsWith("\n"))
			value += "\n";
		cgValue = value;
	}

}
