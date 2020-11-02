package com.michael.dopeshot.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

public class Map {
    public static final String TAG = Map.class.getSimpleName();

    private final TiledMap tiledMap;

    public Map(final TiledMap tiledMap) {
        this.tiledMap = tiledMap;

        parseCollisionLayer();
    }

    private void parseCollisionLayer() {
        final MapLayer collisionLayer = tiledMap.getLayers().get("collision");

        if (collisionLayer == null) {
            Gdx.app.debug(TAG, "There is no collision layer on your map. Ensure to name the layer in Tiled 'collision'.");
            return;
        }

        final MapObjects mapObjects = collisionLayer.getObjects();
        if (mapObjects == null) {
            Gdx.app.debug(TAG, "mapObjects is null. You don't have any collection on your map. Are you sure to continue?.");
            return;
        }
/*
        final Array<RectangleMapObject> rectangleMapObjects = collisionLayer.getObjects().getByType(RectangleMapObject.class);
        for(RectangleMapObject mapObject : rectangleMapObjects) {
*/
        for(final MapObject mapObj : mapObjects) {
            if(mapObj instanceof RectangleMapObject) {
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObj;

            } else if(mapObj instanceof PolylineMapObject) {
                final PolylineMapObject polylineMapObject = (PolylineMapObject) mapObj;

            } else {
                Gdx.app.debug(TAG, "MapObject of type " + mapObj + "is not supported in collision layer!");
            }
        }

    }


}
