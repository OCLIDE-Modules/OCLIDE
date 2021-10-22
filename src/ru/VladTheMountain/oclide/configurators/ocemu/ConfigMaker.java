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
package ru.VladTheMountain.oclide.configurators.ocemu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ru.VladTheMountain.oclide.configurators.ocemu.component.OCEmuComponent;

/**
 *
 * @author VladTheMountain
 */
public class ConfigMaker {

    //CONFIG VARIABLES
    private static String monochromeColor = "0xFFFFFF";
    private static boolean allowBytecode = false;
    private static boolean allowGC = false;
    private static int timeout = 5;
    private static boolean debug = true;
    private static boolean vague = true;
    private static boolean enableHttp = true;
    private static boolean enableTcp = true;
    private static int maxNetworkPacketSize = 8192;
    private static int maxWirelessRange = 400;
    //
    private String computerComponents;

    /**
     * Initialization constructor
     *
     * @param comps array of OCEmuComponent
     * @see ru.VladTheMountain.oclide.configurators.ocemu.component.OCEmuComponent
     */
    public ConfigMaker(OCEmuComponent[] comps) {
        if (comps == null || comps.length < 2) {
            Logger.getLogger(ConfigMaker.class.getName()).log(Level.WARNING, "ConfigMaker received an incomplete configutation", comps);
            JOptionPane.showMessageDialog(null, "Couldn't create ConfigMaker for OCEmu: Incomplete configuration:\n" + Arrays.toString(comps), "Caught " + "", JOptionPane.WARNING_MESSAGE);
        }
        this.computerComponents = "";
        for (OCEmuComponent comp : comps) {
            switch (comp.getComponentType()) {
                case 0:
                    this.computerComponents = this.computerComponents + "      "
                            + "{\"computer\", "
                            + "\"" + comp.getComponentAddress() + "\"},\n";
                    break;
                case 1:
                    this.computerComponents = this.computerComponents + "      "
                            + "{\"eeprom\", "
                            + "\"" + comp.getComponentAddress() + "\", " + "" + comp.getOptionAt(0) + ", " + "\"" + comp.getOptionAt(1) + "\"},\n";
                    break;
                case 2:
                    this.computerComponents = this.computerComponents + "      "
                            + "{\"filesystem\", "
                            + "\"" + comp.getComponentAddress() + "\", " + "" + comp.getOptionAt(0) + ", " + "\"" + comp.getOptionAt(1) + "\", " + "" + comp.getOptionAt(2) + "},\n";
                    break;
                case 3:
                    this.computerComponents = this.computerComponents + "      "
                            + "{\"gpu\", "
                            + "\"" + comp.getComponentAddress() + "\", " + "" + comp.getOptionAt(0) + ", " + "" + comp.getOptionAt(1) + ", " + "" + comp.getOptionAt(2) + ", " + "" + comp.getOptionAt(3) + "},\n";
                    break;
                case 4:
                    this.computerComponents = this.computerComponents + "      "
                            + "{\"internet\", \"" + comp.getComponentAddress() + "\"},\n";
                    break;
                case 5:
                    this.computerComponents = this.computerComponents + "      "
                            + "{\"keyboard_sdl2\", "
                            + "\"" + comp.getComponentAddress() + "\"},\n";
                    break;
                case 6:
                    this.computerComponents = this.computerComponents + "      "
                            + "{\"modem\", "
                            + "\"" + comp.getComponentAddress() + "\", " + "" + comp.getOptionAt(0) + ", " + "" + comp.getOptionAt(1) + "},\n";
                    break;
                case 7:
                    this.computerComponents = this.computerComponents + "      "
                            + "{\"ocemu\", "
                            + "\"" + comp.getComponentAddress() + "\"},\n";
                    break;
                case 8:
                    this.computerComponents = this.computerComponents + "      "
                            + "{\"screen_sdl2\", "
                            + "\"" + comp.getComponentAddress() + "\", " + "" + comp.getOptionAt(0) + ", " + "" + comp.getOptionAt(1) + ", " + "" + comp.getOptionAt(2) + ", " + "" + comp.getOptionAt(3) + "},\n";
                    break;
                default:
                    Logger.getLogger(ConfigMaker.class.getName()).log(Level.SEVERE, "ConfigMaker received an invalid component type: {0}", comp.getComponentType());
                    break;
            }
            Logger.getLogger(this.getClass().getName()).log(Level.CONFIG, "Added component to OCEmu config:", comp.toString());
        }
    }

