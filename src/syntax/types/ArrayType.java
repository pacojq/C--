package syntax.types;

import syntax.Type;
import visitor.Visitor;

public class ArrayType extends AbstractType {

	public final static String NAME = "ArrayType";
	
	
	private Type typeOf;
	private int size;
	
	
	public static ArrayType buildArray(int size, Type typeOf) {
		
		if (typeOf instanceof ArrayType) {			
			ArrayType other = (ArrayType) typeOf;
			other.setTypeOf(buildArray(size, other.getTypeOf()));
			return other;			
		}
		else return new ArrayType(size, typeOf);
	}
	
	
	private ArrayType(int size, Type typeOf) {
		super(typeOf.getLine(), typeOf.getColumn());
		this.typeOf = typeOf;
		this.size = size;
	}
	
	
	
	public Type getTypeOf() {
		return typeOf;
	}
	
	private void setTypeOf(Type typeOf) {
		this.typeOf = typeOf;
	}
	
	public int getSize() {
		return size;
	}
	
	
	
	@Override
	public Type squareBrackets(Type other) {
		if (other.getName().equals(IntType.NAME)) {
			return this.typeOf;
		}
		return super.squareBrackets(other);
	}
	
	
	
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String toString() {
		return String.format("%s[%s]", typeOf, size);
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}


	@Override
	public int numberOfBytes() {
		return this.size * this.typeOf.numberOfBytes();
	}

	
}
