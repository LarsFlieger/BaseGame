package com.michael.dopeshot.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.*;
import com.michael.dopeshot.BaseGame;

import static com.michael.dopeshot.BaseGame.*;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    public GameScreen(final BaseGame context) {
        super(context);

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        // create circle
        bodyDef.position.set(8, 8);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_CIRCLE;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_BOX;
        CircleShape cShape = new CircleShape();
        cShape.setRadius(0.5f);
        fixtureDef.shape = cShape;
        body.createFixture(fixtureDef);
        cShape.dispose();


        // create box
        bodyDef.position.set(7.43f, 4);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_BOX;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_CIRCLE;
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        pShape.dispose();

        // create platform
        bodyDef.position.set(8, 2);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        pShape = new PolygonShape();
        pShape.setAsBox(5f, 0.5f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        pShape.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            context.setScreen(ScreenType.LOADING);
        }
        viewport.apply(true);
        box2DDebugRenderer.render(world, viewport.getCamera().combined);
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
