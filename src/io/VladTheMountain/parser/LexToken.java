/*
 * The MIT License
 *
 * Copyright 2021 Vladislav Gorskii.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.VladTheMountain.parser;

/**
 * A type of object representing the token produced by the lexer.
 *
 * @author VladTheMountain
 */
public class LexToken {

    public static final String KEYWORD_BREAK = "break";
    public static final String KEYWORD_GOTO = "goto";
    public static final String KEYWORD_DO = "do";
    public static final String KEYWORD_END = "end";
    public static final String KEYWORD_WHILE = "while";
    public static final String KEYWORD_REPEAT = "repeat";
    public static final String KEYWORD_UNTIL = "until";
    public static final String KEYWORD_IF = "if";
    public static final String KEYWORD_THEN = "then";
    public static final String KEYWORD_ELSEIF = "elseif";
    public static final String KEYWORD_ELSE = "else";
    public static final String KEYWORD_FOR = "for";
    public static final String KEYWORD_IN = "in";
    public static final String KEYWORD_FUNCTION = "function";
    public static final String KEYWORD_LOCAL = "local";

    public static final String KEYWORD_RETURN = "return";

    public static final String KEYWORD_NIL = "nil";
    public static final String KEYWORD_FALSE = "false";
    public static final String KEYWORD_TRUE = "true";

    public static final String KEYWORD_AND = "and";
    public static final String KEYWORD_OR = "or";
    public static final String KEYWORD_NOT = "not";

    public static final String TYPE_NAME = "Name";
    public static final String TYPE_NUMERAL = "Numeral";
    public static final String TYPE_LITERALSTRING = "LiteralString";

    public static final String TYPE_CHUNK = "chunk";
    public static final String TYPE_BLOCK = "block";
    public static final String TYPE_STATEMENT = "stat";
    public static final String TYPE_RETURNSTATEMENT = "retstat";
    public static final String TYPE_LABEL = "label";
    public static final String TYPE_FUNCNAME = "funcname";
    public static final String TYPE_VARLIST = "varlist";
    public static final String TYPE_VAR = "var";
    public static final String TYPE_NAMELIST = "namelist";
    public static final String TYPE_EXPLIST = "explist";
    public static final String TYPE_EXP = "exp";
    public static final String TYPE_PREFIXEXP = "prefixexp";
    public static final String TYPE_FUNCTIONCALL = "functioncall";
    public static final String TYPE_ARGS = "args";
    public static final String TYPE_FUNCTIONDEF = "functiondef";
    public static final String TYPE_FUNCBODY = "funcbody";
    public static final String TYPE_PARLIST = "parlist";
    public static final String TYPE_TABLECONSTRUCTOR = "tableconstructor";
    public static final String TYPE_FIELDLIST = "fieldlist";
    public static final String TYPE_FIELD = "field";
    public static final String TYPE_FIELDSEP = "fieldsep";
    public static final String TYPE_BINOP = "binop";
    public static final String TYPE_UNOP = "unop";

    private String tokenType, tokenValue;

    public LexToken(String type, String value) {
        tokenType = type;
        tokenValue = value;
    }

    public String getType() {
        return tokenType;
    }

    public String getValue() {
        return tokenValue;
    }
}
