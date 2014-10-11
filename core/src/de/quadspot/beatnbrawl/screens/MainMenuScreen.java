/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.quadspot.beatnbrawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import de.quadspot.beatnbrawl.beatnbrawl;

//http://stackoverflow.com/questions/14675007/how-to-draw-smooth-text-in-libgdx

/**
 *
 * @author herfi
 */
public class MainMenuScreen implements Screen {
    final beatnbrawl game;

    OrthographicCamera camera;
    private TextButton startGameButton;
    Stage stage;

    public MainMenuScreen(final beatnbrawl gam) {
        game = gam;
        stage = new Stage();

    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
/*
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Turtles in Time!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);*/
        stage.act();
        stage.draw();
        //game.batch.end();
        
        
        

/*        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        } */
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void show() {
        Sprite sp = new Sprite(new Texture(Gdx.files.internal("button.png")));
        sp.setBounds(0,0, 1000, 100);
        SpriteDrawable sprite = new SpriteDrawable(sp);



        TextButtonStyle style = new TextButtonStyle(sprite,sprite,sprite,game.font);

        startGameButton = new TextButton( "Start game",  style);



        startGameButton.addListener( new InputListener () {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                return true; // return true to indicate the event was handled
            }
        } );


        stage.addActor(startGameButton);
        Gdx.input.setInputProcessor(stage);

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
