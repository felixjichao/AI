grammar fol;

formula
   : (NOT)? PREDICATE LPAREN (VARIABLE | PREDICATE) (',' (VARIABLE | PREDICATE))* RPAREN                       #atom
   | LPAREN formula RPAREN      #parenthesis
   | NOT formula                #negation
   | formula AND formula        #conjunction
   | formula OR formula         #disjunction
   | formula IMPLY formula        #implication
   ;

LPAREN
   : '('
   ;


RPAREN
   : ')'
   ;


AND
   : '&'
   ;


OR
   : '|'
   ;


NOT
   : '~'
   ;

IMPLY
    : '=>'
    ;

VARIABLE
   : ('a' .. 'z')
   ;


PREDICATE
   : ('A' .. 'Z') [a-zA-Z]*
   ;

WS
   : (' ' | '\t' | '\r' | '\n') + -> skip
   ;