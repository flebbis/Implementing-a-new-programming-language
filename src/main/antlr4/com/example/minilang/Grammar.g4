
grammar Grammar;



// Root rule
program : exp EOF;

// java.GrammarParser Rules
exp: '(' exp ')'                               #ParensExp
   | left=exp (MULTIPLY | DIVIDE) right=exp    #MulDiv
   | left=exp (PLUS | MINUS) right=exp         #AddSub
   | left= exp POWER right=exp                 #Power 
   | left=exp (GE | LE | GT | LT) right=exp    #Relational
   | left=exp AND right=exp                    #AndExp
   | left=exp OR right=exp                     #OrExp
   | INT                            #Int     
   | DOUBLE                         #Double    
   | BOOLEAN                        #Boolean
   | STRING                         #String
   | ID                             #IdExp
   | ID ASSIGN exp                  #AssignmentExp
   | ID PLUS_ASSIGN exp             #PlusAssignExp
   | ID MINUS_ASSIGN exp            #MinusAssignExp
   | ID DIV_ASSIGN exp              #DivAssignExp
   ;

// java.GrammarLexer Rules

STRING      : '"'  ( '\\' . | ~["\\] )* '"' |
              '\'' ( '\\' . | ~['\\] )* '\'';
BOOLEAN     : 'True' | 'False';
DOUBLE      : [0-9]+ '.'? [0-9]*;
INT         : [0-9]+;
BOOL        : 'true' | 'false';
DOUBLE      : [0-9]+ '.' [0-9]+;
PLUS        : '+';
MINUS       : '-';
MULTIPLY    : '*';
DIVIDE      : '/';
WHITESPACE  : [ \t\r\n]+ -> skip;
POWER       : '**';
GT          : '>';
LT          : '<'; 
LE          : '<=';
GE          : '>=';
EQ          : '==';
NE          : '!=';
AND         : 'and';
OR          : 'or';
NOT         : 'not'; 
ID          : [a-zA-Z_][a-zA-Z0-9_]*; // need help 
ASSIGN      : '=';
PLUS_ASSIGN : '+=';
MINUS_ASSIGN: '-=';
MULT_ASSIGN : '*=';
DIV_ASSIGN  : '/=';
