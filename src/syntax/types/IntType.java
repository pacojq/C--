package syntax.types;

import code.CodeGenerator;
import syntax.Type;
import visitor.Visitor;

public class IntType extends AbstractType {

	public final static String NAME = "IntType";
	
	
	public IntType(int line, int column) {
		super(line, column);
	}
	
	
	@Override
	public boolean isLogical() {
		return true;
	}
	
	
	@Override
	public Type arithmetic(Type other) {
		
		switch (other.getName()) {
			case CharType.NAME: return this;
			case IntType.NAME: return this;
			case DoubleType.NAME: return other;		
		}
		return super.arithmetic(other);
	}
	
	
	@Override
	public Type comparison(Type other) {
		
		switch (other.getName()) {
			case CharType.NAME: return this;
			case IntType.NAME: return this;
			case DoubleType.NAME: return other;
		}
		return super.comparison(other);
	}
	
	
	@Override
	public Type logicalOperation(Type other) {
		
		if (other.isLogical()) {
			return this;
		}
		return super.logicalOperation(other);
	}
	
	
	@Override
	public Type cast(Type other) {
		
		switch (other.getName()) {		
			case CharType.NAME: return other;
			case IntType.NAME: return this;
			case DoubleType.NAME: return other;		
		}
		return super.cast(other);
	}
	
	
	
	
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String toString() {
		return "int";
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}



	@Override
	public int numberOfBytes() {
		return 2;
	}
	
	@Override
	public String cgSuffix() {
		return "i";
	}
	
	
	@Override
	public String cgConvert(Type other) {
		
		switch (other.getName()) {
			case CharType.NAME:
				return CodeGenerator.getInstance().i2b();
			case DoubleType.NAME:
				return CodeGenerator.getInstance().i2f();
		}
		return super.cgConvert(other);
	}
	
}
