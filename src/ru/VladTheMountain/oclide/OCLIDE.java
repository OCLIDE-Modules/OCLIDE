/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.VladTheMountain.oclide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import ru.VladTheMountain.oclide.main.MainFrame;

/**
 *
 * @author VladTheMountain
 */
public class OCLIDE {

    private static JProgressBar progressBar;
    private static JLabel statusLabel;
    static Timer t;

    public static void main(String[] args) throws URISyntaxException {
        /*JDialog fileChecker = new JDialog((JFrame) null, "Prepairing to start...");
        progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
        statusLabel = new JLabel("Text");
        fileChecker.setLayout(new FlowLayout(FlowLayout.CENTER));
        fileChecker.add(statusLabel);
        fileChecker.add(progressBar);
        fileChecker.setSize(600, 200);
        fileChecker.setResizable(false);
        fileChecker.setVisible(true);
        t = new Timer(300, (ActionEvent e) -> {
            fileChecker.repaint();
        });
        t.start();
        checkFiles();*/
        JOptionPane.showMessageDialog(null, "This product is UNFINISHED. The following \'app\' is just a GUI skeleton, not a full release.\n\n"
                + " The MIT License\n"
                + " \n"
                + " © 2020 Vladislav Gorskii.\n"
                + " \n"
                + " Permission is hereby granted, free of charge, to any person obtaining a copy\n"
                + " of this software and associated documentation files (the \"Software\"), to deal\n"
                + " in the Software without restriction, including without limitation the rights\n"
                + " to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n"
                + " copies of the Software, and to permit persons to whom the Software is\n"
                + " furnished to do so, subject to the following conditions:\n"
                + " \n"
                + " The above copyright notice and this permission notice shall be included in\n"
                + " all copies or substantial portions of the Software.\n"
                + " \n"
                + " THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n"
                + " IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n"
                + " FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n"
                + " AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n"
                + " LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n"
                + " OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n"
                + " THE SOFTWARE.", "WARNING", JOptionPane.ERROR_MESSAGE);
        new MainFrame().setVisible(true);
    }

