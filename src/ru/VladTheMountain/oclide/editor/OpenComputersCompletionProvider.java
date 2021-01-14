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
package ru.VladTheMountain.oclide.editor;

import java.util.Arrays;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.ParameterizedCompletion.Parameter;

/**
 *
 * @author VladTheMountain
 */
public class OpenComputersCompletionProvider {

    /* CONSTANTS */
    //Variable types
    private static final String NULL = "nil";
    private static final String BOOL = "boolean";
    private static final String NUM = "number";
    private static final String STR = "string";
    private static final String FUNC = "function";
    private static final String UDAT = "userdata";
    private static final String THRD = "thread";
    private static final String TABL = "table";
    //Special
    private static final String OPTS = "...";

    public static CompletionProvider getProvider() {
        DefaultCompletionProvider provider = new DefaultCompletionProvider();
        //Lua keywords
        provider.addCompletion(new BasicCompletion(provider, "and"));
        provider.addCompletion(new BasicCompletion(provider, "break"));
        provider.addCompletion(new BasicCompletion(provider, "do"));
        provider.addCompletion(new BasicCompletion(provider, "else"));
        provider.addCompletion(new BasicCompletion(provider, "elseif"));
        provider.addCompletion(new BasicCompletion(provider, "end"));
        provider.addCompletion(new BasicCompletion(provider, "false"));
        provider.addCompletion(new BasicCompletion(provider, "for"));
        provider.addCompletion(new BasicCompletion(provider, "function"));
        provider.addCompletion(new BasicCompletion(provider, "goto"));
        provider.addCompletion(new BasicCompletion(provider, "if"));
        provider.addCompletion(new BasicCompletion(provider, "in"));
        provider.addCompletion(new BasicCompletion(provider, "local"));
        provider.addCompletion(new BasicCompletion(provider, "nil"));
        provider.addCompletion(new BasicCompletion(provider, "not"));
        provider.addCompletion(new BasicCompletion(provider, "or"));
        provider.addCompletion(new BasicCompletion(provider, "repeat"));
        provider.addCompletion(new BasicCompletion(provider, "return"));
        provider.addCompletion(new BasicCompletion(provider, "then"));
        provider.addCompletion(new BasicCompletion(provider, "true"));
        provider.addCompletion(new BasicCompletion(provider, "until"));
        provider.addCompletion(new BasicCompletion(provider, "while"));
        //Lua default fucntions
        /* Do we really need those two global vars in autocompletion? */
        provider.addCompletion(new BasicCompletion(provider, "_G"));
        provider.addCompletion(new BasicCompletion(provider, "_VERSION"));

        FunctionCompletion assertF1 = new org.fife.ui.autocomplete.FunctionCompletion(provider, "assert", OPTS);
        assertF1.setSummary("Issues an error when the value of its argument v is false (i.e., nil or false); otherwise, returns all its arguments. message is an error message; when absent, it defaults to \"assertion failed!\"");
        assertF1.setParams(Arrays.asList(
                new Parameter(BOOL, "v")
        ));
        provider.addCompletion(assertF1);
        FunctionCompletion assertF2 = new org.fife.ui.autocomplete.FunctionCompletion(provider, "assert", OPTS);
        assertF2.setSummary("Issues an error when the value of its argument v is false (i.e., nil or false); otherwise, returns all its arguments. message is an error message; when absent, it defaults to \"assertion failed!\"");
        assertF2.setParams(Arrays.asList(
                new Parameter(BOOL, "v"),
                new Parameter(STR, "message")
        ));
        provider.addCompletion(assertF2);

        FunctionCompletion dofileF1 = new org.fife.ui.autocomplete.FunctionCompletion(provider, "dofile", OPTS);
        dofileF1.setSummary("Opens the named file and executes its contents as a Lua chunk. When called without arguments, dofile executes the contents of the standard input (stdin). Returns all values returned by the chunk. In case of errors, dofile propagates the error to its caller (that is, dofile does not run in protected mode).");
        provider.addCompletion(dofileF1);
        FunctionCompletion dofileF2 = new org.fife.ui.autocomplete.FunctionCompletion(provider, "dofile", OPTS);
        dofileF2.setSummary("Opens the named file and executes its contents as a Lua chunk. When called without arguments, dofile executes the contents of the standard input (stdin). Returns all values returned by the chunk. In case of errors, dofile propagates the error to its caller (that is, dofile does not run in protected mode).");
        dofileF2.setParams(Arrays.asList(
                new Parameter(STR, "filename")
        ));
        provider.addCompletion(dofileF2);

        FunctionCompletion errorF1 = new org.fife.ui.autocomplete.FunctionCompletion(provider, "error", NULL);
        errorF1.setSummary("Terminates the last protected function called and returns message as the error object. Function error never returns.\n"
                + "Usually, error adds some information about the error position at the beginning of the message, if the message is a string. The level argument specifies how to get the error position. With level 1 (the default), the error position is where the error function was called. Level 2 points the error to where the function that called error was called; and so on. Passing a level 0 avoids the addition of error position information to the message.");
        errorF1.setParams(Arrays.asList(
                new Parameter(STR, "message")
        ));
        provider.addCompletion(errorF1);
        FunctionCompletion errorF2 = new org.fife.ui.autocomplete.FunctionCompletion(provider, "error", NULL);
        errorF2.setSummary("Terminates the last protected function called and returns message as the error object. Function error never returns.\n"
                + "Usually, error adds some information about the error position at the beginning of the message, if the message is a string. The level argument specifies how to get the error position. With level 1 (the default), the error position is where the error function was called. Level 2 points the error to where the function that called error was called; and so on. Passing a level 0 avoids the addition of error position information to the message.");
        errorF2.setParams(Arrays.asList(
                new Parameter(STR, "message"),
                new Parameter(NUM, "level")
        ));
        provider.addCompletion(errorF2);
        //OpenOS API

        //Shortcuts
        /* TODO */
        //
        return provider;
    }
}
