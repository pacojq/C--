package syntax;

public interface Statement extends ASTNode {
	
	// Code generation
	public String cgGetExecute();
	public void cgAppendExecute(String execute, Object... format);
	public void cgSetExecute(String execute, Object... format);
	
}
