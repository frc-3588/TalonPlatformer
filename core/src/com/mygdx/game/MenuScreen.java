package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen 
{
    TalonPlatformer game;
    Texture background;
    OrthographicCamera camera;
    Music music;

    public MenuScreen(TalonPlatformer game)
    {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture(Gdx.files.internal("Menu.jpg"));

        music = Gdx.audio.newMusic(Gdx.files.internal("clockwork_chamber_demo.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {

    }
    
    @Override
    public void render(float delta) 
    {
        
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {
            if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
                game.setScreen(new PlayScreen(game));
                dispose();
            }
            return true;
            }

            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                game.setScreen(new PlayScreen(game));
                dispose();
                return true;
            }
        });
    }

    @Override
    public void resize(int width, int height) 
    {

    }

    @Override
    public void pause() 
    {

    }

    @Override
    public void resume() 
    {

    }

    @Override
    public void hide() 
    {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose()
    {
        background.dispose();
        music.dispose();
    }
}