package syntax;

public interface Expression extends ASTNode {

	Type getType();
	void setType(Type type);
	
	boolean getLValue();
	void setLValue(boolean lvalue);
	
	
	// Code Generation
	
	String cgGetAddress();
	void cgAppendAddress(String address);
	void cgSetAddress(String address);
	
	String cgGetValue();
	void cgAppendValue(String value);
	void cgSetValue(String value);
}
