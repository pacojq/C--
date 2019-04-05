package semantics;

import errors.ErrorHandler;
import semantics.util.AbstractVisitor;
import syntax.*;
import syntax.statements.*;
import syntax.types.FunctionType;


public class ReturnTypeVisitor extends AbstractVisitor<Type, Void> {

	
	
	@Override
	public Void visit(FunctionDefinition functionDefinition, Type params) {
		functionDefinition.getType().accept(this, params);
		for (Statement st : functionDefinition.getStatements())
			st.accept(this, functionDefinition.getType()); // we pass the return type
		return null;
	}
	

	@Override
	public Void visit(Return returnStmnt, Type params) {
		super.visit(returnStmnt, params);
		
		FunctionType ft = (FunctionType) params;
		Type returnType = ft.getReturnType();
		
		if (!returnStmnt.getValue().getType().isEquivalent(returnType)) {
			ErrorHandler.getInstance().raiseError(
					returnStmnt.getLine(),
					returnStmnt.getColumn(),
					"Input type not matching!");
		}
		
		return null;
	}
	
}
