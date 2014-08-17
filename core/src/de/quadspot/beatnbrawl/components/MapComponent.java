package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by goetsch on 12.08.14.
 */
public class MapComponent extends Component{
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    MapObjects objects;
    Polygon groundBody1;
    Rectangle groundBody2;
    Rectangle tmp;
    float mapWidth;
    float mapHeight;
    float mapFactor;

    public MapComponent(Batch batch) {
        tiledMap = new TmxMapLoader().load("maps/tmnt.tmx");
        mapWidth = tiledMap.getProperties().get("width", Integer.class).floatValue()*tiledMap.getProperties().get("tilewidth", Integer.class).floatValue();
        mapHeight = tiledMap.getProperties().get("height", Integer.class).floatValue()*tiledMap.getProperties().get("tileheight", Integer.class).floatValue();
        mapFactor = Gdx.graphics.getHeight()/mapHeight;
        
        objects = tiledMap.getLayers().get("Grund").getObjects();
        groundBody1 = ((PolygonMapObject) objects.get("ground")).getPolygon();
        tmp = ((RectangleMapObject) objects.get("ground2")).getRectangle();
        groundBody2 = tmp.setSize(tmp.getWidth()*mapFactor, tmp.getHeight()*mapFactor);

        
            
        
        
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                                // do something with rect...
            } else if (object instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) object).getPolygon();
                // do something with polygon...
            } else if (object instanceof PolylineMapObject) {
                Polyline chain = ((PolylineMapObject) object).getPolyline();
                // do something with chain...
            } else if (object instanceof CircleMapObject) {
                Circle circle = ((CircleMapObject) object).getCircle();
                // do something with circle...
            }
        }

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, mapFactor, batch);
        tiledMapRenderer.render();
    }

    public Rectangle getGroundBody() {
        return groundBody2;
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
    
    public boolean isOnGround(int x, int y){
        return groundBody2.contains(x, y);
    }



}