    /**
     * Creates the new config file
     */
    public void createConfig() {
        String path;
        if (System.getProperty("os.name").contains("Windows")) {
            path = System.getenv("APPDATA") + "\\OCEmu\\ocemu.cfg";
        } else {
            if (new File(System.getenv("XDG_CONFIG_HOME") + "/ocemu").exists()) {
                path = System.getenv("XDG_CONFIG_HOME") + "/ocemu/ocemu.cfg";
            } else {
                path = System.getenv("HOME") + "/.config/ocemu/ocemu.cfg";
            }
        }
        if (path == null) {
            JOptionPane.showMessageDialog(null, "WARNING: Couldn't create path for config file for your OS. Please create an issue about this problem at https://github.com/Vladg24YT/Oclide/issues", "Config creation error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        File config = new File(path);
        if (!(config.exists())) {
            if (!(new File(config.getParent()).exists())) {
                new File(config.getParent()).mkdirs();
            }
            try {
                config.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ConfigMaker.class.getName()).log(Level.SEVERE, "Couldn't create config file:", ex);
                JOptionPane.showMessageDialog(null, ex, "Caught " + ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);
            }
        }
        //
        String contents
                = "--OCEmu configuration. Designed to mimic HOCON syntax, but is not exactly HOCON\n"
                + "--syntax.\n"
                + "ocemu {\n"
                + "\n"
                + "  --Client side settings, presentation and performance related stuff.\n"
                + "  client {\n"
                + "\n"
                + "    --The color of monochrome text (i.e. displayed when in 1-bit color depth,\n"
                + "    --e.g. tier one screens / GPUs, or higher tier set to 1-bit color depth).\n"
                + "    --Defaults to white, feel free to make it some other color, tho!\n"
                + "    monochromeColor=\"" + monochromeColor + "\"\n"
                + "  }\n"
                + "\n"
                + "  --Computer related settings, concerns server performance and security.\n"
                + "  computer {\n"
                + "\n"
                + "    --Settings specific to the Lua architecture.\n"
                + "    lua {\n"
                + "\n"
                + "      --Whether to allow loading precompiled bytecode via Lua's `load` function,\n"
                + "      --or related functions (`loadfile`, `dofile`). Enable this only if you\n"
                + "      --absolutely trust all users on your server and all Lua code you run. This\n"
                + "      --can be a MASSIVE SECURITY RISK, since precompiled code can easily be\n"
                + "      --used for exploits, running arbitrary code on the real server! I cannot\n"
                + "      --stress this enough: only enable this is you know what you're doing.\n"
                + "      allowBytecode=" + allowBytecode + "\n"
                + "\n"
                + "      --Whether to allow user defined __gc callbacks, i.e. __gc callbacks\n"
                + "      --defined *inside* the sandbox. Since garbage collection callbacks are not\n"
                + "      --sandboxed (hooks are disabled while they run), this is not recommended.\n"
                + "      allowGC=" + allowGC + "\n"
                + "    }\n"
                + "\n"
                + "    --The time in seconds a program may run without yielding before it is\n"
                + "    --forcibly aborted. This is used to avoid stupidly written or malicious\n"
                + "    --programs blocking other computers by locking down the executor threads.\n"
                + "    --Note that changing this won't have any effect on computers that are\n"
                + "    --already running - they'll have to be rebooted for this to take effect.\n"
                + "    timeout=" + timeout + "\n"
                + "  }\n"
                + "\n"
                + "  --Emulator related settings. Components, accuracy, and debugging.\n"
                + "  emulator {\n"
                + "\n"
                + "    --Default components available to the computer.\n"
                + "    components {\n"
                + "\n"
                /*computerComponents*/
                + "{\"gpu\", \"ea252465-71b5-4a10-b073-a4fa86d5a0de\", 0, 160, 50, 3},\n"
                + "      {\"modem\", \"8547cc86-0377-40f0-a3b9-fcee29003213\", 1, false},\n"
                + "      {\"eeprom\", \"b66d9734-880f-453e-bfce-3bbb230afaae\", 9, \"lua/bios.lua\"},\n"
                + "      {\"filesystem\", \"2bf82535-b34e-469c-ac31-a92d10040b9a\", 7, \"loot/openos\", \"openos\", true, 1},\n"
                + "      {\"filesystem\", \"a20fd029-b38f-4689-ad35-177b5b9caa33\", -1, \"tmpfs\", \"tmpfs\", false, 5},\n"
                + "      {\"filesystem\", \"93b849a3-d3d6-4a84-851e-7e5fd936ea63\", 5, \".machine\", \"OpenOS\", false, 4},\n"
                + "      {\"internet\", \"c8fe394e-1cf3-4e2b-9f44-a13c6c1552c3\", 2},\n"
                + "      {\"computer\", \"fd0de30a-f3a2-4559-b830-ab39a37376ba\", -1},\n"
                + "      {\"ocemu\", \"8855c210-b7a5-4e49-88bf-cd6659a79e9d\", -1},\n"
                + "      {\"screen_sdl2\", \"8d56e247-3db3-46ef-af93-13ccf12c40e5\", -1, 80, 25, 3},\n"
                + "      {\"keyboard_sdl2\", \"6eea2524-6571-4bad-84c1-73a4fa86d5a0\", -1}"
                + "    }\n"
                + "\n"
                + "    --Whether to enable the emulator's extremely verbose logging.\n"
                + "    debug=" + debug + "\n"
                + "\n"
                + "    --Whether to return vague error messages like OpenComputers.\n"
                + "    vague=" + vague + "\n"
                + "  }\n"
                + "\n"
                + "  internet {\n"
                + "\n"
                + "    --Whether to allow HTTP requests via internet cards. When enabled, the\n"
                + "    --`request` method on internet card components becomes available.\n"
                + "    enableHttp=" + enableHttp + "\n"
                + "\n"
                + "    --Whether to allow TCP connections via internet cards. When enabled, the\n"
                + "    --`connect` method on internet card components becomes available.\n"
                + "    enableTcp=" + enableTcp + "\n"
                + "  }\n"
                + "\n"
                + "  --Other settings that you might find useful to tweak.\n"
                + "  misc {\n"
                + "\n"
                + "    --The maximum size of network packets to allow sending via network cards.\n"
                + "    --This has *nothing to do* with real network traffic, it's just a limit for\n"
                + "    --the network cards, mostly to reduce the chance of computer with a lot of\n"
                + "    --RAM killing those with less by sending huge packets. This does not apply\n"
                + "    --to HTTP traffic.\n"
                + "    maxNetworkPacketSize=" + maxNetworkPacketSize + "\n"
                + "\n"
                + "    --The maximum distance a wireless message can be sent. In other words, this\n"
                + "    --is the maximum signal strength a wireless network card supports. This is\n"
                + "    --used to limit the search range in which to check for modems, which may or\n"
                + "    --may not lead to performance issues for ridiculous ranges - like, you know,\n"
                + "    --more than the loaded area. See also: `wirelessCostPerRange`.\n"
                + "    maxWirelessRange=" + maxWirelessRange + "\n"
                + "  }\n"
                + "}";
        //
        try {
            Files.write(config.toPath(), contents.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(ConfigMaker.class.getName()).log(Level.SEVERE, "Couldn't write to config file", ex);
            JOptionPane.showMessageDialog(null, ex, "Caught " + ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Parses the components' part of the OCEmu emulator config.
     *
     * @param f Config gile
     * @return Array of {@link OCEmuComponent}
     */
    public OCEmuComponent[] readConfig(File f) {
        int lines = 0;
        try {
            lines = Files.readAllLines(f.toPath()).toArray().length;
        } catch (IOException ex) {
            Logger.getLogger(ConfigMaker.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex, "Caught " + ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);
        }
        String[] configContents = new String[lines];
        try {
            System.arraycopy(Files.readAllLines(f.toPath()).toArray(configContents), 0, configContents, 0, lines);
        } catch (IOException ex) {
            Logger.getLogger(ConfigMaker.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex, "Caught " + ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);
        }
        OCEmuComponent[] tmp = {};
        for (int i = 47; i < configContents.length; i++) {
            if (configContents[i].startsWith("{") && (configContents[i].endsWith("}") || configContents[i].endsWith("},"))) {
                byte type = 7;
                String address;
                String[] opts = new String[4];
                int comma1 = configContents[i].indexOf(",");
                int comma2 = configContents[i].indexOf(",", comma1);
                int comma3 = configContents[i].indexOf(",", comma2);
                int comma4 = configContents[i].indexOf(",", comma3);
                int comma5 = configContents[i].indexOf(",", comma4);
                switch (configContents[i].substring(configContents[i].indexOf("\""), configContents[i].indexOf("\"", configContents[i].indexOf("\"")))) {
                    case "computer":
                        type = 0;
                        break;
                    case "eeprom":
                        type = 1;
                        break;
                    case "filesystem":
                        type = 2;
                        break;
                    case "gpu":
                        type = 3;
                        break;
                    case "internet":
                        type = 4;
                        break;
                    case "keyboard":
                        type = 5;
                        break;
                    case "modem":
                        type = 6;
                        break;
                    case "ocemu":
                        type = 7;
                        break;
                    case "screen":
                        type = 8;
                        break;
                    default:
                        Logger.getLogger(ConfigMaker.class.getName()).log(Level.WARNING, "ConfigMaker detected an invalid component type: {0}", configContents[i].substring(configContents[i].indexOf("\""), configContents[i].indexOf("\"", configContents[i].indexOf("\""))));
                        break;
                }
                address = configContents[i].substring(comma1 + 2, comma2 - 1);
                opts[0] = configContents[i].substring(comma2 + 2, comma3 - 1);
                opts[1] = configContents[i].substring(comma3 + 2, comma4 - 1);
                opts[2] = configContents[i].substring(comma4 + 2, comma5 - 1);
                opts[3] = configContents[i].substring(comma5 + 2, configContents[i].indexOf("}") - 1);
                tmp[i] = new OCEmuComponent(type, address, opts);
            }
        }
        return tmp;
    }
}
