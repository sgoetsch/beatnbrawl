package de.quadspot.beatnbrawl;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class beatnbrawl extends ApplicationAdapter {
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
	}

	@Override
	public void render () {
/*		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/

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
