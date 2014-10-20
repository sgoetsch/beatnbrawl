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

import de.quadspot.beatnbrawl.Factory;
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
import de.quadspot.beatnbrawl.systems.HealthSystem;
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
    Factory factory;

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

        factory = new Factory(game, engine);

        addSystems();

        //pauseSystems();
    }

    public void update(float deltaTime) {
        if (deltaTime > 0.1f) deltaTime = 0.1f;
        //System.out.println("State: " +state);
        engine.update(deltaTime);
        game.batch.begin();
        switch (state) {
            case GAME_READY:
                //updateReady();
                break;
            case GAME_RUNNING:
                resumeSystems();

                System.out.println("State: " +state);
                //updateRunning(deltaTime);
                game.batch.end();
                break;
            case GAME_PAUSED:
                //updatePaused();
                break;
            case GAME_LEVEL_END:
                updateLevelEnd();
                System.out.println("State: " +state);

                setState(GameScreen.GAME_RUNNING);
                break;
            case GAME_OVER:
                updateGameOver();
                break;
            default:
                game.batch.end();
        }
    }

    public void updateGameOver(){
        game.font.draw(game.batch, "GAME OVER, MAN!", 30, 680 - 20);
        game.batch.end();
        pauseSystems();
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    public void updateLevelEnd(){
        game.font.draw(game.batch, "YOU MADE IT, MAN!", 30, 680 - 20);
        game.batch.end();
        //pauseSystems();

        // Entities löschen
        // factory mit neuem Level ausführen
        // resume

        if (Gdx.input.justTouched()) {
            removeSystems();
            newLevel();
            addSystems();
            //resumeSystems();
        }
    }

    public void pauseSystems() {
        engine.getSystem(MovementSystem.class).setProcessing(false);
        engine.getSystem(InputSystem.class).setProcessing(false);
        engine.getSystem(AnimationSystem.class).setProcessing(false);
        engine.getSystem(CollisionSystem.class).setProcessing(false);
        engine.getSystem(AISystem.class).setProcessing(false);
        engine.getSystem(HealthSystem.class).setProcessing(false);
        engine.getSystem(PlayerSystem.class).setProcessing(false);
        engine.getSystem(StateSystem.class).setProcessing(false);
        engine.getSystem(RenderSystem.class).setProcessing(false);
    }

    public void resumeSystems() {
        engine.getSystem(MovementSystem.class).setProcessing(true);
        engine.getSystem(InputSystem.class).setProcessing(true);
        engine.getSystem(AnimationSystem.class).setProcessing(true);
        engine.getSystem(CollisionSystem.class).setProcessing(true);
        engine.getSystem(AISystem.class).setProcessing(true);
        engine.getSystem(HealthSystem.class).setProcessing(true);
        engine.getSystem(PlayerSystem.class).setProcessing(true);
        engine.getSystem(StateSystem.class).setProcessing(true);
        engine.getSystem(RenderSystem.class).setProcessing(true);
    }

    public void addSystems() {
        engine.addSystem(new RenderSystem(camera, game));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new InputSystem(camera, game.batch));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new AISystem(this));
        engine.addSystem(new HealthSystem());
        engine.addSystem(new PlayerSystem(this));
        engine.addSystem(new StateSystem(-10));
    }

    public void removeSystems() {
        engine.removeSystem(engine.getSystem(StateSystem.class));
        engine.removeSystem(engine.getSystem(PlayerSystem.class));
        engine.removeSystem(engine.getSystem(HealthSystem.class));
        engine.removeSystem(engine.getSystem(AISystem.class));
        engine.removeSystem(engine.getSystem(CollisionSystem.class));
        engine.removeSystem(engine.getSystem(AnimationSystem.class));
        engine.removeSystem(engine.getSystem(InputSystem.class));
        engine.removeSystem(engine.getSystem(MovementSystem.class));
        engine.removeSystem(engine.getSystem(RenderSystem.class));
    }

    public void newLevel() {
        engine.removeAllEntities();
        //engine.removeEntity(factory.);
        //camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        factory.loadLevel();
        //factory = new Factory(game, engine);



        // Entities löschen
        // factory mit neuem Level ausführen
    }

    @Override
    public void render(float delta) {
        //engine.update(delta);
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
