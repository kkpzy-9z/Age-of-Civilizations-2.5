/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz.desktop;

import age.of.civilizations2.jakowski.lukasz.AoCGame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Age of Civilizations II";
        config.addIcon("ic_32x32.png", Files.FileType.Internal);
        config.resizable = false;
        int tWidth = -1;
        int tHeight = -1;
        boolean tFullscreen = true;
        int tSamples = -1;
        boolean tVSync = false;
        FileReader fr = null;
        String sLine = "";
        System.out.println(System.getProperty("file.encoding"));
        try {
            fr = new FileReader("config.ini");
            BufferedReader bfr = new BufferedReader(fr);
            while ((sLine = bfr.readLine()) != null) {
                String[] tempR = sLine.replace(";", "").split("=");
                try {
                    if (tempR[0].equals("FULLSCREEN")) {
                        tFullscreen = Boolean.parseBoolean(tempR[1]);
                        continue;
                    }
                    if (tempR[0].equals("WIDTH")) {
                        tWidth = Integer.parseInt(tempR[1]);
                        continue;
                    }
                    if (tempR[0].equals("HEIGHT")) {
                        tHeight = Integer.parseInt(tempR[1]);
                        continue;
                    }
                    if (tempR[0].equals("ANTIALIASING")) {
                        tSamples = Integer.parseInt(tempR[1]);
                        continue;
                    }
                    if (!tempR[0].equals("VSYNC")) continue;
                    tVSync = Boolean.parseBoolean(tempR[1]);
                }
                catch (IndexOutOfBoundsException ex) {
                    tWidth = -1;
                    tHeight = -1;
                    tFullscreen = true;
                    tSamples = -1;
                    tVSync = false;
                    break;
                }
                catch (IllegalArgumentException ex) {
                    tWidth = -1;
                    tHeight = -1;
                    tFullscreen = true;
                    tSamples = -1;
                    tVSync = false;
                    break;
                }
            }
            fr.close();
        }
        catch (IOException ex) {
            tWidth = -1;
            tHeight = -1;
            tFullscreen = true;
            tSamples = -1;
            tVSync = false;
        }
        if (tSamples != -1) {
            config.samples = tSamples;
        }
        config.vSyncEnabled = tVSync;
        if (tWidth <= 0 && tHeight <= 0) {
            config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
            config.fullscreen = tFullscreen;
        } else {
            config.width = tWidth;
            config.height = tHeight;
            config.fullscreen = tFullscreen;
        }
        new LwjglApplication((ApplicationListener)new AoCGame(new DesktopLinkHandler()), config);
    }
}

