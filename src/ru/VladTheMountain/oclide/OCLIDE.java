/*
 * The MIT License
 *
 * Copyright 2020 Vladislav Gorskii.
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
package ru.VladTheMountain.oclide;

import java.awt.event.ActionEvent;
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
import javax.swing.Timer;
import ru.VladTheMountain.oclide.main.MainFrame;

/**
 *
 * @author VladTheMountain
 */
public class OCLIDE extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new form OCLIDE
     */
    public OCLIDE() {
        initComponents();
        this.setVisible(true);
        Timer t = new Timer(300, (ActionEvent e) -> {
            javax.swing.FocusManager.getCurrentManager().getActiveWindow().repaint();
            javax.swing.FocusManager.getCurrentManager().getActiveWindow().revalidate();
        });
        t.start();
        /*try {
            this.checkFiles();
        } catch (URISyntaxException ex) {
            Logger.getLogger(OCLIDE.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        this.setVisible(false);
        new MainFrame().setVisible(true);
    }

    @Deprecated
    private void checkFiles() throws URISyntaxException {
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

        this.progressBar.setValue(0);
        this.progressBar.setMaximum(openosFiles.length);
        for (int i = 0; i < openosFiles.length; i++) {
            if (!(new File("environment/OpenOS/" + openosFiles[i]).exists())) {
                FileOutputStream fos = null;
                try {
                    new File("environment/OpenOS/" + openosFiles[i]).getParentFile().mkdirs();
                    ReadableByteChannel rbc = Channels.newChannel(new URL(OpenOSRepo + openosFiles[i]).openStream());
                    fos = new FileOutputStream("environment/OpenOS/" + openosFiles[i]);
                    fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
                    /*this.statusLabel.setText*/
                    System.out.println("Downloading: " + OpenOSRepo + openosFiles[i] + " -> " + "environment/OpenOS/" + openosFiles[i] + ". Total - " + Files.size(Paths.get("environment/OpenOS/" + openosFiles[i])) + " bytes.");
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
            this.progressBar.setValue(i + 1);
        }

        this.progressBar.setValue(0);
        this.progressBar.setMaximum(plan9kFiles.length);
        for (int i = 0; i < plan9kFiles.length; i++) {
            if (!(new File("environment/Plan9k/" + plan9kFiles[i]).exists())) {
                FileOutputStream fos = null;
                try {
                    new File("environment/Plan9k/" + plan9kFiles[i]).getParentFile().mkdirs();
                    ReadableByteChannel rbc = Channels.newChannel(new URL(Plan9kRepo + plan9kFiles[i]).openStream());
                    fos = new FileOutputStream("environment/Plan9k/" + plan9kFiles[i]);
                    fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
                    this.statusLabel.setText("Downloading: " + Plan9kRepo + plan9kFiles[i] + " -> " + "environment/Plan9k/" + plan9kFiles[i] + ". Total - " + Files.size(Paths.get("environment/Plan9k/" + plan9kFiles[i])) + " bytes.");
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
            this.progressBar.setValue(i + 1);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressBar = new javax.swing.JProgressBar();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        progressBar.setValue(100);

        statusLabel.setText("Sample Text");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(statusLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(255, Short.MAX_VALUE)
                .addComponent(statusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(400, 300));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OCLIDE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            OCLIDE oclide;
            oclide = new OCLIDE();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
