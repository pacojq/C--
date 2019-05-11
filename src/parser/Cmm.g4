grammar Cmm;	

@header {
	import parser.*;
	
	import syntax.*;
	import syntax.expressions.*;
	import syntax.expressions.binary.*;
	import syntax.statements.*;
	import syntax.types.*;
	
	import java.util.*;
}


program returns [Program ast, List<Definition> def = new ArrayList<Definition>()]:
		(d=definition { $def.addAll($d.ast); })* main { $def.add($main.ast); } EOF
		{ $ast = new Program(0, 0, $def); }
	;


main returns [FunctionDefinition ast, FunctionType fType]:
		v=voidType 'main' '(' ')' fnBlock 
		{ $ast = new FunctionDefinition($v.ast.getLine(), $v.ast.getColumn(), "main", new FunctionType($v.ast), $fnBlock.ast); }
	;




// * * * * * * * * *   TYPES   * * * * * * * * * * //



type returns [Type ast]:

		primitive { $ast = $primitive.ast; }
		
	// Arrays
	| 	t=type '[' i=INT_CONSTANT ']'
		{ $ast = ArrayType.buildArray(Integer.parseInt($i.text), $t.ast); }
		('[' i2=INT_CONSTANT ']' { $ast = ArrayType.buildArray( Integer.parseInt($i2.text), $ast); } )*
		
	| 	structType { $ast = $structType.ast; }
	;
		


structType returns [StructType ast, List<VariableDefinition> defs=new ArrayList<VariableDefinition>()]:
		s='struct' '{' (variableDef { $defs.addAll($variableDef.ast); })* '}' 
		{ $ast = new StructType($s.getLine(), $s.getCharPositionInLine()+1, $defs); } 
	;

  	
primitive returns [Type ast]:
		
		i='int' { $ast = new IntType($i.getLine(), $i.getCharPositionInLine()+1); }
	
	| 	c='char' { $ast = new CharType($c.getLine(), $c.getCharPositionInLine()+1); }
	
	| 	d='double' { $ast = new DoubleType($d.getLine(), $d.getCharPositionInLine()+1); }
	;


voidType returns [Type ast]:
		v='void' { $ast = new VoidType($v.getLine(), $v.getCharPositionInLine()+1); }
	;


funcReturnType returns [Type ast]:

		primitive {$ast = $primitive.ast; }
		
	| 	voidType {$ast = $voidType.ast; }
	;




// * * * * * * * * *   DEFINITIONS   * * * * * * * * * * //


definition returns [List<Definition> ast = new ArrayList<Definition>()]:

		fuctionDef { $ast.add($fuctionDef.ast); }
		
	| 	variableDef { $ast.addAll($variableDef.ast); }
	;

  	
variableDef returns [List<VariableDefinition> ast = new ArrayList<VariableDefinition>()]: 

	// int a; char b, c;
		t=type id1=ID 
		{$ast.add(new VariableDefinition($t.ast.getLine(), $t.ast.getColumn(), $id1.text, $type.ast));}
		(',' id2=ID {$ast.add(new VariableDefinition($t.ast.getLine(), $t.ast.getColumn(), $id2.text, $type.ast));} )* ';'
	;
		
	
		
fuctionDef returns [FunctionDefinition ast, List<VariableDefinition> p = new ArrayList<VariableDefinition>()]: 
	
	// Function Definition
		t=funcReturnType ID '(' (params { $p.addAll($params.ast); } )? ')' fnBlock
		{ $ast = new FunctionDefinition($t.ast.getLine(), $t.ast.getColumn(), $ID.text, new FunctionType($t.ast, $p), $fnBlock.ast); }
	;
		

params returns [List<VariableDefinition> ast = new ArrayList<VariableDefinition>()]: 

		t1=primitive id1=ID 
		{$ast.add(new VariableDefinition($t1.ast.getLine(), $t1.ast.getColumn(), $id1.text, $t1.ast));}
		(',' t2=primitive id2=ID
			{$ast.add(new VariableDefinition($t2.ast.getLine(), $t2.ast.getColumn(), $id2.text, $t2.ast));}
		)*
	;
		




// * * * * * * * * *   STATEMENTS   * * * * * * * * * * //


funcInvocation returns [FunctionInvocation ast, List<Expression> args = new ArrayList<Expression>()]:
		func=variable '(' ( e1=expression { $args.add($e1.ast); } (',' e2=expression { $args.add($e2.ast); })* )? ')'
 		{ $ast = new FunctionInvocation($func.ast, $args); }
 	;
 
  	
block returns [List<Statement> ast = new ArrayList<Statement>()]:

		st1=statement { $ast.addAll($st1.ast); }
		
	| 	'{' (st2=statement { $ast.addAll($st2.ast); })* '}'
	;
		
		
fnBlock returns [List<Statement> ast = new ArrayList<Statement>()]: 
		'{'
			(variableDef { $ast.addAll($variableDef.ast); })*
			(statement { $ast.addAll($statement.ast); })*
		'}'
	;
	
	
		 
