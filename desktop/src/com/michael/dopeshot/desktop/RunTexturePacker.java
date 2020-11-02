package com.michael.dopeshot.desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class RunTexturePacker {

    public static void main(String[] arg) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.edgePadding = true;
        settings.paddingY = 2;
        settings.paddingX = 2;
        //settings.maxWidth = 2048;
        //settings.maxHeight = 2048;
        settings.filterMin = Texture.TextureFilter.Nearest;
        settings.filterMag = Texture.TextureFilter.Nearest;
        settings.bleed = false;
        settings.duplicatePadding = false;
        TexturePacker.process(settings, "core\\assets\\map\\basic\\", "core\\assets\\packed\\map", "atlas");
    }
}
