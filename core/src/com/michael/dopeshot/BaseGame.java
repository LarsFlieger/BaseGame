package com.michael.dopeshot;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.michael.dopeshot.screen.LoadingScreen;
import com.michael.dopeshot.screen.ScreenType;

import java.util.EnumMap;

public class BaseGame extends Game {
	private static final String TAG = BaseGame.class.getSimpleName();

	private EnumMap<ScreenType, Screen> screenCache;
	private FitViewport screenViewport;

	public static final short BIT_CIRCLE = 1<<0;
	public static final short BIT_BOX = 1<<1;
	public static final short BIT_GROUND = 1<<2;

	private World world;
	private Box2DDebugRenderer box2DDebugRenderer;

	private static final float FIXED_TIME_STEP = 1 / 60f;
	private float accumulator;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		accumulator = 0;

		Box2D.init();
		world = new World(new Vector2(0, -9.81f), true);
		box2DDebugRenderer = new Box2DDebugRenderer();

		screenViewport = new FitViewport(16,9);
		screenCache = new EnumMap<ScreenType, Screen>(ScreenType.class);
		setScreen(ScreenType.GAME);
	}

	public void setScreen(final ScreenType screenType) {
		final Screen screen = screenCache.get(screenType);

		if(screen == null) {
			try {
				Gdx.app.debug(TAG, "Creating new screen: " + screenType);
				final Screen newScreen = (Screen) ClassReflection.getConstructor(screenType.getScreenClass(), BaseGame.class).newInstance(this);
				screenCache.put(screenType, newScreen);
				setScreen(newScreen);
			} catch (ReflectionException e) {
				throw new GdxRuntimeException("Screen " + screenType + " could not be created!", e);
			}

		} else {
			Gdx.app.debug(TAG, "Switching to screen: " + screenType);
			setScreen(screen);
		}

	}

	@Override
	public void render() {
		super.render();
		Gdx.app.log("Info", "FPS: " + Gdx.graphics.getFramesPerSecond() + " Delta:" + Gdx.graphics.getRawDeltaTime());

		accumulator += Math.min(0.25f, Gdx.graphics.getRawDeltaTime());
		while(accumulator >= FIXED_TIME_STEP) {
			world.step(FIXED_TIME_STEP, 6, 2);
			accumulator -= FIXED_TIME_STEP;
		}

		//final float alpha = accumulator / FIXED_TIME_STEP;
	}

	@Override
	public void dispose() {
		super.dispose();
		box2DDebugRenderer.dispose();
		world.dispose();
	}

	public World getWorld() {
		return world;
	}

	public FitViewport getScreenViewport() {
		return screenViewport;
	}

	public Box2DDebugRenderer getBox2DDebugRenderer() {
		return box2DDebugRenderer;
	}
}
