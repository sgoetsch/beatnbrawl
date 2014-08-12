package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by goetsch on 12.08.14.
 */
public class MapComponent extends Component{
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    float mapWidth;
    float mapHeight;
    float mapFactor;

    public MapComponent(Batch batch) {
        tiledMap = new TmxMapLoader().load("maps/tmnt.tmx");
        mapWidth = tiledMap.getProperties().get("width", Integer.class).floatValue()*tiledMap.getProperties().get("tilewidth", Integer.class).floatValue();
        mapHeight = tiledMap.getProperties().get("height", Integer.class).floatValue()*tiledMap.getProperties().get("tileheight", Integer.class).floatValue();

        mapFactor = Gdx.graphics.getHeight()/mapHeight;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, mapFactor, batch);
        tiledMapRenderer.render();
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public float getMapWidth() {
        return mapWidth*mapFactor;
    }

    public float getMapHeight() {
        return mapHeight*mapFactor;
    }



}
