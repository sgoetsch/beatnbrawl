package de.quadspot.beatnbrawl;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;
import de.quadspot.beatnbrawl.systems.MovementSystem;
import de.quadspot.beatnbrawl.systems.RenderSystem;

public class beatnbrawl extends Game {
	//SpriteBatch batch;
	//Texture img;
    Engine engine;
    OrthographicCamera camera;
    Float delta;
	
	@Override
	public void create () {
/*		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");*/
        delta = Gdx.graphics.getDeltaTime();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        engine = new Engine();
        engine.addSystem(new RenderSystem(camera));
        engine.addSystem(new MovementSystem());

        Entity entity = new Entity();
        entity.add(new RenderComponent());
        entity.add(new PositionComponent(new Vector2(300,300)));
        entity.add(new MovementComponent(new Vector2(10,-10),20));

        engine.addEntity(entity);
        //entity.removeAll();
        Entity entity2 = new Entity();
        entity2.add(new PositionComponent(new Vector2(-500,0)));
        entity2.add(new RenderComponent());
        engine.addEntity(entity2);


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
