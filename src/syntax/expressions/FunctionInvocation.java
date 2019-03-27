package syntax.expressions;

import java.util.ArrayList;
import java.util.List;

import semantics.util.Visitor;
import syntax.Expression;
import syntax.Statement;

public class FunctionInvocation extends AbstractExpression implements Statement {

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
	
}
