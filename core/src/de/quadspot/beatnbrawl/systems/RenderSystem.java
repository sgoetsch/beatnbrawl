package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
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

import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;

/**
 * Created by goetsch on 05.08.14.
 */
public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    public RenderSystem(OrthographicCamera camera, SpriteBatch batch){
        this.camera = camera;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(PositionComponent.class, RenderComponent.class));
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        ComponentMapper <PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper <RenderComponent> rcm = ComponentMapper.getFor(RenderComponent.class);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();

        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);
            batch.draw(rcm.get(entity).getImg(), pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y);
            //batch.draw(render.getImg(), 300, 300);
        }

        batch.end();
    }
}
