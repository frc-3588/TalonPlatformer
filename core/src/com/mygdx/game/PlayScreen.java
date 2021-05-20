package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Player.State;

public class PlayScreen implements Screen 
{
    // Reference to the game, used to set screems
    public TalonPlatformer game;

    // Makes the map and animations in a useable format
    private TextureAtlas atlas;
    private TextureRegion region;

    // Game music playing in the background
    private Music music;

    // Used for what the player will view
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    public Hud hud;
    private Stage stage;

    // Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Physics world variables
    public World world;
    // private Box2DDebugRenderer b2dr;

    // Player sprite
    private Player player;

    private static Player thePlayer;
    private static boolean isTouch;

    // Buttons for touch screen
    private Button jumpBtn;
    private Button leftBtn;
    private Button rightBtn;

    public PlayScreen(TalonPlatformer game) 
    {
        // CHANGE TO PNG WITH ALL THE SPRITES 
        atlas = new TextureAtlas("Player_and_Enemies.pack");
        region = new TextureRegion(new Texture("platformerFlat.jpg"));
        
        
        music = Gdx.audio.newMusic(Gdx.files.internal("clockwork_chamber_full.mp3"));
        music.setVolume((float) 0.25);
        music.setLooping(true);
        music.play();

        this.game = game;

        // Create a camera that is used to follow the player through the TalonPlatformer world
        gamecam = new OrthographicCamera();

        // Create a FitViewPort to maintain virtual aspect ratio despite what the player does with the screen size
        gamePort = new FitViewport(TalonPlatformer.V_WIDTH / TalonPlatformer.PPM, TalonPlatformer.V_HEIGHT / TalonPlatformer.PPM, gamecam);

        stage = new Stage();

        // Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("GameMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / TalonPlatformer.PPM);

        // Initially set our gamecam to be centered corretly at the start of the game
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // World creation with gravity
        world = new World(new Vector2(0, -10), true);

        // Allows for debug lines of our box2d world
        //  b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        // Create the player in our game world
        player = new Player(world, this); 

        thePlayer = player;
         
        // Looks for collisions with spikes and traps
        world.setContactListener(new WorldContactListener());
         
        // Create our game HUD for scores/timers/level info
        hud = new Hud(player, game.batch);

        // Attaches images to the buttons themselves
        jumpBtn = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Jump button.png")))));
        leftBtn = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Left button.png")))));
        rightBtn = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Right button.png")))));
        
        stage.addActor(leftBtn);
        stage.addActor(rightBtn);
        stage.addActor(jumpBtn);

        // Makes every button look for when it is pressed/touched
        leftBtn.addListener(new ClickListener() 
        {              
            public boolean touchDown(InputEvent e, float x, float y, int point) 
            {
                return true;
            };
        });

        rightBtn.addListener(new ClickListener() 
        {
            public boolean touchDown(InputEvent e, float x, float y, int point) 
            {
                return true;
            };
        });

        jumpBtn.addListener(new ClickListener() 
        {
            public boolean touchDown(InputEvent e, float x, float y, int point) 
            {
                return true;
            };
        });

        // Sets the position of each button at specific locations of the map
        leftBtn.setPosition(0, 60);
        rightBtn.setPosition(40, 60);
        jumpBtn.setPosition(Gdx.graphics.getWidth()-jumpBtn.getWidth(), 60);
        
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show()
    {


    }


    public void handleInput(float deltaTime) {
        // FOR TESTING WITH APPLYING NO GAME PHYSICS (MOVE THROUGH THE MAP FAST)
        // if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0) {
        //     player.b2body.applyForceToCenter(0, 150, true);
        // }
        // if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) 
        // { 
        //     player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true); 
        // }

        // TOUCH MOVE LEFT
        if(leftBtn.isPressed())
        {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
            
        // TOUCH MOVE RIGHT
        if(rightBtn.isPressed())
        {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        // TOUCH JUMP UP
        if(jumpBtn.isPressed())
        {
            player.jump();
        }
        
        // KEYBOARD ARROW MOVE LEFT
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        // KEYBOARD ARROW MOVE RIGHT
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }


        // KEYBOARD ARROW JUMP UP
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.jump();
        }

    }
public void update(float deltaTime) {
        // Checks for user input (touching screen or using arrow keys)
        handleInput(deltaTime);

        // This performs collision detection, integration, and constraint solution.
        // A player won't move if this isn't here
        world.step(1/60f, 6, 2);

        player.update(deltaTime);

        // Makes the camera follow the player
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

//        if(hud.getKeycount() == 0)
//        {
//            music.stop();
//            game.setScreen(new EndScreen(game, true));
//        }
//      

        if(player.getState() == State.DEAD)
        {
            music.stop();
            game.setScreen(new EndScreen(game, false));
        }

        if(hud.getKeycount() == 1)
        {
            player.setKeys(1);
        }
        else if(hud.getKeycount() == 2)
        {
            player.setKeys(2);
        }
        else if(hud.getKeycount() == 3)
        {
            player.setKeys(3);
        }
        else if(hud.getKeycount() == 4)
        {
            player.setKeys(4);
        }
        else if(hud.getKeycount() == 5)
        {
            music.stop();
            game.setScreen(new EndScreen(game, true));
        }

        // Render our game map
        renderer.render();

        // Render our Box2DDebugLines
        // b2dr.render(world, gamecam.combined);

        // Set our batch to now draw what the Hud camera sees
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
        
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        stage.act(delta);
        stage.draw();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width, height);
        stage.getViewport().update(width, height, true);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        // b2dr.dispose();
        // leftBtn.dispose();
        // rightBtn.dispose();
        // jumpBtn.dispose();
        hud.dispose();
        music.dispose();
    }

    public static Player getPlayer()
    {
        return thePlayer;
    }
    
}