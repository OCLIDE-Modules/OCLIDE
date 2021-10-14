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
package io.VladTheMountain.oclide.editor;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.LanguageAwareCompletionProvider;
import org.fife.ui.autocomplete.ParameterizedCompletion.Parameter;

/**
 * This class provides an instance of {@code LanguageAwareProvider} via
 * {@link #getProvider()} method, which contains autocompletion stuff for both
 * Lua 5.3 and OpenOS 1.7
 *
 * @see org.fife.ui.autocomplete.LanguageAwareCompletionProvider
 * @author VladTheMountain
 */
public class OCLIDECompletionProvider {

    private static DefaultCompletionProvider defaultProvider;

    // 'Since' //
    //Lua
    private static final String LUA_53 = "Lua 5.3";
    //OpenOS
    private static final String OPENOS_16 = "OpenOS 1.6";
    private static final String OPENOS_17 = "OpenOS 1.7";

    /**
     * Constructs and returns the {@code LanguageAwareProvider}
     *
     * @return An instance of {@code LanguageAwareProvider} with ready
     * autocompletion rules
     * @deprecated Will be reworked to be automated via Parser.
     * @see io.VladTheMountain.parser
     */
    public static CompletionProvider getProvider() {
        ResourceBundle autocompletion = ResourceBundle.getBundle("ru.VladTheMountain.oclide.resources.autocompletion.Autocompletion", Locale.getDefault());
        defaultProvider = new DefaultCompletionProvider();
        defaultProvider.setAutoActivationRules(true, null);
        defaultProvider.setParameterizedCompletionParams('(', ", ", ')');

        //Lua default
        /* Do we really need those two global vars in autocompletion? */
        defaultProvider.addCompletion(new BasicCompletion(defaultProvider, "_G"));
        defaultProvider.addCompletion(new BasicCompletion(defaultProvider, "_VERSION"));

        createCompletion("assert", LUA_53, autocompletion.getString("assert"), "v's arguments", new CustomParameter[][]{
            {new CustomParameter("object", "v", true, "Object to return")},
            {new CustomParameter("object", "v", false, "Object to return"), new CustomParameter("string", "message", true, "Message to print if 'v' is false")}
        });

        createCompletion("dofile", LUA_53, autocompletion.getString("dofile"), "Chunk's return values", new CustomParameter[][]{
            {new CustomParameter("string", "filename", true, "File to execute")},
            {}
        });

        createCompletion("error", LUA_53, autocompletion.getString("error"), "", new CustomParameter[][]{
            {new CustomParameter("string", "message", true, "Message to print")},
            {new CustomParameter("string", "message", false, "Message to print"), new CustomParameter("number", "level", true, "Way of getting the error position")}
        });

        createCompletion("getmetatable", LUA_53, autocompletion.getString("getmetatable"), "Returns 'object's __metatable field", new CustomParameter[][]{
            {new CustomParameter("table", "object", true, "Table to return __metatable value of")}
        });

        createCompletion("ipairs", LUA_53, autocompletion.getString("ipairs"), "Returns three values - an iterator function, the table t, and 0", new CustomParameter[][]{
            {new CustomParameter("table", "t", true, "Table to iterate through")}
        });

        createCompletion("load", LUA_53, autocompletion.getString("load"), "Returns the compiled chunk as a function", new CustomParameter[][]{
            {new CustomParameter("chunk", "chunk", true, "Chunk to load")},
            {new CustomParameter("chunk", "chunk", false, "Chunk to load"), new CustomParameter("string", "chunkname", true, "The name of the chunk for error messages and debug information")},
            {new CustomParameter("chunk", "chunk", false, "Chunk to load"), new CustomParameter("string", "chunkname", false, "The name of the chunk for error messages and debug information"), new CustomParameter("string", "mode", true, "Controls whether the chunk can be text or binary")},
            {new CustomParameter("chunk", "chunk", false, "Chunk to load"), new CustomParameter("string", "chunkname", false, "The name of the chunk for error messages and debug information"), new CustomParameter("string", "mode", false, "Controls whether the chunk can be text or binary"), new CustomParameter("object", "env", true, "The value to set the first upvalue of the chunk to")}
        });

        createCompletion("loadfile", LUA_53, autocompletion.getString("loadfile"), "The same as <code>load()</code>", new CustomParameter[][]{
            {new CustomParameter("string", "filename", true, "The file to get the chunk from")},
            {new CustomParameter("string", "filename", false, "The file to get the chunk from"), new CustomParameter("string", "mode", false, "Controls whether the chunk can be text or binary")},
            {new CustomParameter("string", "filename", false, "The file to get the chunk from"), new CustomParameter("string", "mode", false, "Controls whether the chunk can be text or binary"), new CustomParameter("object", "env", true, "The value to set the first upvalue of the chunk to")},
            {}
        });

        createCompletion("next", LUA_53, autocompletion.getString("next"), "Returns the next index of the table and its associated value", new CustomParameter[][]{
            {new CustomParameter("table", "table", true, "The table to traverse all fields of")},
            {new CustomParameter("table", "table", false, "The table to traverse all fields of"), new CustomParameter("number", "index", true, "The initial index")}
        });

        createCompletion("pairs", LUA_53, autocompletion.getString("pairs"), "Returns <code>next(t)</code>, <code>t</code> and <code>nil</code>", new CustomParameter[][]{
            {new CustomParameter("table", "t", true, "The table to iterate through")}
        });

        createCompletion("pcall", LUA_53, autocompletion.getString("pcall"), "Returns the status code of calling <code>f(...)</code>", new CustomParameter[][]{
            {new CustomParameter("function", "f", true, "Function to call")},
            {new CustomParameter("function", "f", false, "Function to call"), new CustomParameter("", "...", true, "Function arguments")}
        });

        createCompletion("print", LUA_53, autocompletion.getString("print"), "", new CustomParameter[][]{
            {new CustomParameter("", "...", true, "Object(-s) to print")}
        });

        createCompletion("rawequal", LUA_53, autocompletion.getString("rawequal"), "Returns boolean - result of __eq metamethod", new CustomParameter[][]{
            {new CustomParameter("object", "v1", false, "First object to compare"), new CustomParameter("object", "v2", true, "Second object to compare")}
        });

        createCompletion("rawget", LUA_53, autocompletion.getString("rawget"), "Returns value at <code>index</code> of <code>table</code>", new CustomParameter[][]{
            {new CustomParameter("table", "table", false, "Table to get value from"), new CustomParameter("number", "index", true, "Index of the <code>table</code> to get value of")}
        });

        createCompletion("rawlen", LUA_53, autocompletion.getString("rawlen"), "Returns the length of the object", new CustomParameter[][]{
            {new CustomParameter("table", "v", true, "Object to measure length of")}
        });

        createCompletion("rawset", LUA_53, autocompletion.getString("rawset"), "Returns <code>table</code>", new CustomParameter[][]{
            {new CustomParameter("table", "table", false, "the <code>table</code> in which you need to change the value"), new CustomParameter("number", "index", false, "the index of the table's cell to change value of"), new CustomParameter("object", "value", true, "the new value of the <code>table[index]</code>")}
        });

        createCompletion("select", LUA_53, autocompletion.getString("select"), "Returns argument <code>index</code>", new CustomParameter[][]{
            {new CustomParameter("number", "index", false, "the argument to return"), new CustomParameter("obejct", "...", true, "arguments")}
        });

        createCompletion("setmetatable", LUA_53, autocompletion.getString("setmetatable"), "Returns <code>table</code>", new CustomParameter[][]{
            {new CustomParameter("table", "table", false, "table to set metatable for"), new CustomParameter("metatable", "metatable", true, "metatable to set")}
        });

        createCompletion("tonumber", LUA_53, autocompletion.getString("tonumber"), "Returns <code>e</code> as a number", new CustomParameter[][]{
            {new CustomParameter("object", "e", true, "object to translate to a number")},
            {new CustomParameter("object", "e", false, "object to translate to a number"), new CustomParameter("number", "base", true, "the base number for <code>e</code>")}
        });

        createCompletion("tostring", LUA_53, autocompletion.getString("tostring"), "Returns <code>v</code> as a string", new CustomParameter[][]{
            {new CustomParameter("object", "v", true, "object to translate to a string")}
        });

        createCompletion("type", LUA_53, autocompletion.getString("type"), "Returns the type of <code>v</code>", new CustomParameter[][]{
            {new CustomParameter("object", "v", true, "object to get type of")}
        });

        createCompletion("xpcall", LUA_53, autocompletion.getString("xpcall"), "Returns the same as <code>pcall</code>", new CustomParameter[][]{
            {new CustomParameter("function", "f", false, "function to call"), new CustomParameter("object", "msgh", true, "message handler")},
            {new CustomParameter("function", "f", false, "function to call"), new CustomParameter("object", "msgh", false, "message handler"), new CustomParameter("...", "args", true, "arguments to pass to <code>f</code>")}
        });

        //Bit32
        createCompletion("bit32.arshift", LUA_53, autocompletion.getString("bit32_arshift"), "Returns the number <code>x</code> shifted <code>disp</code> bits to the right", new CustomParameter[][]{
            {new CustomParameter("number", "x", false, "number to shift"), new CustomParameter("number", "disp", true, "how much to shift")}
        });

        createCompletion("bit32.band", LUA_53, autocompletion.getString("bit32_band"), "Returns the bitwise and of its operands", new CustomParameter[][]{
            {new CustomParameter("...", "", true, "operands")}
        });

        createCompletion("bit32.bnot", LUA_53, autocompletion.getString("bit32_bnot"), "Returns the bitwise negation of <code>x</code>", new CustomParameter[][]{
            {new CustomParameter("object", "x", true, "operand")}
        });

        createCompletion("bit32.bor", LUA_53, autocompletion.getString("bit32_bor"), "Returns the bitwise or of its operands", new CustomParameter[][]{
            {new CustomParameter("...", "", true, "operands")}
        });

        createCompletion("bit32.btest", LUA_53, autocompletion.getString("bit32_btest"), "Returns a boolean signaling whether the bitwise and of its operands is different from zero", new CustomParameter[][]{
            {new CustomParameter("...", "", true, "operands")}
        });

        createCompletion("bit32.bxor", LUA_53, autocompletion.getString("bit32_bxor"), "Returns the bitwise exclusive or of its operands", new CustomParameter[][]{
            {new CustomParameter("...", "", true, "operands")}
        });

        createCompletion("bit32.extract", LUA_53, autocompletion.getString("bit32_extract"), "Returns the unsigned number formed by the bits <code>field</code> to <code>field + width - 1</code> from <code>n</code>", new CustomParameter[][]{
            {new CustomParameter("number", "n", false, ""), new CustomParameter("bits", "field", true, "")},
            {new CustomParameter("number", "n", false, ""), new CustomParameter("bits", "field", false, ""), new CustomParameter("number", "width", true, "")}
        });

        createCompletion("bit32.replace", LUA_53, autocompletion.getString("bit32_replace"), "Returns a copy of n with the bits <code>field</code> to <code>field + width - 1</code> replaced by the value <code>v</code>", new CustomParameter[][]{
            {new CustomParameter("number", "n", false, ""), new CustomParameter("object", "v", false, "new value"), new CustomParameter("bits", "field", true, "")},
            {new CustomParameter("number", "n", false, ""), new CustomParameter("object", "v", false, "new value"), new CustomParameter("bits", "field", false, ""), new CustomParameter("number", "width", true, "")}
        });

        createCompletion("bit32.lrotate", LUA_53, autocompletion.getString("bit32_lrotate"), "Returns the number <code>x</code> rotated <code>disp</code> bits to the left", new CustomParameter[][]{
            {new CustomParameter("number", "x", false, "number to rotate"), new CustomParameter("number", "disp", true, "how much to shift")}
        });

        createCompletion("bit32.lshift", LUA_53, autocompletion.getString("bit32_lshift"), "Returns the number <code>x</code> shifted <code>disp</code> bits to the left", new CustomParameter[][]{
            {new CustomParameter("number", "x", false, "number to shift"), new CustomParameter("number", "disp", true, "how much to shift")}
        });

        createCompletion("bit32.rrotate", LUA_53, autocompletion.getString("bit32_rrotate"), "Returns the number <code>x</code> rotated <code>disp</code> bits to the right", new CustomParameter[][]{
            {new CustomParameter("number", "x", false, "number to rotate"), new CustomParameter("number", "disp", true, "how much to shift")}
        });

        createCompletion("bit32.rshift", LUA_53, autocompletion.getString("bit32_rshift"), "Returns the number <code>x</code> shifted <code>disp</code> bits to the right", new CustomParameter[][]{
            {new CustomParameter("number", "x", false, "number to shift"), new CustomParameter("number", "disp", true, "how much to shift")}
        });

        //Coroutine
        createCompletion("coroutine.create", LUA_53, autocompletion.getString("coroutine_create"), "Returns a new coroutine", new CustomParameter[][]{
            {new CustomParameter("function", "f", true, "function to wrap into the coroutine")}
        });

        createCompletion("coroutine.resume", LUA_53, autocompletion.getString("coroutine_resume"), "Returns function output", new CustomParameter[][]{
            {new CustomParameter("coroutine", "co", true, "coroutine to continue execution of")},
            {new CustomParameter("coroutine", "co", false, "coroutine to continue execution of"), new CustomParameter("...", "vals", true, "values to pass to the function")}
        });

        createCompletion("coroutine.running", LUA_53, autocompletion.getString("coroutine_running"), "Returns the current coroutine + boolean", new CustomParameter[][]{
            {}
        });

        createCompletion("coroutine.status", LUA_53, autocompletion.getString("coroutine_status"), "Returns the status of the coroutine", new CustomParameter[][]{
            {new CustomParameter("coroutine", "co", true, "coroutine to get status of")}
        });

        createCompletion("coroutine.wrap", LUA_53, autocompletion.getString("coroutine_wrap"), "Returns a function that resumes the coroutine each time it is called", new CustomParameter[][]{
            {new CustomParameter("function", "f", true, "function to wrap into the coroutine")}
        });

        createCompletion("coroutine.yield", LUA_53, autocompletion.getString("coroutine_yield"), "", new CustomParameter[][]{
            {new CustomParameter("...", "", true, "arguments for <code>coroutine.resume</code>")}
        });

        //Debug
        createCompletion("debug.getinfo", LUA_53, autocompletion.getString("debug_getinfo"), "Returns a table with information about a function", new CustomParameter[][]{
            {new CustomParameter("function", "f", true, "function to get information about")},
            {new CustomParameter("function", "f", false, "function to get information about"), new CustomParameter("string", "what", true, "the string describing which fields to fill in")},
            {new CustomParameter("thread", "thread", false, "the thread to get running level from"), new CustomParameter("function", "f", true, "function to get information about")},
            {new CustomParameter("thread", "thread", false, "the thread to get running level from"), new CustomParameter("function", "f", false, "function to get information about"), new CustomParameter("string", "what", true, "the string describing which fields to fill in")}
        });

        createCompletion("debug.traceback", LUA_53, autocompletion.getString("debug_traceback"), "Returns a string with a traceback of the call stack", new CustomParameter[][]{
            {},
            {new CustomParameter("string", "message", true, "message to return before traceback")},
            {new CustomParameter("string", "message", false, "message to return before traceback"), new CustomParameter("number", "level", true, "at which level to start the traceback")},
            {new CustomParameter("thread", "thread", true, "the thread to get running level from")},
            {new CustomParameter("thread", "thread", false, "the thread to get running level from"), new CustomParameter("string", "message", false, "message to return before traceback")},
            {new CustomParameter("thread", "thread", false, "the thread to get running level from"), new CustomParameter("string", "message", false, "message to return before traceback"), new CustomParameter("number", "level", true, "at which level to start the traceback")}
        });

        //IO
        createCompletion("io.close", LUA_53, autocompletion.getString("io_close"), "", new CustomParameter[][]{
            {},
            {new CustomParameter("file", "file", true, "the file to close")}
        });

        createCompletion("io.flush", LUA_53, autocompletion.getString("io_flush"), "", new CustomParameter[][]{
            {}
        });

        createCompletion("io.input", LUA_53, autocompletion.getString("io_input"), "", new CustomParameter[][]{
            {},
            {new CustomParameter("file", "file", true, "the file to open in text mode")}
        });

        //Math
        //OS
        //Package
        //String
        //Table
        /* OpenOS API */ //Buffer
        //Colors
        //Component
        //Computer
        //Event
        //UUID
        createCompletion("uuid.next", OPENOS_16, "Returns 128 bit random identifiers, represented as a hex value in a string grouped by 8, 4, 4, 4, and 12 hex characters, separated by dashes. e.g. <code>34eb7b28-14d3-4767-b326-dd1609ba92e</code>. You might recognize this pattern as it is the same used for component addressing.", "Returns a new UUID", new CustomParameter[][]{
            {}
        });
        //Filesystem
        //Internet
        //Keyboard
        //Note
        //Process
        //RC
        //Robot
        //Serialization
        //Shell
        //Sides
        //Term
        //Text
        //Thread
        //Transforms
        //Unicode
        /* Component API */

        //
        Logger.getLogger(OCLIDECompletionProvider.class.getName()).log(Level.INFO, "Autocompletion complete");
        LanguageAwareCompletionProvider p = new LanguageAwareCompletionProvider(defaultProvider);
        return p;
    }

    /**
     * Creates a new FunctionCompletion with params
     *
     * @param name Function name
     * @param definedIn API version where function is first defined
     * @param shortDesc Function description
     * @param returnDesc Function return value's description
     * @param pars Function parameters' 2d-array
     */
    private static void createCompletion(String name, String definedIn, String shortDesc, String returnDesc, CustomParameter[][] pars) {
        //Every possible parameter's variant
        for (CustomParameter[] par : pars) {
            FunctionCompletion f = new FunctionCompletion(defaultProvider, name, "function");
            f.setDefinedIn(definedIn);
            f.setShortDescription(shortDesc);
            f.setReturnValueDescription(returnDesc);
            f.setParams(Arrays.asList(par));
            defaultProvider.addCompletion(f);
        }
    }
}

class CustomParameter extends Parameter {

    /**
     *
     * @param type Parameter's type
     * @param name Parameter's name
     * @param endParam If the pararmeter is final
     * @param desc Parameter's description
     */
    public CustomParameter(String type, String name, boolean endParam, String desc) {
        super(type, name, endParam);
        setDescription(desc);
    }

}
