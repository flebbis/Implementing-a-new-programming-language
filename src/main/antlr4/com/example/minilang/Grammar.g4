
grammar Grammar;



// Root rule
program : def* EOF;

def: stm    #DStm
    | func  #DFunc
    ;

func: TYPE 'func' ID '(' expSeparator ')' block #FuncNoInference
    | 'func' ID '(' expSeparator ')' block      #FuncInference
    ;

stm: exp SEXPEND                        #SExp
    | 'return' exp                      #SReturn
    | 'while' exp '{' stm '}'           #SWhile
    | 'do' exp '{' stm '}'              #SDo
    | ifStmt                            #SIfElse
    | block                             #SBlock
    | decl SEXPEND                      #SDecl
    | init SEXPEND                      #SInit
    ;

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
exp: '(' exp ')'                                                 #EParensExp
   | left=exp (MULTIPLY | DIVIDE) right=exp                      #EMulDiv
   | left=exp (PLUS | MINUS) right=exp                           #EAddSub
   | left= exp POWER right=exp                                   #EPower
   | left=exp (GE | LE | GT | LT) right=exp                      #ERelational
   | left=exp AND right=exp                                      #EAnd
   | left=exp OR right=exp                                       #EOr
   | INT                                                         #EInt
   | DOUBLE                                                      #EDouble
   | STRING                                                      #EString
   | ID                                                          #EIdExp
   | ID ASSIGN exp                                               #EAssignmentExp
   | ID PLUS_ASSIGN exp                                          #EPlusAssignExp
   | ID MINUS_ASSIGN exp                                         #EMinusAssignExp
   | ID DIV_ASSIGN exp                                           #EDivAssignExp
   | '[' TYPE ']' ID ASSIGN DYNARR_START expSeparator DYNARR_END #DynamicArrayNoInferrence
   | ID ASSIGN DYNARR_START expSeparator DYNARR_END              #DynamicArrayInference
   | ID '(' expSeparator ')'                                     #EFunctionCall
   | ID '[' exp ']'                                              #EArrayIndexing
   | ID INC                                                      #EInc
   | ID DEC                                                      #EDec
   ;

expSeparator: (exp (',' exp)* )?;

// java.GrammarLexer Rules

STRING      : '"'  ( '\\' . | ~["\\] )* '"' |
              '\'' ( '\\' . | ~['\\] )* '\'';
DOUBLE      : [0-9]+ '.'? [0-9]*;
INT         : [0-9]+;
BOOL        : 'true' | 'false';
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
DYNARR_START : '[';
DYNARR_END   : ']';
SEXPEND   : ';' | '\n';
TYPE        : 'int' | 'double' | 'string' | 'bool';
INC          : '++';
DEC          : '--';


// Single-line comments
LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;

// Multi-line comments
BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

// Whitespace
WS
    : [ \t\r\n]+ -> skip
    ;
