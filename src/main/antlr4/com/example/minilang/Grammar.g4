
grammar Grammar;



// Root rule
program : exp EOF;

// java.GrammarParser Rules
exp: '(' exp ')'                               #ParensExp
   | left=exp (MULTIPLY | DIVIDE) right=exp    #MulDiv
   | left=exp (PLUS | MINUS) right=exp         #AddSub
   | INT                            #Int
   ;

// java.GrammarLexer Rules
INT         : [0-9]+;
BOOL        : 'true' | 'false';
DOUBLE      : [0-9]+ '.' [0-9]+;
PLUS        : '+';
MINUS       : '-';
MULTIPLY    : '*';
DIVIDE      : '/';
WHITESPACE  : [ \t\r\n]+ -> skip;