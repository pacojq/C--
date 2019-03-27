package syntax;

import semantics.util.Visitor;

public interface ASTNode {

	public int getLine();
	public int getColumn();
	
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params);
	
}
