package com.michael.dopeshot.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.michael.dopeshot.BaseGame;
import com.michael.dopeshot.map.CollisionArea;
import com.michael.dopeshot.map.Map;

import static com.michael.dopeshot.BaseGame.*;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    private final Body player;

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera gameCamera;
    private AssetManager assetManager; // never make this static!!

    private final Map map;

    private final GLProfiler profiler;

    public GameScreen(final BaseGame context) {
        super(context);

        gameCamera = context.getGameCamera();
        assetManager = context.getAssetManager();
        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, context.getSpriteBatch());

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        // create player
        bodyDef.position.set(8, 8);
        bodyDef.gravityScale = 1;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bodyDef);
        player.setUserData("PLAYER");

        fixtureDef.density = 1;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = polygonShape;
        player.createFixture(fixtureDef);
        polygonShape.dispose();
/*
        // create room
        bodyDef.position.set(0, 0);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        final Body body = world.createBody(bodyDef);
        body.setUserData("GROUND");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        final ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new float[]{1,1,15,1,15,8,1,8});
        fixtureDef.shape = chainShape;
        body.createFixture(fixtureDef);
        chainShape.dispose();
 */

        //spawn collision areas

        final TiledMap tiledMap = assetManager.get("map/basic/map.tmx", TiledMap.class);
        mapRenderer.setMap(assetManager.get("map/basic/map.tmx", TiledMap.class));
        map = new Map(tiledMap);

        spawnCollisionAreas();
    }

    private void spawnCollisionAreas() {
        for(final CollisionArea collisionArea : map.getCollisionAreas()) {
            resetBodieAndFixtureDefinition();
            bodyDef.position.set(collisionArea.getX(), collisionArea.getY());
            bodyDef.fixedRotation = true;
            final Body body = world.createBody(bodyDef);
            body.setUserData("GROUND");

            fixtureDef.filter.categoryBits = BIT_GROUND;
            fixtureDef.filter.maskBits = -1;
            final ChainShape chainShape = new ChainShape();
            chainShape.createChain(collisionArea.getVertices());
            fixtureDef.shape = chainShape;
            body.createFixture(fixtureDef);
            chainShape.dispose();
        }
    }

    private void resetBodieAndFixtureDefinition() {
        bodyDef.position.set(0,0);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = false;

        fixtureDef.density = 0;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = 0x0001;
        fixtureDef.filter.maskBits = -1;
        fixtureDef.shape = null;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.L)) {
            context.setScreen(ScreenType.LOADING);
        }

        final float speedX, speedY, speed;
        speed = 5;
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            speedX = -speed;
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            speedX = speed;
        } else {
            speedX = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            speedY = speed;
        } else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            speedY = -speed;
        } else {
            speedY = 0;
        }

        player.applyLinearImpulse(
                (speedX - player.getLinearVelocity().x) * player.getMass(),
                (speedY - player.getLinearVelocity().y) * player.getMass(),
                player.getWorldCenter().x, player.getWorldCenter().y, true
        );



        viewport.apply(true);

        gameCamera.position.set(player.getPosition().x, player.getPosition().y, 0);
        gameCamera.update();

        mapRenderer.setView(gameCamera);
        mapRenderer.render();
        box2DDebugRenderer.render(world, viewport.getCamera().combined);

        Gdx.app.debug("RenderInfo", "Bindings: " + profiler.getTextureBindings() + " | Drawcalls: " + profiler.getDrawCalls() + " | FPS: " + Gdx.graphics.getFramesPerSecond());
        profiler.reset();
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
        mapRenderer.dispose();
    }
}
