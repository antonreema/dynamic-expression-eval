grammar SimpleBoolean;

parse
 : expression EOF
 ;

expression
 : LPAREN expression RPAREN                       #parenExpression
 | NOT expression                                 #notExpression
 | left=expression op=comparator right=expression #comparatorExpression
 | left=expression op=binary right=expression     #binaryExpression
 | bool                                           #boolExpression
 | IDENTIFIER                                     #identifierExpression
 | DECIMAL                                        #decimalExpression
 | TEXT                                           #textExpression
 ;

comparator
 : GT | GE | LT | LE | EQ | NE | IN | NOTIN | STARTSWITH | ENDSWITH | NULL | NOTNULL
 ;

binary
 : AND | OR
 ;

bool
 : TRUE | FALSE
 ;

AND        : 'AND' ;
OR         : 'OR' ;
NOT        : 'NOT';
TRUE       : 'TRUE' ;
FALSE      : 'FALSE' ;
GT         : '>' ;
GE         : '>=' ;
LT         : '<' ;
LE         : '<=' ;
EQ         : '=' ;
NE         : '!=' ;
IN         : 'IN' ;
NOTIN      : 'NOTIN' ;
STARTSWITH : 'STARTSWITH' ;
ENDSWITH   : 'ENDSWITH' ;
NULL       : 'NULL' ;
NOTNULL    : 'NOTNULL' ;
LPAREN     : '(' ;
RPAREN     : ')' ;
DECIMAL    : '-'? [0-9]+ ( '.' [0-9]+ )? ;
IDENTIFIER : [a-zA-Z_] [a-zA-Z_0-9]* ;
TEXT       : [a-zA-Z_0-9]+ (',' [a-zA-Z_0-9]+ )*;
WS         : [ \r\t\u000C\n]+ -> skip;
