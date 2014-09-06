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
    Float delta;

    public GameScreen(final beatnbrawl gam) {
        game = gam;
        delta = Gdx.graphics.getDeltaTime();
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
        entity2.add(new AIComponent());
        engine.addEntity(entity2);


        engine.addSystem(new RenderSystem(camera, game.batch));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new InputSystem(camera, game.batch));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new AISystem());
        engine.addSystem(new StateSystem(-10));
    }

    @Override
    public void render(float f) {
        delta = Gdx.graphics.getDeltaTime();

        engine.update(delta);    }

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
    
}
