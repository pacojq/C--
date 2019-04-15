package code;

import syntax.Definition;
import syntax.expressions.Variable;
import visitor.AbstractVisitor;

public class ValueVisitor extends AbstractVisitor<Void, Void> {//TODO CGVisitor {

	
	
	@Override
	public Void visit(Variable variable, Void params) {

		variable.cgSetValue("");
		Definition def = variable.getDefinition();
		
		variable.cgSetValue("push%s %s", def.getType().cgSufix(), variable.cgGetAddress());		
		//variable.cgSetValue("pusha %s", def.getOffset());
		
		return null;
	}
	
}
