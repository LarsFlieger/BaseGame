package com.michael.dopeshot.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.michael.dopeshot.BaseGame;

public class DesktopLauncher {

    public static void main(String[] arg) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "BaseGame Dopeshot";
        config.width = 1920;
        config.height = 1080;
        config.resizable = true;
        config.fullscreen = false;
        config.samples = 0; //anti aliasing
        config.vSyncEnabled = false;
        config.foregroundFPS = 60;
        config.backgroundFPS = 30;
        config.pauseWhenBackground = true; //Require this to pause and resume on focus


        new LwjglApplication(new BaseGame(), config);

    }
}
