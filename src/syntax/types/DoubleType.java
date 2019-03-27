package syntax.types;

import semantics.util.Visitor;

public class DoubleType extends AbstractType {

	public final static String NAME = "DoubleType";
	
	
	public DoubleType(int line, int column) {
		super(line, column);
	}
	
	
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String toString() {
		return "double";
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
