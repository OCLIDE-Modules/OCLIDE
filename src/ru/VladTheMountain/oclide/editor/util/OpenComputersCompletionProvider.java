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
package ru.VladTheMountain.oclide.editor.util;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.LanguageAwareCompletionProvider;
import org.fife.ui.autocomplete.ParameterizedCompletion;
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
        ResourceBundle autocompletion = ResourceBundle.getBundle("ru.VladTheMountain.oclide.resources.autocompletion.Autocompletion", Locale.getDefault());
        DefaultCompletionProvider defaultProvider = new DefaultCompletionProvider();
        defaultProvider.setAutoActivationRules(true, null);
        defaultProvider.setParameterizedCompletionParams('(', ", ", ')');
        //Lua default fucntions
        /* Do we really need those two global vars in autocompletion? */
        defaultProvider.addCompletion(new BasicCompletion(defaultProvider, "_G"));
        defaultProvider.addCompletion(new BasicCompletion(defaultProvider, "_VERSION"));

        FunctionCompletion assertF1 = new FunctionCompletion(defaultProvider, "assert", OPTS);
        assertF1.setShortDescription(autocompletion.getString("ipairs")/*"Issues an error when the value of its argument v is false (i.e., nil or false); otherwise, returns all its arguments. message is an error message; when absent, it defaults to \"assertion failed!\""*/);
        assertF1.setParams(Arrays.asList(
                new Parameter(BOOL, "v", true)
        ));
        defaultProvider.addCompletion(assertF1);
        FunctionCompletion assertF2 = new FunctionCompletion(defaultProvider, "assert", OPTS);
        assertF2.setShortDescription("Issues an error when the value of its argument v is false (i.e., nil or false); otherwise, returns all its arguments. message is an error message; when absent, it defaults to \"assertion failed!\"");
        assertF2.setParams(Arrays.asList(
                new Parameter(BOOL, "v, "),
                new Parameter(STR, "message", true)
        ));
        defaultProvider.addCompletion(assertF2);

        FunctionCompletion dofileF1 = new FunctionCompletion(defaultProvider, "dofile", OPTS);
        dofileF1.setShortDescription("Opens the named file and executes its contents as a Lua chunk. Executes the contents of the standard input (stdin). In case of errors, dofile propagates the error to its caller (that is, dofile does not run in protected mode).");
        dofileF1.setReturnValueDescription("Returns all values returned by the chunk.");
        defaultProvider.addCompletion(dofileF1);
        FunctionCompletion dofileF2 = new FunctionCompletion(defaultProvider, "dofile", OPTS);
        dofileF2.setShortDescription("Opens the named file and executes its contents as a Lua chunk. In case of errors, dofile propagates the error to its caller (that is, dofile does not run in protected mode).");
        dofileF2.setDefinedIn("Lua 5.2 basic library");
        dofileF2.setParams(Arrays.asList(
                new Parameter(STR, "filename", true)
        ));
        dofileF2.setReturnValueDescription("Returns all values returned by the chunk.");
        defaultProvider.addCompletion(dofileF2);

        FunctionCompletion errorF1 = new FunctionCompletion(defaultProvider, "error", NULL);
        errorF1.setShortDescription("Terminates the last protected function called and returns message as the error object. Function error never returns.\n"
                + "Usually, error adds some information about the error position at the beginning of the message, if the message is a string. The level argument specifies how to get the error position. With level 1 (the default), the error position is where the error function was called. Level 2 points the error to where the function that called error was called; and so on. Passing a level 0 avoids the addition of error position information to the message.");
        errorF1.setParams(Arrays.asList(
                new Parameter(STR, "message", true)
        ));
        defaultProvider.addCompletion(errorF1);
        FunctionCompletion errorF2 = new FunctionCompletion(defaultProvider, "error", NULL);
        errorF2.setShortDescription("Terminates the last protected function called and returns message as the error object. Function error never returns.\n"
                + "Usually, error adds some information about the error position at the beginning of the message, if the message is a string. The level argument specifies how to get the error position. With level 1 (the default), the error position is where the error function was called. Level 2 points the error to where the function that called error was called; and so on. Passing a level 0 avoids the addition of error position information to the message.");
        errorF2.setParams(Arrays.asList(
                new Parameter(STR, "message"),
                new Parameter(NUM, "level", true)
        ));
        defaultProvider.addCompletion(errorF2);

        FunctionCompletion test = new FunctionCompletion(defaultProvider, "name", "type");
        test.setDefinedIn("Defined In");
        test.setParams(Arrays.asList(new ParameterizedCompletion.Parameter("type1", "param1"), new ParameterizedCompletion.Parameter("type2", "param2", true)));
        test.setRelevance(1);
        test.setReturnValueDescription("Return description");
        test.setShortDescription("Short description");
        test.setSummary("Summary");
        defaultProvider.addCompletion(test);
        //OpenOS API

        //Shortcuts
        /* TODO */
        //
        LanguageAwareCompletionProvider p = new LanguageAwareCompletionProvider(defaultProvider);
        return p;
    }
}
