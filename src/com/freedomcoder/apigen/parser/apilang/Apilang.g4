grammar Apilang;
@lexer::members {
    public static final int WHITESPACE = 3;
    public static final int COMMENTS = 2;
}
parse
 : importDeclaration* typeDeclaration* EOF
 ;

importDeclaration
	:	IMPORT STRING SEMI?
	;

typeDeclaration
	:	apiDeclaration
	|	objectDeclaration
	|	errorDeclaration
	;

typeModifier
	:	annotation
	|	PUBLIC
	|	PRIVATE
	;

objectDeclaration
	:	typeModifier* OBJECT ID OBRACE extFieldDeclaration* CBRACE SEMI? DESCRIPTION?
	;

errorDeclaration
	:	typeModifier* ERROR ID OBRACE extFieldDeclaration* CBRACE SEMI? DESCRIPTION?
	;

apiDeclaration
	:	typeModifier* API ID OBRACE apiTypeDeclaration* CBRACE
	;
	
apiTypeDeclaration
	:	apiGroupDeclaration
	|	apiMethodDeclaration
	|	objectDeclaration
	;
	
apiGroupDeclaration
	:	typeModifier* GROUP ID throwsDeclaration? OBRACE apiTypeDeclaration* CBRACE
	;

apiMethodDeclaration
	:	typeModifier* METHOD ID throwsDeclaration? OPAR fieldDeclaration* CPAR (OBRACE responseDeclaration* CBRACE | ARROW OBRACE extFieldDeclaration* CBRACE)
	;

throwsDeclaration
	:	THROWS ID (',' ID)*
	;

responseDeclaration
	:	typeModifier* ID OBRACE extFieldDeclaration* CBRACE
	;

extFieldDeclaration
	:	fieldDeclaration
	|	inlineObjectDeclaration
	;

inlineObjectDeclaration
	:	typeModifier* ID inlineTypeName ARRBRACK* OBRACE extFieldDeclaration* CBRACE SEMI? DESCRIPTION?
	;

fieldDeclaration
	:	fieldModifier* ID ARRBRACK* variableDeclaratorList SEMI DESCRIPTION?
	;

inlineTypeName
	:	LT ID GT
	;

fieldModifier
	:	annotation
	|	PUBLIC
	|	PRIVATE
	;

variableDeclaratorList
	:	variableDeclarator (',' variableDeclarator)*
	;

variableDeclarator
	:	ID  ('=' atom)?
	;

annotation
	:	normalAnnotation
	|	markerAnnotation
	|	singleElementAnnotation
	;

normalAnnotation
	:	'@' ID '(' elementValuePairList? ')'
	;

elementValuePairList
	:	elementValuePair (',' elementValuePair)*
	;

elementValuePair
	:	ID '=' elementValue
	;

elementValue
	:	atom
	;

elementValueList
	:	elementValue (',' elementValue)*
	;

markerAnnotation
	:	'@' ID
	;

singleElementAnnotation
	:	'@' ID '(' elementValue ')'
	;

atom
 : OPAR atom CPAR #parExpr
 | (INT | FLOAT)  #numberAtom
 | (TRUE | FALSE) #booleanAtom
 | ID             #idAtom
 | STRING         #stringAtom
 | NULL           #nullAtom
 ;

ASSIGN : '=';
LT : '<';
GT : '>';
OPAR : '(';
CPAR : ')';
OBRACE : '{';
CBRACE : '}';
LBRACK : '[';
RBRACK : ']';
SEMI : ';';
COMMA : ',';
DOT : '.';
ARROW : '->';

IMPORT : 'import';
API : 'api';
GROUP : 'group';
METHOD : 'method';
OBJECT : 'object';
THROWS : 'throws';
ERROR : 'error';
PUBLIC : 'public';
PRIVATE : 'private';

TRUE : 'true';
FALSE : 'false';
NULL : 'null';

ARRBRACK : '[]';

ID
 : [a-zA-Z_] [a-zA-Z_0-9]*
 ;

INT
 : [0-9]+
 ;

FLOAT
 : [0-9]+ '.' [0-9]* 
 | '.' [0-9]+
 ;
 
DESCRIPTION
 : '[' (~[\]]) (~[\]\r\n])+ ']'
 ;

STRING
 : '"' (~["\r\n] | '""')* '"'
 ;

COMMENT
    : '/*' .*? '*/' -> channel(2)
    ;

LINE_COMMENT
    : '//' ~[\r\n]* -> channel(2)
    ;
    
SPACE
 : [ \t\r\n] -> skip
 ;

OTHER
 : . 
 ;
