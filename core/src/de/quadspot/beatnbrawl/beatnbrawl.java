package de.quadspot.beatnbrawl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.quadspot.beatnbrawl.screens.MainMenuScreen;

public class beatnbrawl extends Game {
    OrthographicCamera camera;
    public SpriteBatch batch;
    public BitmapFont font;
    public static final boolean DEBUG = true;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        font = new BitmapFont();
        font.scale(3);
        this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
        super.render();
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
