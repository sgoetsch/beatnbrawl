package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.quadspot.beatnbrawl.components.CollisionComponent;

import de.quadspot.beatnbrawl.components.InputComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;

/**
 * Created by goetsch on 05.08.14.
 */
public class InputSystem extends EntitySystem implements InputProcessor{
    private Stage stage;
    private SpriteBatch batch;
    private ImmutableArray<Entity> entities;
    private OrthographicCamera camera;
    private ComponentMapper<InputComponent> icm;
    private ComponentMapper<MovementComponent> mcm;
    private ComponentMapper<CollisionComponent> ccm;


    public InputSystem(OrthographicCamera camera, SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {

        InputMultiplexer im = new InputMultiplexer();

        entities = engine.getEntitiesFor(Family.getFor(InputComponent.class, CollisionComponent.class, MovementComponent.class));

        icm = ComponentMapper.getFor(InputComponent.class);
        ccm = ComponentMapper.getFor(CollisionComponent.class);

        //Create a Stage and add TouchPad
        //stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
        stage = new Stage(new ExtendViewport(camera.viewportWidth,camera.viewportHeight), batch);
        stage.addActor(icm.get(entities.first()).getTouchpad());

        im.addProcessor(stage);
        im.addProcessor(this);
        Gdx.input.setInputProcessor(im);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {

        icm = ComponentMapper.getFor(InputComponent.class);
        mcm = ComponentMapper.getFor(MovementComponent.class);

        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);
            mcm.get(entity).getVelocity().set(new Vector3(mcm.get(entity).getMaxVelocity().x * icm.get(entity).getTouchpad().getKnobPercentX(),
                    0,
                    mcm.get(entity).getMaxVelocity().z * icm.get(entity).getTouchpad().getKnobPercentY()));
        }

        stage.act(deltaTime);
        stage.draw();
        if ((icm.get(entities.first()).getTouchpad().isTouched() == false) && (icm.get(entities.first()).getTouchpad().isVisible() == true))
            icm.get(entities.first()).getTouchpad().setVisible(false);


    }

    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link com.badlogic.gdx.Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link com.badlogic.gdx.Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link com.badlogic.gdx.Input.Buttons#LEFT} on
     * Android and iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (Gdx.input.getX(pointer) > Gdx.graphics.getWidth()/2 ){
            if(mcm.get(entities.first()).getState().equals(MovementComponent.State.STAND_RIGHT)||mcm.get(entities.first()).getState().equals(MovementComponent.State.WALK_RIGHT)){
               System.out.println("Attack right");

                mcm.get(entities.first()).setState(MovementComponent.State.ATTACK_RIGHT);
                ccm.get(entities.first()).getCollidingBody().attackRight(100);
    
                
            }else if(mcm.get(entities.first()).getState().equals(MovementComponent.State.STAND_LEFT) || mcm.get(entities.first()).getState().equals(MovementComponent.State.WALK_LEFT)){
                System.out.println("Attack left");
                
                mcm.get(entities.first()).setState(MovementComponent.State.ATTACK_LEFT);
                ccm.get(entities.first()).getCollidingBody().attackLeft(100);
            }
            
        }
        else {
            icm = ComponentMapper.getFor(InputComponent.class);
            icm.get(entities.first()).getTouchpad().setBounds(stage.screenToStageCoordinates(new Vector2(screenX, screenY)).x - 250, stage.screenToStageCoordinates(new Vector2(screenX, screenY)).y - 250, 500, 500);
            icm.get(entities.first()).getTouchpad().setVisible(true);
            stage.touchDown(screenX, screenY, pointer, button);
        }
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link com.badlogic.gdx.Input.Buttons#LEFT} on Android
     * and iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button   @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.  @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on either Android or iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on either Android or iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
