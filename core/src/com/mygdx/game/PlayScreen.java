package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
    private TalonPlatformer game;
    // Texture texture;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(TalonPlatformer game) {
        this.game = game;
         // Subject to change
         // texture = new Texture("badlogic.jpg");

         // Create a camera that is used to follow the player through the TalonPlatformer world
         gamecam = new OrthographicCamera();

         // Create a FitViewPort to maintain virtual aspect ratio despite what the player does with the screen size
         gamePort = new FitViewport(TalonPlatformer.V_WIDTH, TalonPlatformer.V_HEIGHT, gameCam);

         // Create our game HUD for scores/timers/level info
         hud = new Hud(game.batch);

         mapLoader = new TmxMapLoader();
         map = maploader.load("Talon platformer map.tmx");
         renderer = new OrthogonalTiledMapRenderer(map);
         gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }
    
    @Override
    public void show()
    {
        

    }

    public void handleInput(float deltaTime) {
        if (Gdx.input.isTouched()) {
            // Temporary
            gamecam.position.x += 100 * deltaTime;
        }
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) 
    {   
        update(delta);

        // Clear the game screen with Black
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        // Set our batch to now draw what the Hud camera sees
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        // game.batch.draw(texture, 0, 0);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
    

    }

    @Override
    public void dispose() 
    {
    

    }
    
}