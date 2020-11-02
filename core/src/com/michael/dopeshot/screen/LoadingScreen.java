package com.michael.dopeshot.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.michael.dopeshot.BaseGame;

public class LoadingScreen extends AbstractScreen{
    private final AssetManager assetManager;

    public LoadingScreen(final BaseGame context) {
        super(context);

        this.assetManager = context.getAssetManager();
        assetManager.load("map/basic/map.tmx", TiledMap.class);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(assetManager.update()) {
            context.setScreen(ScreenType.GAME);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
