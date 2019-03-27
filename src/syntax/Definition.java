package syntax;

public interface Definition extends ASTNode {

	public String getName();
	public Type getType();
	
	public int getScope();
	public void setScope(int scope);
	
}
