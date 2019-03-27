package syntax.statements;

import java.util.ArrayList;
import java.util.List;

import semantics.util.Visitor;
import syntax.AbstractASTNode;
import syntax.Definition;
import syntax.Statement;
import syntax.Type;
import syntax.types.FunctionType;

public class FunctionDefinition extends AbstractASTNode implements Definition {

	private int scope;
	
	private String name;
	private FunctionType functionType;
	private List<Statement> statements;
	
	public FunctionDefinition(int line, int column, 
			String name, FunctionType functionType, List<Statement> statements) {
		
		super(line, column);
		this.name = name;
		this.functionType = functionType;
		this.statements = new ArrayList<Statement>(statements);
	}
	
	public String getName() {
		return new String(name);
	}
	
	public Type getType() {
		return functionType;
	}
	
	public List<Statement> getStatements() {
		return new ArrayList<Statement>(statements);
	}
	
	
	@Override
	public String toString() {
		String params = "";		
		List<VariableDefinition> list = functionType.getParams();
		if (list.size() > 0) {
			for (int i = 0; i < list.size()-1; i ++)
				params += list.get(i).toString() + ", ";
			params += list.get(list.size()-1).toString();
		}
			
		return String.format("FunctionDefinition [%s : %s]: %s %s(%s)", 
				getLine(),
				getColumn(),
				functionType.getReturnType(),
				name, 
				params);
	}
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}

	@Override
	public int getScope() {
		return scope;
	}
	
	@Override
	public void setScope(int scope) {
		this.scope = scope;
	}
	
}
