/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2021 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

#ifndef YY_YY_PARSER_TAB_H_INCLUDED
# define YY_YY_PARSER_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token kinds.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    YYEMPTY = -2,
    YYEOF = 0,                     /* "end of file"  */
    YYerror = 256,                 /* error  */
    YYUNDEF = 257,                 /* "invalid token"  */
    BE = 258,                      /* BE  */
    NUMBER = 259,                  /* NUMBER  */
    INTEGER = 260,                 /* INTEGER  */
    BOOL = 261,                    /* BOOL  */
    STRING = 262,                  /* STRING  */
    CHAR = 263,                    /* CHAR  */
    CONST = 264,                   /* CONST  */
    CHECK = 265,                   /* CHECK  */
    ELSE = 266,                    /* ELSE  */
    READFROMCONSOLE = 267,         /* READFROMCONSOLE  */
    SHOWINCONSOLE = 268,           /* SHOWINCONSOLE  */
    STOPWHEN = 269,                /* STOPWHEN  */
    FUNCTION = 270,                /* FUNCTION  */
    FOR = 271,                     /* FOR  */
    START = 272,                   /* START  */
    PLUS = 273,                    /* PLUS  */
    MINUS = 274,                   /* MINUS  */
    MULTIPLY = 275,                /* MULTIPLY  */
    DIVIDE = 276,                  /* DIVIDE  */
    BACKSLASH = 277,               /* BACKSLASH  */
    MODULO = 278,                  /* MODULO  */
    LESS = 279,                    /* LESS  */
    LESSEQUAL = 280,               /* LESSEQUAL  */
    GREATER = 281,                 /* GREATER  */
    GREATEREQUAL = 282,            /* GREATEREQUAL  */
    EQUAL = 283,                   /* EQUAL  */
    NOTEQUAL = 284,                /* NOTEQUAL  */
    AND = 285,                     /* AND  */
    OR = 286,                      /* OR  */
    ASSIGN = 287,                  /* ASSIGN  */
    LEFTROUND = 288,               /* LEFTROUND  */
    RIGHTROUND = 289,              /* RIGHTROUND  */
    LEFTCURLY = 290,               /* LEFTCURLY  */
    RIGHTCURLY = 291,              /* RIGHTCURLY  */
    LEFTBRACKET = 292,             /* LEFTBRACKET  */
    RIGHTBRACKET = 293,            /* RIGHTBRACKET  */
    COLON = 294,                   /* COLON  */
    SEMICOLON = 295,               /* SEMICOLON  */
    COMMA = 296,                   /* COMMA  */
    APOSTROPHE = 297,              /* APOSTROPHE  */
    QUOTE = 298,                   /* QUOTE  */
    IDENTIFIER = 299,              /* IDENTIFIER  */
    NUMBER_CONST = 300,            /* NUMBER_CONST  */
    STRING_CONST = 301,            /* STRING_CONST  */
    CHAR_CONST = 302               /* CHAR_CONST  */
  };
  typedef enum yytokentype yytoken_kind_t;
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;


int yyparse (void);


#endif /* !YY_YY_PARSER_TAB_H_INCLUDED  */
