package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Bits;

import de.quadspot.beatnbrawl.components.MapComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;

/**
 * Created by goetsch on 05.08.14.
 */
public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private Entity mapEntity;


    private OrthographicCamera camera;
    private SpriteBatch batch;

    public RenderSystem(OrthographicCamera camera, SpriteBatch batch){
        this.camera = camera;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(PositionComponent.class, RenderComponent.class), new Bits(), new Bits()));
        mapEntity = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(MapComponent.class), new Bits(), new Bits())).first();
        ComponentMapper <PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper <MapComponent> mapcm = ComponentMapper.getFor(MapComponent.class);
        camera.position.set(Gdx.graphics.getWidth()/2, mapcm.get(mapEntity).getMapHeight()/2, 0);
        camera.update();
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        ComponentMapper <PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper <RenderComponent> rcm = ComponentMapper.getFor(RenderComponent.class);
        ComponentMapper <MapComponent> mapcm = ComponentMapper.getFor(MapComponent.class);


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);


        if ((pcm.get(entities.first()).getPosition().x > camera.viewportWidth/2) && (pcm.get(entities.first()).getPosition().x < mapcm.get(mapEntity).getMapWidth()-camera.viewportWidth/2)){
            camera.position.set(pcm.get(entities.first()).getPosition().x, mapcm.get(mapEntity).getMapHeight()/2, 0);
        }

        camera.update();
        mapcm.get(mapEntity).getTiledMapRenderer().setView(camera);
        mapcm.get(mapEntity).getTiledMapRenderer().render();

        batch.begin();

        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);
            batch.draw(rcm.get(entity).getImg(), pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y);
            //batch.draw(render.getImg(), 300, 300);
        }

        batch.end();
    }
}
