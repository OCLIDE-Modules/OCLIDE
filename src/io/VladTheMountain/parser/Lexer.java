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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Queue;
import org.apache.commons.collections4.queue.CircularFifoQueue;

/**
 * <code>The lexer reads characters from the input/character reader and produces
 * tokens. In other words, it converts a character stream into a token stream.
 * Hence its sometimes also called the tokenizer. These tokens are produced
 * according to the defined grammar.</code>
 *
 * @author VladTheMountain
 */
public class Lexer {

    private InputReader input;

    private Queue<String/*LexToken*/> ringBuffer;
    private final int bufferSize = 256;

    private String lexerState;

    //Tokens
    private static final String NIL = "nil";
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private static final String NUMBER = "Numerical";
    private static final String STRING = "LiteralLString";
    private static final String TRIPLE_DOT = "\'...\'";
    private static final String FUNCTION = "functiondef";
    private static final String PREFIX = "prefixexp";
    private static final String TABLE = "tableconstructor";
    private static final String BINOP = "binop";
    private static final String UNOP = "unop";
    //End of Tokens

    /**
     * Initializes the {@code InputReader}, which is neccessary for peeeking and
     * consuming methods
     *
     * @param filePath Path to file to get code from
     * @throws FileNotFoundException if the specified file could not be found
     */
    public Lexer(String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }

    /**
     * Initializes the {@code InputReader}, which is neccessary for peeeking and
     * consuming methods
     *
     * @param file The file to get code from
     * @throws FileNotFoundException if the specified file could not be found
     */
    public Lexer(File file) throws FileNotFoundException {
        input = new InputReader(file);
        ringBuffer = new CircularFifoQueue<>(bufferSize);
        lexerState = LexToken.TYPE_CHUNK;
    }

    /**
     * Get the next token from the input.This is used to look ahead the tokens
     * without consuming/removing them from the input stream. Calling the
     * {@code peek()} method more than once will return the same token.
     *
     * @return The next character from the input
     * @throws java.io.IOException
     * @see #peek(int)
     */
    public String peek() throws IOException {
        ringBuffer.add(nextToken());
        return ringBuffer.peek();
    }

    /**
     * Get the next k-th token from the input. This is used to look ahead the
     * tokens without consuming/removing them from the input stream. Calling the
     * {@code peek()} method more than once will return the same token.
     *
     * @param k the index of the character to get
     * @return The k-th character from the input
     * @see #peek()
     */
    public String peek(int k) {
        return (String) ringBuffer.toArray()[k];
    }

    /**
     * Get the next token from the input, and remove it from the input. This
     * means, calling the {@code consume()} method multiple times will return a
     * new character at each invocation.
     *
     * @return The next character from the input
     * @see #consume(int)
     */
    public String consume() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get the next k-th token from the input, and remove it from the input.
     * This means, calling the {@code consume()} method multiple times will
     * return a new character at each invocation.
     *
     * @param k the index of the character to get
     * @return The k-th character from the input
     * @see #consume()
     */
    public String consume(int k) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Private method, containing all the logic of the lexer. It changes the
     * lexer's state and launches the neccessary processing functions depending
     * on the grammar rules of the language.
     *
     * @param c the char to lex/tokenize
     * @throws java.io.IOException if an I/O error occures
     */
    private String nextToken() throws IOException {
        if (input.peek() == 'n'
                && input.peek(input.currentPos + 1) == 'i'
                && input.peek(input.currentPos + 1) == 'l') {
            return processNil();
        } else if (input.peek() == 'f'
                && input.peek(input.currentPos + 1) == 'a'
                && input.peek(input.currentPos + 2) == 'l'
                && input.peek(input.currentPos + 3) == 's'
                && input.peek(input.currentPos + 4) == 'e') {
            return processFalse();
        } else if (input.peek() == 'f'
                && input.peek(input.currentPos + 1) == 'u'
                && input.peek(input.currentPos + 2) == 'n'
                && input.peek(input.currentPos + 3) == 'c'
                && input.peek(input.currentPos + 4) == 't'
                && input.peek(input.currentPos + 1) == 'i'
                && input.peek(input.currentPos + 2) == 'o'
                && input.peek(input.currentPos + 3) == 'n') {
            return processFunction();
        } else if (input.peek() == 't'
                && input.peek(input.currentPos + 1) == 'r'
                && input.peek(input.currentPos + 2) == 'u'
                && input.peek(input.currentPos + 3) == 'e') {
            return processTrue();
        } else if (input.peek() == '0'
                || input.peek() == '1'
                || input.peek() == '2'
                || input.peek() == '3'
                || input.peek() == '4'
                || input.peek() == '5'
                || input.peek() == '6'
                || input.peek() == '7'
                || input.peek() == '8'
                || input.peek() == '9') {
            return processNumeral();
        } else if (input.peek() == '"') {
            return processLiteralString();
        } else if (input.peek() == '.' && input.peek(input.currentPos + 1) == '.'
                && input.peek(input.currentPos + 2) == '.') {
            return processTripleDot();
        } else {
            return processChunk();
        }
    }
    // WARNING: LESS-DOCUMENTED PROCESSING FUNCTIONS AHEAD

    private String processNil() {
        return null;
    }

    private String processFalse() {
        return null;
    }

    private String processTrue() {
        return null;
    }

    private String processNumeral() {
        return null;
    }

    private String processLiteralString() {
        return null;
    }

    private String processTripleDot() {
        return null;
    }

    private String processFunction() {
        return null;
    }

    private String processPrefix() {
        return null;
    }

    private String processTable() {
        return null;
    }

    private String processBinOP() {
        return null;
    }

    private String processUnOP() {
        return null;
    }

    private String processChunk() {
        return null;
    }
}
