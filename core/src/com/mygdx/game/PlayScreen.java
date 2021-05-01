package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.mygdx.game.Player.State;

public class PlayScreen implements Screen{
    // Reference to the game, used to set screems
    public TalonPlatformer game;
    private TextureAtlas atlas;
    private TextureRegion region;
    private Music music;


    // Texture texture;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    public Hud hud;

    // Tiled map variables
    private TmxMapLoader maploader;
    public TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d variables
    public World world;
    private Box2DDebugRenderer b2dr;

    // Player sprite
    private Player player;

    private static Player thePlayer;
    private static boolean isTouch;

    public PlayScreen(TalonPlatformer game) {
        // CHANGE TO PNG WITH ALL THE SPRITES 
        atlas = new TextureAtlas("Player_and_Enemies.pack");
        region = new TextureRegion(new Texture("platformerFlat.jpg"));
        
        music = Gdx.audio.newMusic(Gdx.files.internal("happy_level_theme_Test_1.mp3"));
        music.setLooping(true);
        music.play();

        this.game = game;
         // Subject to change
         // texture = new Texture("badlogic.jpg");

         // Create a camera that is used to follow the player through the TalonPlatformer world
         gamecam = new OrthographicCamera();

         // Create a FitViewPort to maintain virtual aspect ratio despite what the player does with the screen size
         gamePort = new FitViewport(TalonPlatformer.V_WIDTH / TalonPlatformer.PPM, TalonPlatformer.V_HEIGHT / TalonPlatformer.PPM, gamecam);

        

         // Load our map and setup our map renderer
         maploader = new TmxMapLoader();
         map = maploader.load("LevelThree.tmx");
         renderer = new OrthogonalTiledMapRenderer(map, 1 / TalonPlatformer.PPM);

         // Initially set our gamecam to be centered corretly at the start of the game
         gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

         // World creation with gravity
         world = new World(new Vector2(0, -10), true);

         // Allows for debug lines of our box2d world
         b2dr = new Box2DDebugRenderer();

         new B2WorldCreator(world, map);

         // Create the player in our game world
         player = new Player(world, this); 

         thePlayer = player;
         
         world.setContactListener(new WorldContactListener());
         // Create our game HUD for scores/timers/level info
         hud = new Hud(player, game.batch);
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show()
    {


    }

    public void handleInput(float deltaTime) {
        // if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0) {
        //     player.b2body.applyForceToCenter(0, 150, true);
        // }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.jump();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }
public void update(float deltaTime) {
        handleInput(deltaTime);

        world.step(1/60f, 6, 2);

        player.update(deltaTime);

        gamecam.position.x = player.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);

        hud.update(deltaTime, 0, true, true);
    }

    public static void setContact(boolean contactMade)
    {
        isTouch = contactMade;
    } 

    public static boolean getContact()
    {
        return isTouch;
    }

    @Override
    public void render(float delta) 
    {
        // Seperate our update logic from render
        update(delta);

        // Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(region, 0, 0, TalonPlatformer.V_WIDTH, TalonPlatformer.V_HEIGHT);
        game.batch.end();

        // Render our game map
        renderer.render();

        // Render our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        // Set our batch to now draw what the Hud camera sees
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(player.getState() == State.DEAD)
        {
            game.setScreen(new EndScreen(game, player));
        }
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        this.dispose();

    }

    public static Player getPlayer()
    {
        return thePlayer;
    }
    
}