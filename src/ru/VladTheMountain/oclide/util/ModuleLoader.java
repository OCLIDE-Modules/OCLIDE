/*
 * The MIT License
 *
 * Copyright 2021 Vladislav Gosrkii.
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
package ru.VladTheMountain.oclide.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Downloads binaries from https://github.com/OCLIDE-Modules/
 *
 * @author VladTheMountain
 */
public class ModuleLoader {

    /**
     * Downloads OCEmu build from https://github.com/OCLIDE-Modules/OCLIDE-OCEmu
     * to OCEmu folder
     */
    public static void downloadOCEmu() {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            try {
                recursivelyCopyFiles(new File(new URL("https://github.com/OCLIDE-Modules/OCLIDE-OCEmu/ocemu-windows").toURI()), new File(System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + "OCEmu"));
            } catch (MalformedURLException | URISyntaxException ex) {
                Logger.getLogger(ModuleLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

        }
    }

    //Util
    private static void recursivelyCopyFiles(File src, File targ) {
        File[] files = src.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isFile()) {
                try {
                    Files.copy(f.toPath(), new File(targ.getAbsolutePath() + FileSystems.getDefault().getSeparator() + f.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    Logger.getLogger(ModuleLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                /*try {
                    FileUtils.copyDirectory(f, targ);
                } catch (IOException ex) {
                    Logger.getLogger(ModuleLoader.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
            if (f.isDirectory()) {
                recursivelyCopyFiles(src, targ);
            }
        }
    }
}
