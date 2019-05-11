package syntax.types;

import syntax.Type;
import visitor.Visitor;

public class CharType extends AbstractType {

	public final static String NAME = "CharType";
	
	
	public CharType(int line, int column) {
		super(line, column);
	}

	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String toString() {
		return "char";
	}
	
	
	
	
	@Override
	public Type arithmetic(Type other) {
		
		switch (other.getName()) {		
			case CharType.NAME: return new IntType(getLine(), getColumn());
			case IntType.NAME: return other;
			case DoubleType.NAME: return other;		
		}
		return super.arithmetic(other);
	}
	
	@Override
	public Type cast(Type other) {
		
		switch (other.getName()) {		
			case CharType.NAME: return this;
			case IntType.NAME: return other;
			case DoubleType.NAME: return other;		
		}
		return super.cast(other);
	}
	
	
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}


	@Override
	public int numberOfBytes() {
		return 1;
	}
	
	@Override
	public String cgSuffix() {
		return "b";
	}
}
