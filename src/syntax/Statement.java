package syntax;

public interface Statement extends ASTNode {

	Definition getContainer();
	void setContainer(Definition definition);
	
}
