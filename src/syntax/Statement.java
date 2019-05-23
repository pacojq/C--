package syntax;

public interface Statement extends ASTNode {
	
	// Code generation
	public String cgGetExecute();
	public void cgAppendExecute(String execute);
	public void cgSetExecute(String execute);
	
}
