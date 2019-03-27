package syntax;

public interface Expression extends ASTNode {

	Type getType();
	void setType(Type type);
	
	boolean getLValue();
	void setLValue(boolean lvalue);
	
}
