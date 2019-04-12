package code;

import syntax.Definition;
import syntax.expressions.Variable;

public class ValueVisitor extends CGVisitor {

	
	
	@Override
	public Void visit(Variable variable, Void params) {

		variable.cgValue = "";
		Definition def = variable.getDefinition();
		
		variable.cgValue = String.format(
				"push%s %s\n", def.getType().cgSufix(), variable.cgAdress
			);		
		variable.cgValue = String.format("pusha %s\n", def.getOffset());
		
		return null;
	}
	
}
