package de.quadspot.beatnbrawl;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import de.quadspot.beatnbrawl.components.AIComponent;
import de.quadspot.beatnbrawl.components.ActionComponent;
import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.BossComponent;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.HealthComponent;
import de.quadspot.beatnbrawl.components.InputComponent;
import de.quadspot.beatnbrawl.components.MapComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;
import de.quadspot.beatnbrawl.components.StateComponent;

/**
 * Created by goetsch on 14.10.14.
 */
public class Factory {
    Engine engine;
    TiledMap tiledMap;
    public float mapHeight;
    MapObjects objects;
    beatnbrawl game;
    float mapFactor;
    int level = 1;


    public Factory(beatnbrawl game, Engine engine) {
        this.engine = engine;
        this.game = game;
        loadLevel();
    }

    public void loadLevel(int x) {
        // TODO Levelliste auslesen.
        level =  x;
        switch (x%2) {
            case 0:
            	tiledMap = new TmxMapLoader().load("maps/tmnt.tmx");
                break;
            case 1:
                tiledMap = new TmxMapLoader().load("maps/schiff2.tmx");
                break;
        }
        mapHeight = tiledMap.getProperties().get("height", Integer.class).floatValue()*tiledMap.getProperties().get("tileheight", Integer.class).floatValue();
        mapFactor = Gdx.graphics.getHeight()/mapHeight;
        createMap();

        objects = tiledMap.getLayers().get("Player").getObjects();

        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                createPlayer(rect.getX(), rect.getY(), "don.atlas");
            }
        }

        objects = tiledMap.getLayers().get("Enemies").getObjects();

        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                createEnemy(rect.getX(), rect.getY(), "don.atlas");
            }
        }

        objects = tiledMap.getLayers().get("Boss").getObjects();

        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                createBoss(rect.getX(), rect.getY(), "don.atlas");
            }
        }

        Iterator<MapLayer> iterator = tiledMap.getLayers().iterator();
        while ( iterator.hasNext()) {
            MapLayer mapLayer = iterator.next();
            //System.out.println(mapLayer.getName());
            if (!(mapLayer instanceof TiledMapTileLayer)){
                try {
                    System.out.println("x = [" + mapLayer.getProperties().get("model") + "]");
                } catch (Exception e) {
                    System.out.println("NPE!!!!");
                }
            }
        }

        level++;
    }

    public void loadLevel() {
        loadLevel(level);
    }

    private void createPlayer(float x, float y, String model) {
        Entity entity = new Entity();
        entity.add(new RenderComponent());
        entity.add(new PositionComponent(new Vector3(x*mapFactor,0,y*mapFactor)));
        entity.add(new MovementComponent(new Vector3(0,0,0),new Vector3(500,0,200)));
        entity.add(new InputComponent());
        entity.add(new AnimationComponent(model));
        entity.add(new CollisionComponent());
        entity.add(new ActionComponent());
        entity.add(new StateComponent());
        entity.add(new HealthComponent(100));

        engine.addEntity(entity);
    }

    private void createEnemy(float x, float y, String model) {
        Entity entity2 = new Entity();
        entity2.add(new PositionComponent(new Vector3(x*mapFactor,0,y*mapFactor)));
        entity2.add(new RenderComponent());
        entity2.add(new MovementComponent(new Vector3(0,0,0),new Vector3(400,0,100)));
        entity2.add(new AnimationComponent(model));
        entity2.add(new CollisionComponent());
        entity2.add(new ActionComponent());
        entity2.add(new StateComponent());
        entity2.add(new HealthComponent(20));

        entity2.add(new AIComponent());
        engine.addEntity(entity2);
    }

    private void createBoss(float x, float y, String model) {
        Entity entity2 = new Entity();
        entity2.add(new PositionComponent(new Vector3(x*mapFactor,0,y*mapFactor)));
        entity2.add(new RenderComponent());
        entity2.add(new MovementComponent(new Vector3(0,0,0),new Vector3(400,0,100)));
        entity2.add(new AnimationComponent(model));
        entity2.add(new CollisionComponent());
        entity2.add(new ActionComponent());
        entity2.add(new StateComponent());
        entity2.add(new HealthComponent(60));
        entity2.add(new BossComponent());

        entity2.add(new AIComponent());
        engine.addEntity(entity2);
    }

    private void createMap() {
        Entity map = new Entity();
        map.add(new MapComponent(game.batch, tiledMap));
        engine.addEntity(map);
    }
}
