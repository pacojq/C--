package syntax.expressions;

import java.util.ArrayList;
import java.util.List;

import syntax.Expression;
import syntax.Statement;
import visitor.Visitor;

public class FunctionInvocation extends AbstractExpression implements Statement {
	
	private Variable function;
	private List<Expression> args;
	
	// Code generation
	private String cgExecute;
	
	public FunctionInvocation(Variable function, List<Expression> args) {
		super(function.getLine(), function.getColumn());
		this.function = function;
		this.args = new ArrayList<Expression>(args);
	}
	
	
	public Variable getFunction() {
		return function;
	}
	
	public List<Expression> getArguments() {
		return new ArrayList<Expression>(args);
	}
	
	
	
	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP params) {
		return visitor.visit(this, params);
	}

	
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(function.getName());
		str.append("(");
		
		if (args.size() > 0) {
			str.append(args.get(0).toString());
			for (int i = 1; i < args.size(); i ++)
				str.append(", " + args.get(i));
		}
		
		str.append(")");
		return str.toString();
	}
	
	

	@Override
	public String cgGetExecute() {
		return cgExecute;
	}


	@Override
	public void cgAppendExecute(String execute, Object... format) {
		if (!execute.endsWith("\n"))
			execute += "\n";
		cgExecute += String.format(execute, format);
	}


	@Override
	public void cgSetExecute(String execute, Object... format) {
		if (!execute.endsWith("\n"))
			execute += "\n";
		cgExecute = String.format(execute, format);
	}


}
