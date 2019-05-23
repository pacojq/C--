package syntax;

import java.util.List;

public interface Type extends ASTNode {
	
	String getName();
	int numberOfBytes();
		
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

	Type attributeAccess(String attribute);
	Type parenthesesOperator(int line, int column, List<Type> arguments);
	
	
	// Code generation
	String cgSuffix();
	String cgConvert(Type other);


}
