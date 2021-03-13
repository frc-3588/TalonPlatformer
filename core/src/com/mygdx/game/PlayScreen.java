package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javafx.scene.shape.Rectangle;

public class PlayScreen implements Screen {
    private TalonPlatformer game;
    // Texture texture;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    
    // Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(TalonPlatformer game) {
        this.game = game;
         // Subject to change
         // texture = new Texture("badlogic.jpg");

         // Create a camera that is used to follow the player through the TalonPlatformer world
         gamecam = new OrthographicCamera();

         // Create a FitViewPort to maintain virtual aspect ratio despite what the player does with the screen size
         gamePort = new FitViewport(TalonPlatformer.V_WIDTH, TalonPlatformer.V_HEIGHT, gamecam);

         // Create our game HUD for scores/timers/level info
         hud = new Hud(game.batch);

         // Load our map and setup our map renderer
         maploader = new TmxMapLoader();
         map = maploader.load("Talon platformer map.tmx");
         renderer = new OrthogonalTiledMapRenderer(map);

         // Initially set our gamecam to be centered corretly at the start of the game
         gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

         // Temporary gravity
         world = new World(new Vector2(0, 0), true);
         b2dr = new Box2DDebugRenderer();

         // Bodies and fixtures
         BodyDef bdef = new BodyDef();
         // Temporary
         PolygonShape shape = new PolygonShape();
         FixtureDef fdef = new FixtureDef();
         Body body;

         // Creating a body and fixture for every corresponding object in the each of the map layers

         // Create ground bodies/fixtures
         for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
             com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();

             bdef.type = BodyDef.BodyType.StaticBody;
             bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

             body = world.createBody(bdef);

             shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
             fdef.shape = shape;
             body.createFixture(fdef);
         }
    }
    
    @Override
    public void show()
    {
        

    }

    public void handleInput(float deltaTime) {
        // if (Gdx.input.isTouched()) {
        //     // Temporary
        //     gamecam.position.x += 100 * deltaTime;
        // }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            gamecam.position.x -= 300 * deltaTime;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            gamecam.position.x += 300 * deltaTime;
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render our game map
        renderer.render();

        // Render our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

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