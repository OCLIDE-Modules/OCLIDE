/*
 * The MIT License
 *
 * Copyright 2020 Vladislav Gorskii.
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
package ru.VladTheMountain.oclide.emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Collectors;
import ru.VladTheMountain.oclide.emulator.lang.Token;

/**
 *
 * @author VladTheMountain
 */
public class LuaInterpreter {

    String[] sourceCode;
    Token[] tokens;
    int tokenCounter;
    String[] commands;

    ErrorHandler mainHandler = new ErrorHandler(this.getClass());

    /**
     * Creates a new {@link LuaInterpreter} object and starts the sequence of
     * interpreting. <strong>Note:</strong> requires every expression or
     * statement to be separated with semicolon (<strong>;</strong>)
     *
     * @param code th script's source code
     */
    public LuaInterpreter(String code) {
        sourceCode = code.split(";", -1);
        startSequence();
    }

    /**
     * Attempts to create a new {@link LuaInterpreter} object and start the
     * sequence of interpreting from a file.
     *
     * @param source absolute path to a file
     */
    public LuaInterpreter(File source) {
        //"fool protection"
        if (!(source.exists())) {
            mainHandler.error("Source file " + source + " is missing. Aborting...");
        } else if (!(source.isFile())) {
            mainHandler.error("Source file " + source + " is not a file. Aborting...");
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get(source.getAbsolutePath()))) {
            List<String> list = br.lines().collect(Collectors.toList());
            sourceCode = (String[]) list.toArray();
        } catch (IOException e) {
            mainHandler.error(e.getLocalizedMessage());
            startSequence();
        }
    }

    private void startSequence() {
        new Lexer();
        new Parser();
        execute();
    }

    private void execute() {

    }

    /**
     * Class just for the sake of being
     */
    class ErrorHandler {

        Class<?> parentClass;

        public ErrorHandler(Class<?> c) {
            this.parentClass = c;
        }

        public void warn(String msg) {
            Logger.getLogger(parentClass.getName()).log(Level.WARNING, msg);
        }

        public void error(String msg) {
            Logger.getLogger(parentClass.getName()).log(Level.SEVERE, msg);
        }
    }

    class Lexer {

        ErrorHandler lexerHandler = new ErrorHandler(this.getClass());

        public Lexer() {
            for (int i = 0; i < sourceCode.length; i++) {
                for (int j = 0; j < sourceCode[i].length(); j++) {
                    //operators
                    if (sourceCode[i].charAt(j) == '+' // +
                            || sourceCode[i].charAt(j) == '-' // -
                            || sourceCode[i].charAt(j) == '*' // *
                            || sourceCode[i].charAt(j) == '/' // /
                            || sourceCode[i].charAt(j) == '%' // %
                            || sourceCode[i].charAt(j) == '^' // ^
                            || sourceCode[i].charAt(j) == '#' // #
                            || sourceCode[i].charAt(j) == '&' // &
                            || sourceCode[i].charAt(j) == '~' // ~
                            || sourceCode[i].charAt(j) == '|' // |
                            || sourceCode[i].charAt(j) == '<' // <
                            || sourceCode[i].charAt(j) == '>' // >
                            || sourceCode[i].charAt(j) == ' ' //  
                            || sourceCode[i].charAt(j) == '=' // =
                            || sourceCode[i].charAt(j) == '(' // (
                            || sourceCode[i].charAt(j) == ')' // )
                            || sourceCode[i].charAt(j) == '{' // {
                            || sourceCode[i].charAt(j) == '}' // }
                            || sourceCode[i].charAt(j) == '[' // [
                            || sourceCode[i].charAt(j) == ']' // ]
                            || sourceCode[i].charAt(j) == ';' // ;
                            || sourceCode[i].charAt(j) == ':' // :
                            || sourceCode[i].charAt(j) == ',' // ,
                            || sourceCode[i].charAt(j) == '.' // .
                            ) {
                        if ((sourceCode[i].charAt(j) == '<' && sourceCode[i].charAt(j + 1) == '<') // <<
                                || (sourceCode[i].charAt(j) == '<' && sourceCode[i].charAt(j + 1) == '=') // <=
                                || (sourceCode[i].charAt(j) == '>' && sourceCode[i].charAt(j + 1) == '>') // >>
                                || (sourceCode[i].charAt(j) == '>' && sourceCode[i].charAt(j + 1) == '=') // >=  
                                || (sourceCode[i].charAt(j) == '=' && sourceCode[i].charAt(j + 1) == '=') // ==
                                || (sourceCode[i].charAt(j) == '~' && sourceCode[i].charAt(j + 1) == '=') // ~=
                                || (sourceCode[i].charAt(j) == '/' && sourceCode[i].charAt(j + 1) == '/') // //
                                || (sourceCode[i].charAt(j) == ':' && sourceCode[i].charAt(j + 1) == ':') // ::
                                || (sourceCode[i].charAt(j) == '.' && sourceCode[i].charAt(j + 1) == '.') // ..
                                ) {
                            if (sourceCode[i].charAt(j) == '.' && sourceCode[i].charAt(j + 1) == '.'
                                    && sourceCode[i].charAt(j + 2) == '.') // ...
                            {
                                tokens[tokenCounter + 1] = new Token(Token.TYPE_OPERATOR, i, j, "...");
                                j += 2;
                            } else {
                                tokens[tokenCounter + 1] = new Token(Token.TYPE_OPERATOR, i, j, String.valueOf(sourceCode[i].charAt(j) + sourceCode[i].charAt(j + 1)));
                                j += 1;
                            }
                        } else {
                            tokens[tokenCounter + 1] = new Token(Token.TYPE_OPERATOR, i, j, String.valueOf(sourceCode[i].charAt(j)));
                        }
                    } // The rest
                    // TODO: Make all characters between the ones listed above to be placed into the next token
                    else {
                        tokens[tokenCounter + 1] = new Token(Token.TYPE_IDENTIFIER, i, j, sourceCode[i].substring(sourceCode[i].indexOf("."), sourceCode[i].indexOf(".")));
                    }
                    tokenCounter++;
                }
            }
        }
    }

    class Parser {

        ErrorHandler parserHandler = new ErrorHandler(this.getClass());
        int commandCounter;

        public Parser() {
            for (Token token : tokens) {
                if (token.type == Token.TYPE_OPERATOR) {
                    switch (token.name) {
                        case "+":
                        //commands[commandCounter + 1] = tokens[i - 1] + tokens[i + 1];
                        default:
                            parserHandler.error("Invalid token at " + token.line + ":" + token.pos + ". Unable to continue executing.");
                    }
                }
                commandCounter++;
            }
        }
    }
}
