
grammar Grammar;



// Root rule
program : exp EOF;

exp
    : '(' exp ')'                              # Parens
    | left=exp op=(MULTIPLY|DIVIDE) right=exp  # MulDiv
    | left=exp op=(PLUS|MINUS) right=exp       # AddSub
    | left=exp op=EQ right=exp                 # Eq
    | INT                                      # Int
    | BOOL                                     # Bool
    ;



// java.GrammarLexer Rules
INT         : [0-9]+;
PLUS        : '+';
MINUS       : '-';
MULTIPLY    : '*';
DIVIDE      : '/';
WHITESPACE  : [ \t\r\n]+ -> skip;
EQ : '==';
BOOL : 'true' | 'false';