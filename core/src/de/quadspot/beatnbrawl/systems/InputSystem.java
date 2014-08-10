package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import de.quadspot.beatnbrawl.components.InputComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;

/**
 * Created by goetsch on 05.08.14.
 */
public class InputSystem extends EntitySystem{
    private Stage stage;
    private SpriteBatch batch;
    private ImmutableArray<Entity> entities;
    private OrthographicCamera camera;


    public InputSystem(OrthographicCamera camera, SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(InputComponent.class));

        ComponentMapper<InputComponent> icm = ComponentMapper.getFor(InputComponent.class);

        //Create a Stage and add TouchPad
        //stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
        stage = new Stage(new ExtendViewport(camera.viewportWidth,camera.viewportHeight), batch);

        stage.addActor(icm.get(entities.first()).getTouchpad());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {

        ComponentMapper<InputComponent> icm = ComponentMapper.getFor(InputComponent.class);
        ComponentMapper<MovementComponent> mcm = ComponentMapper.getFor(MovementComponent.class);

        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);
            mcm.get(entity).getVelocity().set(new Vector2(mcm.get(entity).getMaxVelocity() * icm.get(entity).getTouchpad().getKnobPercentX(),mcm.get(entity).getMaxVelocity() * icm.get(entity).getTouchpad().getKnobPercentY()));
        }

        stage.act(deltaTime);
        stage.draw();

    }
}
