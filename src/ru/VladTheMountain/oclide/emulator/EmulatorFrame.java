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
package ru.VladTheMountain.oclide.emulator;

/**
 *
 * @author VladTheMountain
 */
public class EmulatorFrame extends javax.swing.JFrame {

    javax.swing.JPanel mainPanel;
    javax.swing.JTextArea shell;
    //
    private totoro.ocelot.brain.workspace.Workspace temporaryWorkspace;

    private static final long serialVersionUID = 1L;

    public EmulatorFrame() {
        initComponents();
        this.pack();
        javax.swing.Timer t = new javax.swing.Timer(300, (java.awt.event.ActionEvent e) -> {
            this.repaint();
        });
        try {
            initWorkspace();
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(EmulatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    void initComponents() {
        mainPanel = new javax.swing.JPanel();
        shell = new javax.swing.JTextArea();
        //
        shell.setBackground(java.awt.Color.BLACK);
        shell.setForeground(java.awt.Color.WHITE);
        shell.setFont(java.awt.Font.getFont("Monospaced 12 Plain"));
        //
        mainPanel.add(shell);
        //
        this.getContentPane().add(mainPanel);
    }

    void initWorkspace() throws java.lang.InterruptedException {
        totoro.ocelot.brain.Ocelot.initialize();
        //Workspace initialization
        java.io.File tempWork = new java.io.File("workspace/current");
        if (!(tempWork.exists())) {
            tempWork.mkdirs();
        }
        this.temporaryWorkspace = new totoro.ocelot.brain.workspace.Workspace(tempWork.toPath());
        //Computer creation
        totoro.ocelot.brain.entity.Case tPC = new totoro.ocelot.brain.entity.Case(totoro.ocelot.brain.util.Tier.Two());
        this.temporaryWorkspace.add(tPC);
        //CPU
        totoro.ocelot.brain.entity.CPU tCPU = new totoro.ocelot.brain.entity.CPU(totoro.ocelot.brain.util.Tier.Three());
        tPC.add(tCPU);
        //Memory
        totoro.ocelot.brain.entity.Memory tM1 = new totoro.ocelot.brain.entity.Memory(totoro.ocelot.brain.util.Tier.Four());
        tPC.add(tM1);
        //GPU
        totoro.ocelot.brain.entity.GraphicsCard tGPU = new totoro.ocelot.brain.entity.GraphicsCard(totoro.ocelot.brain.util.Tier.Three());
        tPC.add(tGPU);
        //HDD
        totoro.ocelot.brain.entity.HDDManaged tHDD = new totoro.ocelot.brain.entity.HDDManaged(totoro.ocelot.brain.util.Tier.Three());
        tPC.add(tHDD);
        //EEPROM
        //What kind of Cyberpunk is this?
        totoro.ocelot.brain.loot.Loot.LootFactory tMOSEFI = totoro.ocelot.brain.loot.Loot.MineOSEFIEEPROM(); //O_O
        tPC.add(tMOSEFI.create());
        //Floppy
        totoro.ocelot.brain.loot.Loot.LootFactory tOOSF = totoro.ocelot.brain.loot.Loot.OpenOsFloppy();
        tPC.add(tOOSF.create());
        //Screen
        totoro.ocelot.brain.entity.Screen tScreen = new totoro.ocelot.brain.entity.Screen(totoro.ocelot.brain.util.Tier.Two());
        tPC.add(tScreen);
        //Custom Data (for some reason)
        tPC.setCustomData(new totoro.ocelot.brain.nbt.persistence.PersistableString("hi?"));
        //EventListeners (I wonder if to turn this thing off...)
        /*
        totoro.ocelot.brain.event.EventBus.listenTo(totoro.ocelot.brain.event.BeepEvent.class, new EventListener(){});
         */
        //Turnin' on
        tPC.turnOn();
        //Why update? Really
        while (this.temporaryWorkspace.getIngameTime() < 20) //Why 20?
        {
            this.temporaryWorkspace.update();
            Thread.sleep(50);
        }
        //Why should I make a snapshot?... Ah, whatever
        saveWorkspace(this.temporaryWorkspace);

        //100 секунд и нихрена
        while (this.temporaryWorkspace.getIngameTime() < 100000) {
            for (int i = 1; i <= tScreen.getHeight(); i++) {
                for (int j = 1; j <= tScreen.getWidth(); j++) {
                    this.shell.setText(this.shell.getText() + tScreen.get(i, j));
                }
            }
            this.temporaryWorkspace.update();
            Thread.sleep(50);
        }

        tPC.turnOff();
        totoro.ocelot.brain.Ocelot.shutdown();
    }

    void saveWorkspace(totoro.ocelot.brain.workspace.Workspace w) {
        //Save the 'w'
        totoro.ocelot.brain.nbt.NBTTagCompound nbt = new totoro.ocelot.brain.nbt.NBTTagCompound();
        w.save(nbt);
        java.io.File tempMachineState = new java.io.File("temp/machineState.log");
        if (!(tempMachineState.exists())) {
            try {
                new java.io.File(tempMachineState.getParent()).mkdirs();
                tempMachineState.createNewFile();
            } catch (java.io.IOException ex) {
                java.util.logging.Logger.getLogger(EmulatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        try {
            java.nio.file.Files.write(tempMachineState.toPath(), String.valueOf(nbt).getBytes());
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(EmulatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
