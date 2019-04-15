package syntax;

public interface Expression extends ASTNode {

	Type getType();
	void setType(Type type);
	
	boolean getLValue();
	void setLValue(boolean lvalue);
	
	
	// Code Generation
	
	String cgGetAddress();
	void cgAppendAddress(String address, Object... format);
	void cgSetAddress(String address, Object... format);
	
	String cgGetValue();
	void cgAppendValue(String value, Object... format);
	void cgSetValue(String value, Object... format);
}
