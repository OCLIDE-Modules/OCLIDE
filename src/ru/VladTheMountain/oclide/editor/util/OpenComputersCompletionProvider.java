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
        assertF1.setShortDescription(autocompletion.getString("assert")/*"Issues an error when the value of its argument v is false (i.e., nil or false); otherwise, returns all its arguments. message is an error message; when absent, it defaults to \"assertion failed!\""*/);
        assertF1.setParams(Arrays.asList(
                new Parameter(BOOL, "v", true)
        ));
        defaultProvider.addCompletion(assertF1);
        FunctionCompletion assertF2 = new FunctionCompletion(defaultProvider, "assert", OPTS);
        assertF2.setShortDescription(autocompletion.getString("assert"));
        assertF2.setParams(Arrays.asList(
                new Parameter(BOOL, "v, "),
                new Parameter(STR, "message", true)
        ));
        defaultProvider.addCompletion(assertF2);

        FunctionCompletion dofileF1 = new FunctionCompletion(defaultProvider, "dofile", OPTS);
        dofileF1.setShortDescription(autocompletion.getString("dofile"));
        defaultProvider.addCompletion(dofileF1);
        FunctionCompletion dofileF2 = new FunctionCompletion(defaultProvider, "dofile", OPTS);
        dofileF2.setShortDescription(autocompletion.getString("dofile"));
        dofileF2.setParams(Arrays.asList(
                new Parameter(STR, "filename", true)
        ));
        defaultProvider.addCompletion(dofileF2);

        FunctionCompletion errorF1 = new FunctionCompletion(defaultProvider, "error", NULL);
        errorF1.setShortDescription(autocompletion.getString("error"));
        errorF1.setParams(Arrays.asList(
                new Parameter(STR, "message", true)
        ));
        defaultProvider.addCompletion(errorF1);
        FunctionCompletion errorF2 = new FunctionCompletion(defaultProvider, "error", NULL);
        errorF2.setShortDescription(autocompletion.getString("error"));
        errorF2.setParams(Arrays.asList(
                new Parameter(STR, "message"),
                new Parameter(NUM, "level", true)
        ));
        defaultProvider.addCompletion(errorF2);

        FunctionCompletion getmetatableF = new FunctionCompletion(defaultProvider, "getmetatable", TABL);
        getmetatableF.setShortDescription(autocompletion.getString("getmetatable"));
        getmetatableF.setParams(Arrays.asList(
                new Parameter(TABL, "object", true)));
        defaultProvider.addCompletion(getmetatableF);

        FunctionCompletion ipairsF = new FunctionCompletion(defaultProvider, "ipairs", NUM + ", " + TABL + ", " + 0);
        ipairsF.setShortDescription(autocompletion.getString("ipairs"));
        ipairsF.setParams(Arrays.asList(
                new Parameter(TABL, "t", true)));
        defaultProvider.addCompletion(ipairsF);

        FunctionCompletion loadF1 = new FunctionCompletion(defaultProvider, "load", OPTS);
        loadF1.setShortDescription(autocompletion.getString("load"));

        /*FunctionCompletion test = new FunctionCompletion(defaultProvider, "name", "type");
        test.setDefinedIn("Defined In");
        test.setParams(Arrays.asList(new ParameterizedCompletion.Parameter("type1", "param1"), new ParameterizedCompletion.Parameter("type2", "param2", true)));
        test.setRelevance(1);
        test.setReturnValueDescription("Return description");
        test.setShortDescription("Short description");
        test.setSummary("Summary");
        defaultProvider.addCompletion(test);*/ //OpenOS API
        //Shortcuts
        /* TODO */ //
        LanguageAwareCompletionProvider p = new LanguageAwareCompletionProvider(defaultProvider);
        return p;
    }
}
