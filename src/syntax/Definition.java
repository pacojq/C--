package syntax;

public interface Definition extends ASTNode {

	public String getName();
	public Type getType();
	
	public int getScope();
	public void setScope(int scope);
	
	public int getOffset();
	public void setOffset(int offset);
	
	// Code generation
	public String cgGetExecute();
	public void cgAppendExecute(String execute, Object... format);
	public void cgSetExecute(String execute, Object... format);
	
}
