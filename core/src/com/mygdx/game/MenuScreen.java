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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen 
{
    TalonPlatformer game;
    Texture background;
    Viewport gamePort;
    OrthographicCamera camera;
    Music music;

    public MenuScreen(TalonPlatformer game)
    {
        this.game = game;

        music = Gdx.audio.newMusic(Gdx.files.internal("happy_level_theme_Test_1.mp3"));
        music.setLooping(true);
        music.play();

        camera = new OrthographicCamera();
//        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gamePort = new FitViewport(TalonPlatformer.V_WIDTH / TalonPlatformer.PPM, TalonPlatformer.V_HEIGHT / TalonPlatformer.PPM, camera);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        background = new Texture(Gdx.files.internal("Menu.jpg"));

    }

    @Override
    public void show() {

    }
    
    @Override
    public void render(float delta) 
    {
        
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0, TalonPlatformer.V_WIDTH / TalonPlatformer.PPM, TalonPlatformer.V_HEIGHT / TalonPlatformer.PPM);
        game.batch.end();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {
            if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
                game.setScreen(new PlayScreen(game));
                music.stop();
            }
            return true;
            }

            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                game.setScreen(new PlayScreen(game));
                music.stop();
                return true;
            }
        });
    }

    @Override
    public void resize(int width, int height) 
    {
        gamePort.update(width, height);
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