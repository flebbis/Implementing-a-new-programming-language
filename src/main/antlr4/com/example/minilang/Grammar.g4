grammar Grammar;

separator: (NL | SEMI)+ ;

// Root rule:
program : separator* (def (separator+ def)*)? separator* EOF;

def
    : func  #DFunc
    | stmt    #DStm
    ;

func: TYPE? 'func' ID '(' paramSeparator ')' block
    ;

param: TYPE? ID;

stmt
    : simpleStmt
    | blockStmt
    ;

simpleStmt
    : decl
    | init
    | returnStmt
    | exp
    ;

blockStmt
    : whileStmt
    | doStmt
    | ifStmt
    | block
    ;

block :  '{' separator* (stmt (separator+ stmt)*)? separator* '}' ;

returnStmt: 'return' exp?;

whileStmt: 'while' exp stmt;

doStmt: 'do' exp stmt;

ifStmt
    : 'if' exp block (separator* 'else' (ifStmt | block))?
    ;


decl: TYPE? ID
    ;

init: TYPE? ID ASSIGN exp
    ;


terminator
    : SEMI
    | NL+
    | EOF;

// Precedence-based expression rules
exp
    : assignExpr
    ;

assignExpr
    : orExpr ( (ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN | DIV_ASSIGN | MULT_ASSIGN) assignExpr )?
    ;

orExpr
    : andExpr (OR andExpr)*
    ;

andExpr
    : equalityExpr (AND equalityExpr)*
    ;

equalityExpr
    : relational ( (EQ | NE) relational )*
    ;

relational
    : addExpr ( (GT | LT | GE | LE) addExpr )*
    ;

addExpr
    : mulExpr ( (PLUS | MINUS) mulExpr )*
    ;

mulExpr
    : powerExpr ( (MULTIPLY | DIVIDE) powerExpr )*
    ;

powerExpr
    : unaryExpr (POWER powerExpr)?
    ;

unaryExpr
    : (NOT) unaryExpr
    | postfixExpr
    ;

postfixExpr
    : primary postFixOp*
    ;

postFixOp
    : INC
    | DEC
    | '[' exp ']'
    | '(' expSeparator ')'
    ;

primary
    : '(' exp ')'
    | INT
    | DOUBLE
    | STRING
    | BOOL
    | ID
    | '[' TYPE ']' ID ASSIGN DYNARR_START expSeparator DYNARR_END
    | ID ASSIGN DYNARR_START expSeparator DYNARR_END
    ;

expSeparator: (exp (',' exp)* )?; //x, y, z
paramSeparator: (param (',' param)* )?; //int x, double y, string z

// java.GrammarLexer Rules
TYPE        : 'int' | 'double' | 'string' | 'bool';
STRING      : '"'  ( '\\' . | ~["\\] )* '"' |
              '\'' ( '\\' . | ~['\\] )* '\'';
INT         : [0-9]+; //int over double, so if i = 5 it chooses int
DOUBLE      : [0-9]+ '.'? [0-9]*;
BOOL        : 'true' | 'false';
PLUS_ASSIGN : '+=';
MINUS_ASSIGN: '-=';
MULT_ASSIGN : '*=';
DIV_ASSIGN  : '/=';
PLUS        : '+';
MINUS       : '-';
MULTIPLY    : '*';
DIVIDE      : '/';
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
DYNARR_START : '[';
DYNARR_END   : ']';
INC          : '++';
DEC          : '--';
BOM : '\uFEFF' -> skip;


WS : [ \t]+ -> skip;
NL : ('\r'? '\n') ;
SEMI : ';';

// Single-line comments
LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;

// Multi-line comments
BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;
