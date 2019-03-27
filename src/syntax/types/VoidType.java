package syntax.types;

import semantics.util.Visitor;

public class VoidType extends AbstractType {

	
	public VoidType(int line, int column) {
		super(line, column);
	}

	
	
	@Override
	public String getName() {
		return "VoidType";
	}
	
	@Override
	public String toString() {
		return "void";
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
}