statement returns [List<Statement> ast = new ArrayList<Statement>()]:

	// While loop
		w='while' '(' cond=expression ')' b=block
		{ $ast.add(new WhileLoop($w.getLine(), $w.getCharPositionInLine()+1, $cond.ast, $b.ast)); }
		
	// If-else
	| 	i='if' '(' cond=expression ')' b1=block
		{ $ast.add(new IfStatement($i.getLine(), $i.getCharPositionInLine()+1, $cond.ast, $b1.ast)); }
		('else' b2=block { ((IfStatement)$ast.get(0)).setElsePart($b2.ast); })?	
	
	// Assignment
	| 	e1=expression '=' e2=expression ';'
		{ $ast.add(new Assignment($e1.ast, $e2.ast)); }
  	
  	// Return statement
  	| 	r='return' e=expression ';'
  		{ $ast.add(new Return($r.getLine(), $r.getCharPositionInLine()+1, $e.ast)); }
  	
  	// Read and Write
  	|	readWrite
  		{ $ast.add($readWrite.ast); }
  	
  	| 	funcInvocation ';'
  		{ $ast.add($funcInvocation.ast); }
  	;
  		



// TODO ? read write syntactic sugar 
  		
readWrite returns [Statement ast, List<Expression> expr = new ArrayList<Expression>()]:
 		
 	// Write
 		w='write' e1=expression { $expr.add($e1.ast); } (',' e2=expression { $expr.add($e2.ast); } )* ';'
  		{ $ast = new Write($w.getLine(), $w.getCharPositionInLine()+1, $expr); }
  		
  	// Read
  	| 	r='read' e1=expression { $expr.add($e1.ast); } (',' e2=expression { $expr.add($e2.ast); })* ';'
  		{ $ast = new Read($r.getLine(), $r.getCharPositionInLine()+1, $expr); }
  	;





// * * * * * * * * *   EXPRESSIONS   * * * * * * * * * * //

variable returns [Variable ast]:		
		ID { $ast = new Variable($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.text); }
	;



expression returns [Expression ast]:

		variable 
		{ $ast = $variable.ast; }
		
	| 	i=INT_CONSTANT
		{ $ast = new IntLiteral($i.getLine(), $i.getCharPositionInLine()+1, LexerHelper.lexemeToInt($i.text)); }
	
	| 	r=REAL_CONSTANT
		{ $ast = new DoubleLiteral($r.getLine(), $r.getCharPositionInLine()+1, LexerHelper.lexemeToReal($r.text)); }
		
	| 	c=CHAR_CONSTANT
		{ $ast = new CharLiteral($c.getLine(), $c.getCharPositionInLine()+1,LexerHelper.lexemeToChar($c.text)); }
	
	| 	funcInvocation
		{ $ast = $funcInvocation.ast; }
	
	| 	'(' e=expression ')' 
		{ $ast = $e.ast; }
	
	// Array access
	| 	e1=expression '[' e2=expression ']'
		{ $ast = new ArrayAccess($e1.ast, $e2.ast); }
	
	// Attribute access
	| 	e=expression '.' att=ID
		{ $ast = new AttributeAccess($e.ast, $att.text); }
	
	// Unary minus
	| 	um='-' expression
		{ $ast = new UnaryMinus($um.getLine(), $um.getCharPositionInLine()+1, $expression.ast); }
	
	// Negation
	| 	not='!' expression
		{ $ast = new NotSign($not.getLine(), $not.getCharPositionInLine()+1, $expression.ast); }
	
	| 	e1=expression op=('*'|'/'|'%') e2=expression
		{ $ast = new Arithmetic($op.text, $e1.ast, $e2.ast); }
	
	|	e1=expression op=('+'|'-') e2=expression
		{ $ast = new Arithmetic($op.text, $e1.ast, $e2.ast); }
	
	// Cast
	| 	p='(' t=primitive ')' e=expression
		{ $ast = new Cast($p.getLine(), $p.getCharPositionInLine()+1, $e.ast, $t.ast); }
	
	// Logical comparison
	| 	e1=expression op=('>'|'<'|'>='|'<='|'=='|'!=') e2=expression
		{ $ast = new Comparison($op.text, $e1.ast, $e2.ast); }
	
	// And-Or
	| 	e1=expression op=('&&'|'||') e2=expression
		{ $ast = new LogicalOperation($op.text, $e1.ast, $e2.ast); }
	;













// * * * * * * * * * * * * * * * * * * * * * * * * * * * * //
// 														   // 	
// 						LEXER RULES 					   //
// 														   //	
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * //
  		 
fragment
DIGIT: [0-9]
	 ;

fragment
LETTER: [a-zA-Z]
	  ;
	  
fragment
REAL: INT_CONSTANT? '.' DIGIT+
	| INT_CONSTANT '.' DIGIT*
	;


ID: ('_' | LETTER) ('_' | DIGIT | LETTER)*
  ;

  		 
INT_CONSTANT: '0'
			| [1-9] [0-9]* 
            ;

REAL_CONSTANT: REAL | INT_CONSTANT
			 | (REAL | INT_CONSTANT) ('e' | 'E') '-'? INT_CONSTANT
			 ;
			 			 
CHAR_CONSTANT: '\'' . '\''
			 | '\'\\' ([tnr] | '\\' | '\'')  '\''
			 | '\'\\' INT_CONSTANT '\''
			 ;
			 	 
			 
SL_COMMENT: '//' .*? ('\r' | '\n' | EOF) -> skip
	      ;
	   
ML_COMMENT: '/*' .*? '*/' -> skip
	      ;
	   
BLANKS: (' ' | '\t' | '\r' | '\n')+ -> skip
	   ;


