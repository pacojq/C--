package errors;

import semantics.util.Visitor;
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
		return String.format("ERROR [%s : %s] : %s", getLine(), getColumn(), description);
	}

}
