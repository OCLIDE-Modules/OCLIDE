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
 * <code>The lexer reads characters from the input/character reader and produces
 * tokens. In other words, it converts a character stream into a token stream.
 * Hence its sometimes also called the tokenizer. These tokens are produced
 * according to the defined grammar.</code>
 *
 * @author VladTheMountain
 */
public class Lexer {

    /**
     * Get the next character from the input. This is used to look ahead the
     * characters without consuming/removing them from the input stream. Calling
     * the {@code peek()} method more than once will return the same character.
     *
     * @return The next character from the input
     * @see #peek(int)
     */
    public char peek() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get the next k-th character from the input.This is used to look ahead the
     * characters without consuming/removing them from the input stream. Calling
     * the {@code peek()} method more than once will return the same character.
     *
     * @param k the index of the character to get
     * @return The k-th character from the input
     * @see #peek()
     */
    public char peek(int k) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get the next character from the input, and remove it from the input. This
     * means, calling the {@code consume()} method multiple times will return a
     * new character at each invocation.
     *
     * @return The next character from the input
     * @see #consume(int)
     */
    public char consume() {
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
    public char consume(int k) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
