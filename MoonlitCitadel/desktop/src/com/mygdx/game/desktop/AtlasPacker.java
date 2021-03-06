package com.mygdx.game.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AtlasPacker {
    public static final String INPUT_PATH = "android/assets/sprites/Input";

    public static final String OUTPUT_PATH = "android/assets/sprites/Output";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxHeight = 2048;
        settings.maxWidth = 2048;
        TexturePacker.process(settings, INPUT_PATH, OUTPUT_PATH, "MoonlitCitadelAtlas");
    }
}
