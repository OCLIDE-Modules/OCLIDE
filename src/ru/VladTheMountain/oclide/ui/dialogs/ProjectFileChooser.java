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

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;

/**
 *
 * @author VladTheMountain
 */
public class ProjectFileChooser extends JFileChooser {

    final ResourceBundle localiztionResource = ResourceBundle.getBundle("ru.VladTheMountain.oclide.resources.dialog.Dialog", Locale.getDefault());

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public ProjectFileChooser() {
        this.setCurrentDirectory(new File(System.getProperty("user.home")));
        this.setFileSelectionMode(DIRECTORIES_ONLY);
    }

}
