package syntax.expressions;

import java.util.ArrayList;
import java.util.List;

import syntax.Definition;
import syntax.Expression;
import syntax.Statement;
import visitor.Visitor;

public class FunctionInvocation extends AbstractExpression implements Statement {

	private Definition container;
	
	private Variable function;
	private List<Expression> args;
	
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
	public Definition getContainer() {
		return container;
	}


	@Override
	public void setContainer(Definition definition) {
		this.container = definition;
	}
	
}
