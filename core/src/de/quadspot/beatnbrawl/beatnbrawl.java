package de.quadspot.beatnbrawl;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.InputComponent;
import de.quadspot.beatnbrawl.components.MapComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;
import de.quadspot.beatnbrawl.systems.AnimationSystem;
import de.quadspot.beatnbrawl.systems.InputSystem;
import de.quadspot.beatnbrawl.systems.MovementSystem;
import de.quadspot.beatnbrawl.systems.RenderSystem;

public class beatnbrawl extends Game {
	//SpriteBatch batch;
	//Texture img;
    Engine engine;
    OrthographicCamera camera;
    Float delta;
    SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
        delta = Gdx.graphics.getDeltaTime();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        engine = new Engine();

        Entity entity = new Entity();
        entity.add(new RenderComponent());
        entity.add(new PositionComponent(new Vector3(100,0,10)));
        entity.add(new MovementComponent(new Vector3(0,0,0),new Vector3(500,1000,200)));
        entity.add(new InputComponent());
        entity.add(new AnimationComponent("don.atlas")); // TODO Evtl. noch scale übergeben?
        engine.addEntity(entity);

        Entity map = new Entity();
        map.add(new MapComponent(batch));
        engine.addEntity(map);

        //entity.removeAll();
        Entity entity2 = new Entity();
        entity2.add(new PositionComponent(new Vector3(500,0,0)));
        entity2.add(new RenderComponent());
        engine.addEntity(entity2);


        engine.addSystem(new RenderSystem(camera, batch));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new InputSystem(camera, batch));
        engine.addSystem(new AnimationSystem());




	}

	@Override
	public void render () {
/*		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
        delta = Gdx.graphics.getDeltaTime();

        engine.update(delta);
	}

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
    }
}
