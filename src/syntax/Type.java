package syntax;

public interface Type extends ASTNode {
	
	String getName();
	// TODO int getNumberOfBytes();
	
	
	// Operations
	
	boolean isLogical();
	boolean isEquivalent(Type other);
	
	Type arithmetic(Type other);
	Type comparison(Type other);
	Type logicalOperation(Type other);
	Type squareBrackets(Type other);
	Type cast(Type other);
	
	Type unaryMinus();
	Type not();


	

}
