package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EndScreen implements Screen 
{
    private TalonPlatformer game;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private Music music;
    private Texture background;

    public EndScreen(TalonPlatformer game, boolean playerWins) {
        this.game = game;
        if(playerWins)
        {
            music = Gdx.audio.newMusic(Gdx.files.internal("victory_GANG_test_1.mp3"));
            background = new Texture(Gdx.files.internal("Win screen.png"));
        }
        else
        {
            music = Gdx.audio.newMusic(Gdx.files.internal("the_theme_when_lose.mp3"));
            background = new Texture(Gdx.files.internal("Lose screen.png"));
        }

        music.setLooping(true);
        music.setVolume((float) 0.25);
        music.play();

        camera = new OrthographicCamera();
        gamePort = new FitViewport(TalonPlatformer.V_WIDTH / TalonPlatformer.PPM, TalonPlatformer.V_HEIGHT / TalonPlatformer.PPM, camera);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }

    @Override
    public void show() 
    {
        
    }

    @Override
    public void render(float delta) 
    {
        // Resets the background to all black
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draws the background to the screen
        game.batch.begin();
        game.batch.draw(background, 0, 0, TalonPlatformer.V_WIDTH / TalonPlatformer.PPM, TalonPlatformer.V_HEIGHT / TalonPlatformer.PPM);
        game.batch.end();
        
        // Matches the view of the camera to see the background
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        // Looks for if the player either clicks anywehre or presses any key
        // If so, they will be brought back to the menu
        Gdx.input.setInputProcessor(new InputAdapter() 
        {
            @Override
            public boolean keyDown(int keyCode) {
            if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
                game.setScreen(new MenuScreen(game));
                music.stop();
            }
            return true;
            }

            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
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
        music.dispose();
        background.dispose();
    }
}