    static void checkFiles() throws URISyntaxException {
        String OpenOSRepo = "https://raw.githubusercontent.com/MightyPirates/OpenComputers/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos";
        String Plan9kRepo = "https://raw.githubusercontent.com/MightyPirates/OpenComputers/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/plan9k";
        String[] openosFiles = {
            //root
            "/init.lua",
            "/.prop",
            // bin
            "/bin/address.lua",
            "/bin/alias.lua",
            "/bin/cat.lua",
            "/bin/cd.lua",
            "/bin/clear.lua",
            "/bin/cp.lua",
            "/bin/date.lua",
            "/bin/df.lua",
            "/bin/dmesg.lua",
            "/bin/echo.lua",
            "/bin/edit.lua",
            "/bin/grep.lua",
            "/bin/head.lua",
            "/bin/hostname.lua",
            "/bin/install.lua",
            "/bin/label.lua",
            "/bin/less.lua",
            "/bin/ln.lua",
            "/bin/ls.lua",
            "/bin/lshw.lua",
            "/bin/lua.lua",
            "/bin/man.lua",
            "/bin/mkdir.lua",
            "/bin/mount.lua",
            "/bin/mv.lua",
            "/bin/pastebin.lua",
            "/bin/primary.lua",
            "/bin/pwd.lua",
            "/bin/rc.lua",
            "/bin/reboot.lua",
            "/bin/redstone.lua",
            "/bin/resolution.lua",
            "/bin/rm.lua",
            "/bin/rmdir.lua",
            "/bin/set.lua",
            "/bin/sh.lua",
            "/bin/shutdown.lua",
            "/bin/umount.lua",
            "/bin/unalias.lua",
            "/bin/unset.lua",
            "/bin/uptime.lua",
            "/bin/useradd.lua",
            "/bin/userdel.lua",
            "/bin/wget.lua",
            "/bin/which.lua",
            "/bin/yes.lua",
            // boot
            "/boot/00_base.lua",
            "/boot/01_process.lua",
            "/boot/02_os.lua",
            "/boot/03_io.lua",
            "/boot/04_component.lua",
            "/boot/10_devfs.lua",
            "/boot/89_rc.lua",
            "/boot/90_filesystem.lua",
            "/boot/91_gpu.lua",
            "/boot/92_keyboard.lua",
            "/boot/93_term.lua",
            "/boot/94_shell.lua",
            // etc
            "/etc/motd",
            "/etc/profile.lua",
            "/etc/rc.cfg",
            // etc/rc.d
            "/etc/rc.d/example.lua",
            // home
            "/home/.shrc",
            // lib
            "/lib/bit32.lua",
            "/lib/buffer.lua",
            "/lib/colors.lua",
            "/lib/devfs.lua",
            "/lib/event.lua",
            "/lib/filesystem.lua",
            "/lib/internet.lua",
            "/lib/io.lua",
            "/lib/keyboard.lua",
            "/lib/note.lua",
            "/lib/package.lua",
            "/lib/pipe.lua",
            "/lib/process.lua",
            "/lib/rc.lua",
            "/lib/serialization.lua",
            "/lib/sh.lua",
            "/lib/shell.lua",
            "/lib/sides.lua",
            "/lib/term.lua",
            "/lib/text.lua",
            "/lib/thread.lua",
            "/lib/transforms.lua",
            "/lib/tty.lua",
            "/lib/uuid.lua",
            "/lib/vt100.lua",
            // lib/core
            "/lib/core/boot.lua",
            "/lib/core/cursor.lua",
            "/lib/core/device_labeling.lua",
            "/lib/core/full_buffer.lua",
            "/lib/core/full_cursor.lua",
            "/lib/core/full_event.lua",
            "/lib/core/full_filesystem.lua",
            "/lib/core/full_keyboard.lua",
            "/lib/core/full_ls.lua",
            "/lib/core/full_sh.lua",
            "/lib/core/full_shell.lua",
            "/lib/core/full_text.lua",
            "/lib/core/full_transforms.lua",
            "/lib/core/full_vt.lua",
            "/lib/core/install_basics.lua",
            "/lib/core/install_utils.lua",
            "/lib/core/lua_shell.lua",
            // lib/tools
            "/lib/tools/programLocations.lua",
            "/lib/tools/transfer.lua",
            // usr/man
            "/usr/man/address",
            "/usr/man/alias",
            "/usr/man/cat",
            "/usr/man/cd",
            "/usr/man/clear",
            "/usr/man/cp",
            "/usr/man/date",
            "/usr/man/df",
            "/usr/man/dmesg",
            "/usr/man/echo",
            "/usr/man/edit",
            "/usr/man/grep",
            "/usr/man/head",
            "/usr/man/hostname",
            "/usr/man/install",
            "/usr/man/label",
            "/usr/man/less",
            "/usr/man/ln",
            "/usr/man/ls",
            "/usr/man/lshw",
            "/usr/man/lua",
            "/usr/man/man",
            "/usr/man/mkdir",
            "/usr/man/more",
            "/usr/man/mount",
            "/usr/man/mv",
            "/usr/man/pastebin",
            "/usr/man/primary",
            "/usr/man/pwd",
            "/usr/man/rc",
            "/usr/man/reboot",
            "/usr/man/redstone",
            "/usr/man/resolution",
            "/usr/man/rm",
            "/usr/man/rmdir",
            "/usr/man/set",
            "/usr/man/sh",
            "/usr/man/shutdown",
            "/usr/man/umount",
            "/usr/man/unalias",
            "/usr/man/unset",
            "/usr/man/uptime",
            "/usr/man/useradd",
            "/usr/man/userdel",
            "/usr/man/wget",
            "/usr/man/which",
            "/usr/man/yes",
            // usr/misc
            "/usr/misc/greetings.txt"
        };
        String[] plan9kFiles = {
            // root
            "/init.lua",
            "/.prop",
            // bin
            "/bin/cat.lua",
            "/bin/clear.lua",
            "/bin/components.lua",
            "/bin/cp.lua",
            "/bin/dd.lua",
            "/bin/df.lua",
            "/bin/dmesg.lua",
            "/bin/du.lua",
            "/bin/echo.lua",
            "/bin/edit.lua",
            "/bin/find.lua",
            "/bin/getty.lua",
            "/bin/hostname.lua",
            "/bin/init.lua",
            "/bin/install.lua",
            "/bin/kill.lua",
            "/bin/label.lua",
            "/bin/ln.lua",
            "/bin/ls.lua",
            "/bin/lua.lua",
            "/bin/mkdir.lua",
            "/bin/more.lua",
            "/bin/mount.cow.lua",
            "/bin/mount.lua",
            "/bin/mount.msdos.lua",
            "/bin/mv.lua",
            "/bin/passwd.lua",
            "/bin/pastebin.lua",
            "/bin/ps.lua",
            "/bin/pwd.lua",
            "/bin/rc.lua",
            "/bin/readkey.lua",
            "/bin/reboot.lua",
            "/bin/resolution.lua",
            "/bin/rm.lua",
            "/bin/sandbox.lua",
            "/bin/sh.lua",
            "/bin/shutdown.lua",
            "/bin/sleep.lua",
            "/bin/sshd.lua",
            "/bin/tee.lua",
            "/bin/touch.lua",
            "/bin/uptime.lua",
            "/bin/watch.lua",
            "/bin/wc.lua",
            "/bin/wget.lua",
            // boot/kernel
            "/boot/kernel/pipes",
            // etc/rc.d
            "/etc/rc.d/autoupdate.lua",
            "/etc/rc.d/sshd.lua",
            // lib
            "/lib/colors.lua",
            "/lib/event.lua",
            "/lib/internet.lua",
            "/lib/msdosfs.lua",
            "/lib/rc.lua",
            "/lib/serialization.lua",
            "/lib/shell.lua",
            "/lib/sides.lua",
            "/lib/term.lua",
            "/lib/text.lua",
            // lib/modules/base
            "/lib/modules/base/01_gc.lua",
            "/lib/modules/base/01_util.lua",
            "/lib/modules/base/02_cmd.lua",
            "/lib/modules/base/05_vfs.lua",
            "/lib/modules/base/06_cowfs.lua",
            "/lib/modules/base/09_rootfs.lua",
            "/lib/modules/base/10_devfs.lua",
            "/lib/modules/base/10_procfs.lua",
            "/lib/modules/base/10_sysfs.lua",
            "/lib/modules/base/11_block.lua",
            "/lib/modules/base/12_mount.lua",
            "/lib/modules/base/15_keventd.lua",
            "/lib/modules/base/15_userspace.lua",
            "/lib/modules/base/16_buffer.lua",
            "/lib/modules/base/16_component.lua",
            "/lib/modules/base/16_partition.lua",
            "/lib/modules/base/16_require.lua",
            "/lib/modules/base/17_chatbox.lua",
            "/lib/modules/base/17_data.lua",
            "/lib/modules/base/17_drive.lua",
            "/lib/modules/base/17_eeprom.lua",
            "/lib/modules/base/17_gpt.lua",
            "/lib/modules/base/17_io.lua",
            "/lib/modules/base/17_ipc.lua",
            "/lib/modules/base/17_keyboard.lua",
            "/lib/modules/base/17_nfc.lua",
            "/lib/modules/base/17_tape.lua",
            "/lib/modules/base/18_pty.lua",
            "/lib/modules/base/18_syscall.lua",
            "/lib/modules/base/19_cgroups.lua",
            "/lib/modules/base/19_manageg.lua",
            "/lib/modules/base/20_threading.lua",
            "/lib/modules/base/21_threadUtil.lua",
            "/lib/modules/base/21_timer.lua",
            "/lib/modules/base/25_init.lua",
            // usr/bin
            "/usr/bin/base64.lua",
            "/usr/bin/deflate.lua",
            "/usr/bin/go.lua",
            "/usr/bin/gpg.lua",
            "/usr/bin/inflate.lua",
            "/usr/bin/md5sum.lua",
            "/usr/bin/mkdosfs.lua",
            "/usr/bin/mpt.lua",
            "/usr/bin/sha256sum.lua",
            "/usr/bin/ssh.lua",
            // usr/lib
            "/usr/lib/data.lua",
            "/usr/lib/robot.lua",
            // usr/man
            "/usr/man/pipes",
            // usr/sbin
            "/usr/sbin/sshsession.lua",
            // var/lib/mpt
            "/var/lib/mpt/config.db",
            "/var/lib/mpt/mpt.db"
        };

        if (!(new File("environment/").exists())) {
            new File("environment/OpenOS").mkdirs();
            new File("environment/Plan9k").mkdirs();
        }

        progressBar.setValue(0);
        progressBar.setMaximum(openosFiles.length);
        for (int i = 0; i < openosFiles.length; i++) {
            if (!(new File("environment/OpenOS/" + openosFiles[i]).exists())) {
                FileOutputStream fos = null;
                try {
                    new File("environment/OpenOS/" + openosFiles[i]).getParentFile().mkdirs();
                    ReadableByteChannel rbc = Channels.newChannel(new URL(OpenOSRepo + openosFiles[i]).openStream());
                    fos = new FileOutputStream("environment/OpenOS/" + openosFiles[i]);
                    fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
                    statusLabel.setText("Downloading: " + OpenOSRepo + openosFiles[i] + " -> " + "environment/OpenOS/" + openosFiles[i] + ". Total - " + Files.size(Paths.get("environment/OpenOS/" + openosFiles[i])) + " bytes.");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(OCLIDE.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(OCLIDE.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        Logger.getLogger(OCLIDE.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            progressBar.setValue(i + 1);
        }

        progressBar.setValue(0);
        progressBar.setMaximum(plan9kFiles.length);
        for (int i = 0; i < plan9kFiles.length; i++) {
            if (!(new File("environment/Plan9k/" + plan9kFiles[i]).exists())) {
                FileOutputStream fos = null;
                try {
                    new File("environment/Plan9k/" + plan9kFiles[i]).getParentFile().mkdirs();
                    ReadableByteChannel rbc = Channels.newChannel(new URL(Plan9kRepo + plan9kFiles[i]).openStream());
                    fos = new FileOutputStream("environment/Plan9k/" + plan9kFiles[i]);
                    fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
                    statusLabel.setText("Downloading: " + Plan9kRepo + plan9kFiles[i] + " -> " + "environment/Plan9k/" + plan9kFiles[i] + ". Total - " + Files.size(Paths.get("environment/Plan9k/" + plan9kFiles[i])) + " bytes.");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(OCLIDE.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(OCLIDE.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        Logger.getLogger(OCLIDE.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            progressBar.setValue(i + 1);
        }
    }
}
