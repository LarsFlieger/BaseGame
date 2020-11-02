package com.michael.dopeshot.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.michael.dopeshot.BaseGame.UNIT_SCALE;

public class Map {
    public static final String TAG = Map.class.getSimpleName();

    private final TiledMap tiledMap;
    private final Array<CollisionArea> collisionAreas;
    private final Vector2 playerSpawnLocation;

    public Map(final TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        this.collisionAreas = new Array<CollisionArea>();
        this.playerSpawnLocation = new Vector2();

        parseCollisionLayer();
        parsePlayerSpawnLocation();
    }

    //TODO: This is horrible coded.. :/ Have to find a better solution for this.
    private void parsePlayerSpawnLocation() {
        MapObjects playerSpawnLocationLayer = tiledMap.getLayers().get("playerSpawnLocation").getObjects();
        if(playerSpawnLocationLayer == null) {
            Gdx.app.debug(TAG, "The map doesn't have a 'playerSpawnLocation' layer. Create a layer called 'playerSpawnLocation' with a point object. This object will be used as a spawn point for the player.");
            return;
        }
        final RectangleMapObject playerSpawnLocationFromLayer =  (RectangleMapObject) playerSpawnLocationLayer.get(0);
        final Rectangle playerSpawnLocation = playerSpawnLocationFromLayer.getRectangle();
        this.playerSpawnLocation.set(playerSpawnLocation.x * UNIT_SCALE, playerSpawnLocation.y * UNIT_SCALE);

    }

    private void parseCollisionLayer() {
        final MapLayer collisionLayer = tiledMap.getLayers().get("collision");

        if (collisionLayer == null) {
            Gdx.app.debug(TAG, "There is no collision layer on your map. Ensure to name the layer in Tiled 'collision'.");
            return;
        }

        final MapObjects mapObjects = collisionLayer.getObjects();
        if (mapObjects == null) {
            Gdx.app.debug(TAG, "mapObjects is null. You don't have any collision on your map. Are you sure to continue?.");
            return;
        }
/*
        final Array<RectangleMapObject> rectangleMapObjects = collisionLayer.getObjects().getByType(RectangleMapObject.class);
        for(RectangleMapObject mapObject : rectangleMapObjects) {
*/
        for (final MapObject mapObj : mapObjects) {
            if (mapObj instanceof RectangleMapObject) {
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObj;
                final Rectangle rectangle = rectangleMapObject.getRectangle();
                final float[] rectVertices = new float[10];

                //left-bottom
                rectVertices[0] = 0;
                rectVertices[1] = 0;

                //left-top
                rectVertices[2] = 0;
                rectVertices[3] = rectangle.height;

                //right-top
                rectVertices[4] = rectangle.width;
                rectVertices[5] = rectangle.height;

                //right-bottom
                rectVertices[6] = rectangle.width;
                rectVertices[7] = 0;

                //left-bottom
                rectVertices[8] = 0;
                rectVertices[9] = 0;

                collisionAreas.add(new CollisionArea(rectangle.x, rectangle.y, rectVertices));

            } else if (mapObj instanceof PolylineMapObject) {
                final PolylineMapObject polylineMapObject = (PolylineMapObject) mapObj;
                final Polyline polyline = polylineMapObject.getPolyline();
                collisionAreas.add(new CollisionArea(polyline.getX(), polyline.getY(), polyline.getVertices()));
            } else {
                Gdx.app.debug(TAG, "MapObject of type " + mapObj + "is not supported in collision layer!");
            }
        }
    }

    public Array<CollisionArea> getCollisionAreas() {
        return collisionAreas;
    }

    public Vector2 getPlayerSpawnLocation() {
        return playerSpawnLocation;
    }
}
