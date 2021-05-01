package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Player extends Sprite
{
public enum State { FALLING, JUMPING, IDLE, ROLLING, DEAD };
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerIdle;
    private Animation<TextureRegion> playerRoll;
    private Animation<TextureRegion> playerJump;
    private boolean rollingRight;
    private float stateTimer;
    private float timeCount;
    public boolean loseLife;
    public int lives = 3;
    //public int lives = 1000;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private PlayScreen screen;

public Player(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("Player"));
        this.screen = screen;
        this.world = world;
        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        rollingRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 7; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
            playerRoll = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();
        }

        for (int i = 6; i < 10; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
            playerJump = new Animation<TextureRegion>(0.1f, frames);
        }

        playerIdle = new TextureRegion(getTexture(), 0, 0, 16, 16);
        definePlayer();
        setBounds(0, 0, 16 / TalonPlatformer.PPM, 16 / TalonPlatformer.PPM);
        setRegion(playerIdle);

        minX = 0;
        minY = 0;
        maxX = 2500;
        maxY = 1000;

    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        
        if(loseLife() || PlayScreen.getContact())
            {
                PlayScreen.setContact(false);
                lives --;
                resetPlayer();
                System.out.println(lives);
            }
        }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch(currentState) {
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer);
                break;
            case ROLLING:
                region = playerRoll.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case IDLE:
            default:
                region = playerIdle;
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !rollingRight) && !region.isFlipX()) {
            region.flip(true, false);
            rollingRight = false;
        }

        else if ((b2body.getLinearVelocity().x > 0 || !rollingRight) && region.isFlipX()) {
            region.flip(true, false);
            rollingRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if(currentState == State.DEAD)
        {
            return State.DEAD;
        }
        
        if(lives == 0) {
            return State.DEAD;
        }
        
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            return State.JUMPING;
        }

        if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }

        else if (b2body.getLinearVelocity().x != 0) {
            return State.ROLLING;
        }
        

        else {
            return State.IDLE;
        }
    }

    public void setState()
    {
        currentState = State.DEAD;
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        //This the OG cuz
        //bdef.position.set(32 / TalonPlatformer.PPM, 32 / TalonPlatformer.PPM);
        //This the second level cuz
        //bdef.position.set(32 / TalonPlatformer.PPM, 175 / TalonPlatformer.PPM);
        //This the third level cuz
        bdef.position.set(32 / TalonPlatformer.PPM, 32 / TalonPlatformer.PPM);
        //This the fourth level cuz
        //bdef.position.set(32 / TalonPlatformer.PPM, 200 / TalonPlatformer.PPM);
        //This the fifth level cuz
        //bdef.position.set(32 / TalonPlatformer.PPM, 150 / TalonPlatformer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        
        CircleShape shape = new CircleShape();
        

        shape.setRadius(6 / TalonPlatformer.PPM);
        
        FixtureDef fdef = new FixtureDef();
        
        fdef.shape = shape;
        b2body.createFixture(fdef);
        
        EdgeShape head = new EdgeShape(); 
        
        head.set(new Vector2(-2 / TalonPlatformer.PPM, 6 / TalonPlatformer.PPM), new Vector2(2 / TalonPlatformer.PPM, 6 / TalonPlatformer.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        EdgeShape feet = new EdgeShape();

        feet.set(new Vector2(-2 / TalonPlatformer.PPM, -6 / TalonPlatformer.PPM), new Vector2(2 / TalonPlatformer.PPM, -6 / TalonPlatformer.PPM));
        fdef.shape = feet;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");
        b2body.createFixture(fdef).setUserData("feet");
    }
    
    public void resetPlayer() 
        {
            definePlayer();
        }

    public void jump() {
        if (currentState !=State.JUMPING) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    public boolean loseLife() {
        
        if (b2body.getPosition().x > maxX || b2body.getPosition().x < minX) {
            return true;
        }

        if (b2body.getPosition().y > maxY || b2body.getPosition().y < minY) {
            return true;
        }

        else {
            return false;
        }
    }
    public int getLives()
    {
        return lives;
    }
}