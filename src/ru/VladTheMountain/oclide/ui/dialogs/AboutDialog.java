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
package ru.VladTheMountain.oclide.ui.dialogs;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Yes, it's a separate class for a single call of a single function. Don't ask
 * why and just scroll further.
 *
 * @author VladTheMounatin
 */
public class AboutDialog {

    final ResourceBundle localiztionResource = ResourceBundle.getBundle("io.VladTheMountain.oclide.resources.dialog.Dialog", Locale.getDefault());

    /**
     *
     * @param parent parent JFrame
     */
    public AboutDialog(JFrame parent) {
        JOptionPane.showMessageDialog(parent,
                "<html><body>"
                + "<h2>OCLIDE beta 0.1.0</h2>"
                + "<pre>Copyright (c) VladTheMountain (Vladislav Gorskii) 2021.</pre>"
                + "<p>"
                + "OCLIDE (OpenComputers Lua Integrated Development Environment) is an open-source Lua IDE for developing software for OpenComputers Minecraft mod.<br>"
                + "This program and it's source, until otherwise stated, are distributed under the MIT License."
                + "</p><br><p>"
                + "Lua 5.2 and Lua 5.3 documentation is taken from official Lua website <a href=\"https:\\lua.org\">lua.org</a>"
                + "</p>"
                + "<h3>Special thanks to:</h3><ul>"
                + "<li><a href=\"https://computercraft.ru/\">ComputerCraft.RU forum</a> for testing and reviewing the program during its development</li>"
                + "<li><a href=\"https://codacy.com\">Codacy</a> for creating such a powerful automated code review platform</li>"
                + "<li>Developers of the following open-source projects:<ul>"
                + "<li><a href=\"https://github.com/bobbylight/RSyntaxTextArea\">RSyntaxTextArea</a>, <a href=\"https://github.com/bobbylight/AutoComplete\">AutoComplete</a>, <a href=\"https://github.com/bobbylight/RSTAUI\">RSTAUI</a>, <a href=\"https://github.com/bobbylight/SpellChecker\">Spellchecker</a></li>"
                + "<li><a href=\"https://netbeans.apache.org/\">Apache NetBeans IDE</a></li>"
                + "<li><a href=\"https://github.com/zenith391/OCEmu\">OCEmu</a></li>"
                + "<li><a href=\"https://gitlab.com/cc-ru/ocelot/ocelot-brain\">Ocelot Brain</a> and <a href=\"https://gitlab.com/cc-ru/ocelot/ocelot-desktop\">Ocelot Desktop</a></li>"
                + "<li><a href=\"https://github.com/payonel/ocvm\">OCVM</a></li>"
                + "</ul></li>"
                + "<li><a href=\"https://github.com/misiuji\">misiuji</a> for help with compiling OCEmu binaries</li></ul>"
                + "</body></html>",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
