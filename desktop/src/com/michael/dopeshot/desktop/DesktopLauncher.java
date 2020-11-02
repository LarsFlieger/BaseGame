package com.michael.dopeshot.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.michael.dopeshot.BaseGame;

import java.util.logging.Filter;

public class DesktopLauncher {
	private static final boolean CREATE_ATLAS = false;

	public static void main (String[] arg) {
		if(CREATE_ATLAS) {
			TexturePacker.Settings settings = new TexturePacker.Settings();
			settings.edgePadding = true;
			settings.paddingY = 2;
			settings.paddingX = 2;
			settings.maxWidth = 2048;
			settings.maxHeight = 2048;
			settings.filterMin = Texture.TextureFilter.Nearest;
			settings.bleed = true;
			TexturePacker.process(settings, "core\\assets\\map\\basic\\", "core\\assets\\packed\\map", "atlas");

		} else   {
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

			config.title = "BaseGame Dopeshot";
			config.width = 1920;
			config.height = 1080;
			config.resizable = true;
			config.fullscreen = false;
			config.samples = 2; //anti aliasing
			config.vSyncEnabled = false;
			config.foregroundFPS = 60;
			config.backgroundFPS = 30;
			config.pauseWhenBackground = true; //Require this to pause and resume on focus


			new LwjglApplication(new BaseGame(), config);
		}
	}
}
