/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.quadspot.beatnbrawl.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import de.quadspot.beatnbrawl.beatnbrawl;
import de.quadspot.beatnbrawl.components.AIComponent;
import de.quadspot.beatnbrawl.components.ActionComponent;
import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.HealthComponent;
import de.quadspot.beatnbrawl.components.InputComponent;
import de.quadspot.beatnbrawl.components.MapComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;
import de.quadspot.beatnbrawl.components.StateComponent;
import de.quadspot.beatnbrawl.systems.AISystem;
import de.quadspot.beatnbrawl.systems.AnimationSystem;
import de.quadspot.beatnbrawl.systems.CollisionSystem;
import de.quadspot.beatnbrawl.systems.InputSystem;
import de.quadspot.beatnbrawl.systems.MovementSystem;
import de.quadspot.beatnbrawl.systems.PlayerSystem;
import de.quadspot.beatnbrawl.systems.RenderSystem;
import de.quadspot.beatnbrawl.systems.StateSystem;

/**
 *
 * @author herfi
 */
public class GameScreen implements Screen{
    final beatnbrawl game;
    Engine engine;
    OrthographicCamera camera;

    public static final int GAME_READY = 0;
    public static final int GAME_RUNNING = 1;
    public static final int GAME_PAUSED = 2;
    public static final int GAME_LEVEL_END = 3;
    public static final int GAME_OVER = 4;

    private int state;

    public GameScreen(final beatnbrawl game) {
        this.game = game;
        state = GAME_RUNNING;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        engine = new Engine();

        Entity entity = new Entity();
        entity.add(new RenderComponent());
        entity.add(new PositionComponent(new Vector3(100,0,100)));
        entity.add(new MovementComponent(new Vector3(0,0,0),new Vector3(500,0,200)));
        entity.add(new InputComponent());
        entity.add(new AnimationComponent("don.atlas"));
        entity.add(new CollisionComponent());
        entity.add(new ActionComponent());
        entity.add(new StateComponent());
        entity.add(new HealthComponent());

        engine.addEntity(entity);

        Entity map = new Entity();
        map.add(new MapComponent(game.batch, "maps/schiff2.tmx"));
        engine.addEntity(map);

        //entity.removeAll();
        Entity entity2 = new Entity();
        entity2.add(new PositionComponent(new Vector3(500,0,150)));
        entity2.add(new RenderComponent());
        entity2.add(new MovementComponent(new Vector3(0,0,0),new Vector3(400,1000,100)));
        //entity2.add(new InputComponent());
        entity2.add(new AnimationComponent("don.atlas"));
        entity2.add(new CollisionComponent());
        entity2.add(new ActionComponent());
        entity2.add(new StateComponent());
        entity2.add(new HealthComponent());

        entity2.add(new AIComponent());
        engine.addEntity(entity2);


        engine.addSystem(new RenderSystem(camera, game));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new InputSystem(camera, game.batch));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new AISystem());
        engine.addSystem(new PlayerSystem(this));
        engine.addSystem(new StateSystem(-10));
    }

    public void update(float deltaTime) {
        //if (deltaTime > 0.1f) deltaTime = 0.1f;

        engine.update(deltaTime);

        switch (state) {
            case GAME_READY:
                //updateReady();
                break;
            case GAME_RUNNING:
                //updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
                //updatePaused();
                break;
            case GAME_LEVEL_END:
                //updateLevelEnd();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    public void updateGameOver(){
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        update(delta);
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public void setState(int state) {
        this.state = state;
    }
}
