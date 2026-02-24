grammar Grammar;



// Root rule: allow optional statement-end tokens between/after definitions
program : (def SEXPEND?)* EOF;

def: stm    #DStm
    | func  #DFunc
    ;

func: TYPE 'func' ID '(' paramSeparator ')' block #FuncNoInference
    | 'func' ID '(' expSeparator ')' block      #FuncInference
    ;

param: TYPE ID        #ParamDecl
;

stm
    : init SEXPEND?                     #SInit
    | decl SEXPEND?                     #SDecl
    | 'return' exp SEXPEND?             #SReturn
    | 'while' exp '{' stm '}'           #SWhile
    | 'do' exp '{' stm '}'              #SDo
    | ifStmt                            #SIfElse
    | block                             #SBlock
    | exp SEXPEND?                      #SExp
    ;
//
//stm: exp SEXPEND                        #SExp
//    | 'return' exp                      #SReturn
//    | 'while' exp '{' stm '}'           #SWhile
//    | 'do' exp '{' stm '}'              #SDo
//    | ifStmt                            #SIfElse
//    | block                             #SBlock
//    | decl SEXPEND                      #SDecl
//    | init SEXPEND                      #SInit
//    ;

decl: TYPE ID  #DeclNoInference
    | ID       #DeclInference
    ;

init: TYPE ID ASSIGN exp  #InitNoInference
    | ID ASSIGN exp       #InitInference
    ;

block: '{' stm* '}';

ifStmt  : 'if' exp block ('else' ifStmt)?   #IfElseIf
        | 'if' exp block 'else' block       #IfElse
        | 'if' exp block                    #If
        ;


// java.GrammarParser Rules
//exp: '(' exp ')'                                                 #EParensExp
//   | left=exp (MULTIPLY | DIVIDE) right=exp                      #EMulDiv
//   | left=exp (PLUS | MINUS) right=exp                           #EAddSub
//   | left= exp POWER right=exp                                   #EPower
//   | left=exp (GE | LE | GT | LT) right=exp                      #ERelational
//   | left=exp AND right=exp                                      #EAnd
//   | left=exp OR right=exp                                       #EOr
//   | INT                                                         #EInt
//   | DOUBLE                                                      #EDouble
//   | STRING                                                      #EString
//   | ID                                                          #EIdExp
//   | ID ASSIGN exp                                               #EAssignmentExp
//   | ID PLUS_ASSIGN exp                                          #EPlusAssignExp
//   | ID MINUS_ASSIGN exp                                         #EMinusAssignExp
//   | ID DIV_ASSIGN exp                                           #EDivAssignExp
//   | '[' TYPE ']' ID ASSIGN DYNARR_START expSeparator DYNARR_END #DynamicArrayNoInferrence
//   | ID ASSIGN DYNARR_START expSeparator DYNARR_END              #DynamicArrayInference
//   | ID '(' expSeparator ')'                                     #EFunctionCall
//   | exp '[' exp ']'                                             #EArrayIndexing
//   | ID INC                                                      #EInc
//   | ID DEC                                                      #EDec
//   ;

// Precedence-based expression rules

exp
    : assignExpr
    ;

assignExpr
    : orExpr ( (ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN | DIV_ASSIGN) assignExpr )?
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
    : unaryExpr (POWER powerExpr)?        // right-associative
    ;

unaryExpr
    : (NOT | PLUS | MINUS) unaryExpr
    | postfixExpr
    ;

postfixExpr
    : primary ( INC | DEC | '[' exp ']' | '(' expSeparator ')' )*
    ;

primary
    : '(' exp ')'
    | INT
    | DOUBLE
    | STRING
    | ID
    | '[' TYPE ']' ID ASSIGN DYNARR_START expSeparator DYNARR_END
    | ID ASSIGN DYNARR_START expSeparator DYNARR_END
    ;



expSeparator: (exp (',' exp)* )?;
paramSeparator: (param (',' param)* )?;

// java.GrammarLexer Rules
TYPE        : 'int' | 'double' | 'string' | 'bool';
STRING      : '"'  ( '\\' . | ~["\\] )* '"' |
              '\'' ( '\\' . | ~['\\] )* '\'';
DOUBLE      : [0-9]+ '.'? [0-9]*;
INT         : [0-9]+;
BOOL        : 'true' | 'false';
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
PLUS_ASSIGN : '+=';
MINUS_ASSIGN: '-=';
MULT_ASSIGN : '*=';
DIV_ASSIGN  : '/=';
DYNARR_START : '[';
DYNARR_END   : ']';
INC          : '++';
DEC          : '--';
BOM : '\uFEFF' -> skip;
SEXPEND
    : SEMI
    | NL
    | SEMI NL
    ;


// To this (Newlines are no longer skipped)
WS : [ \t]+ -> skip;
NL : [\r\n]+;
SEMI : ';';

//WHITESPACE  : [ \t\r\n]+ -> skip;

// Single-line comments
LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;

// Multi-line comments
BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;


//
//// ANTLR lexer fragment: place keywords before ID, and skip whitespace
//
//INT_TYPE : 'int' ;
//DOUBLE_TYPE : 'double' ;
//RETURN : 'return' ;
//IF : 'if' ;
//ELSE : 'else' ;
//FOR : 'for' ;
//WHILE : 'while' ;
//AND : '&&' ;
//OR : '||' ;
//EQ : '==' ;
//NE : '!=' ;
//GE : '>=' ;
//LE : '<=' ;
//ASSIGN : '=' ;
//PLUS : '+' ;
//MINUS : '-' ;
//MULTIPLY : '*' ;
//DIVIDE : '/' ;
//POWER : '^' ;
//INC : '++' ;
//DEC : '--' ;
//LPAREN : '(' ;
//RPAREN : ')' ;
//LBRACK : '[' ;
//RBRACK : ']' ;
//SEMI : ';' ;
//COMMA : ',' ;
//STRING : '"' ( ~["\\] | '\\' . )* '"' ;
//
//// numeric literal - keep its name if you already use INT in parser,
//// otherwise rename to INT_LIT
//INT : [0-9]+ ;
//DOUBLE : [0-9]+ '.' [0-9]* ;
//
//// identifier rule must come after all keywords
//ID : [a-zA-Z_] [a-zA-Z_0-9]* ;
//
//// skip whitespace and comments
//WS : [ \t\r\n]+ -> skip ;
//LINE_COMMENT : '//' ~[\r\n]* -> skip ;
//BLOCK_COMMENT : '/*' .*? '*/' -> skip ;
