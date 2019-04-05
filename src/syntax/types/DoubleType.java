package syntax.types;

import semantics.util.Visitor;
import syntax.Type;

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
	public Type arithmetic(Type other) {
		
		switch (other.getName()) {		
			case CharType.NAME: return this;
			case IntType.NAME: return this;
			case DoubleType.NAME: return this;		
		}
		return super.arithmetic(other);
	}
	
	@Override
	public Type cast(Type other) {
		
		switch (other.getName()) {		
			case CharType.NAME: return other;
			case IntType.NAME: return other;
			case DoubleType.NAME: return this;		
		}
		return super.cast(other);
	}
	
	
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}
	
}
