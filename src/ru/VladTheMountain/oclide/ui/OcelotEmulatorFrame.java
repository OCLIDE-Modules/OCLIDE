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
package ru.VladTheMountain.oclide.ui;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;
import ru.VladTheMountain.oclide.emulator.brain.CustomCanvas;
import totoro.ocelot.brain.Ocelot;
import totoro.ocelot.brain.entity.CPU;
import totoro.ocelot.brain.entity.Case;
import totoro.ocelot.brain.entity.GraphicsCard;
import totoro.ocelot.brain.entity.HDDManaged;
import totoro.ocelot.brain.entity.Memory;
import totoro.ocelot.brain.entity.Screen;
import totoro.ocelot.brain.loot.Loot;
import totoro.ocelot.brain.loot.Loot.LootFactory;
import totoro.ocelot.brain.nbt.NBTTagCompound;
import totoro.ocelot.brain.nbt.persistence.PersistableString;
import totoro.ocelot.brain.util.Tier;
import totoro.ocelot.brain.workspace.Workspace;

/**
 *
 * @author VladTheMountain
 */
public class OcelotEmulatorFrame extends JFrame {

    Canvas mainPanel;
    //
    private Workspace temporaryWorkspace;

    private static final long serialVersionUID = 1L;

    public OcelotEmulatorFrame() {
        initComponents();
        this.pack();
        Timer t = new Timer(300, (ActionEvent e) -> {
            this.repaint();
        });
        t.start();
        /*try {
            initWorkspace();
        } catch (InterruptedException ex) {
            Logger.getLogger(OcelotEmulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    private void initComponents() {
        this.setTitle("Emulator");
        mainPanel = new CustomCanvas();
        //
        this.getContentPane().add(mainPanel);
    }

    private void initWorkspace() throws InterruptedException {
        Ocelot.initialize();
        //Workspace initialization
        File tempWork = new File("workspace/current");
        if (!(tempWork.exists())) {
            tempWork.mkdirs();
        }
        this.temporaryWorkspace = new Workspace(tempWork.toPath());
        //Computer creation
        Case tPC = new Case(Tier.Two());
        this.temporaryWorkspace.add(tPC);
        //CPU
        CPU tCPU = new CPU(Tier.Three());
        tPC.add(tCPU);
        //Memory
        Memory tM1 = new Memory(Tier.Four());
        tPC.add(tM1);
        //GPU
        GraphicsCard tGPU = new GraphicsCard(Tier.Three());
        tPC.add(tGPU);
        //HDD
        HDDManaged tHDD = new HDDManaged(Tier.Three());
        tPC.add(tHDD);
        //EEPROM
        //What kind of Cyberpunk is this?
        LootFactory tMOSEFI = Loot.OpenOsEEPROM(); //O_O
        tPC.add(tMOSEFI.create());
        //Floppy
        LootFactory tOOSF = Loot.OpenOsFloppy();
        tPC.add(tOOSF.create());
        //Screen
        Screen tScreen = new Screen(Tier.Two());
        tPC.connect(tScreen);
        //Custom Data (for some reason)
        tPC.setCustomData(new PersistableString("hi?"));

        //EventListeners
        // Они ещё мало изучены
        // Потому что от них получали больше ошибок чем информации
        /*
        totoro.ocelot.brain.event.EventBus.listenTo(totoro.ocelot.brain.event.BeepEvent.class, new EventListener(){});
        EventBus.listenTo(BeepEvent.class, new BeepEventFunction(){
            @Override
            public void onEventActivated() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });*/
        
        //Turnin' on
        tPC.turnOn();

        while (this.temporaryWorkspace.getIngameTime() < 20) {
            this.temporaryWorkspace.update();
            Thread.sleep(50);
        }

        saveWorkspace(this.temporaryWorkspace);

        while (this.temporaryWorkspace.getIngameTime()
                < 100000) {
            for (int i = 1; i <= tScreen.getHeight(); i++) {
                for (int j = 1; j <= tScreen.getWidth(); j++) {
                    //
                }
            }
            this.temporaryWorkspace.update();
            Thread.sleep(50);
        }

        tPC.turnOff();

        Ocelot.shutdown();
    }

    private void saveWorkspace(Workspace w) {
        //Save the 'w'
        NBTTagCompound nbt = new NBTTagCompound();
        w.save(nbt);
        File tempMachineState = new File("temp/machineState.log");
        if (!(tempMachineState.exists())) {
            try {
                new File(tempMachineState.getParent()).mkdirs();
                tempMachineState.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(OcelotEmulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Files.write(tempMachineState.toPath(), String.valueOf(nbt).getBytes());
        } catch (IOException ex) {
            Logger.getLogger(OcelotEmulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
