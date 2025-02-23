%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define YYDEBUG 1

int yylex();
void yyerror(char *);
%}

%token BE
%token NUMBER
%token INTEGER
%token BOOL
%token STRING
%token CHAR
%token CONST
%token CHECK
%token ELSE
%token READFROMCONSOLE
%token SHOWINCONSOLE
%token STOPWHEN
%token FUNCTION
%token FOR
%token START

%token PLUS
%token MINUS
%token MULTIPLY
%token DIVIDE
%token BACKSLASH
%token MODULO
%token LESS
%token LESSEQUAL
%token GREATER
%token GREATEREQUAL
%token EQUAL
%token NOTEQUAL
%token AND
%token OR
%token ASSIGN

%token LEFTROUND
%token RIGHTROUND
%token LEFTCURLY
%token RIGHTCURLY
%token LEFTBRACKET
%token RIGHTBRACKET
%token COLON
%token SEMICOLON
%token COMMA
%token APOSTROPHE
%token QUOTE

%token IDENTIFIER
%token NUMBER_CONST
%token STRING_CONST
%token CHAR_CONST

%start program

%%

program : START compound_statement

statement : declaration SEMICOLON 
          | assignment_statement 
          | return_statement SEMICOLON 
          | iostmt SEMICOLON 
          | if_statement 
          | while_statement 
          | for_statement

statement_list : statement 
               | statement statement_list

compound_statement : LEFTCURLY statement_list RIGHTCURLY

expression : expression PLUS term 
           | expression MINUS term 
           | term

term : term MULTIPLY factor 
     | term DIVIDE factor 
     | term MODULO factor 
     | factor

factor : LEFTROUND expression RIGHTROUND 
       | IDENTIFIER 
       | constant

constant : NUMBER_CONST 
         | STRING_CONST 
         | CHAR_CONST 

iostmt : READFROMCONSOLE LEFTROUND IDENTIFIER RIGHTROUND 
       | SHOWINCONSOLE LEFTROUND IDENTIFIER RIGHTROUND 
       | SHOWINCONSOLE LEFTROUND constant RIGHTROUND

simple_type : NUMBER 
            | INTEGER 
            | BOOL 
            | STRING 
            | CHAR

array_declaration : simple_type IDENTIFIER LEFTBRACKET RIGHTBRACKET

declaration : BE IDENTIFIER simple_type 
            | array_declaration 

assignment_statement : IDENTIFIER ASSIGN expression SEMICOLON

if_statement : CHECK LEFTROUND condition RIGHTROUND compound_statement 
             | CHECK LEFTROUND condition RIGHTROUND compound_statement ELSE compound_statement

while_statement : STOPWHEN LEFTROUND condition RIGHTROUND compound_statement

return_statement : FUNCTION expression 

for_statement : FOR for_header compound_statement

for_header : LEFTROUND INTEGER assignment_statement condition assignment_statement RIGHTROUND

condition : expression relation expression

relation : LESS 
         | LESSEQUAL 
         | EQUAL 
         | NOTEQUAL 
         | GREATEREQUAL 
         | GREATER

%%

void yyerror(char *s)
{
    fprintf(stderr, "Error: %s\n", s);
}

extern FILE *yyin;

int main(int argc, char **argv)
{
    if (argc > 1) yyin = fopen(argv[1], "r");
    if (argc > 2 && !strcmp(argv[2], "-d")) yydebug = 1;
    if (!yyparse()) fprintf(stderr, "\tProgram is syntactically correct.\n");
    return 0;
}
