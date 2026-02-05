
grammar Grammar;



// Root rule
program : exp EOF;

// java.GrammarParser Rules
exp: exp (MULTIPLY | DIVIDE) exp
   | exp (PLUS | MINUS) exp
   | INT
   ;

// java.GrammarLexer Rules
INT         : [0-9]+;
PLUS        : '+';
MINUS       : '-';
MULTIPLY    : '*';
DIVIDE      : '/';
WHITESPACE  : [ \t\r\n]+ -> skip;