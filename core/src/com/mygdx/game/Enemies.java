package com.mygdx.game;
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

public class Enemies extends Sprite {
    public enum State { FALLING, IDLE, ROLLING, JUMPING };
    public State currentState;
    public State previousState;
    public World world;
    public PlayScreen screen;
    public Body b2body;
    private TextureRegion enemyIdle;
    private Animation<TextureRegion> enemyRoll;
    //private Animation<TextureRegion> enemyJump;
    private boolean movingRight;
    private boolean rollingRight;
    private float stateTimer;
    private int xPosition;
    private int yPosition;
    private boolean enemyIsDead = false;
    private boolean enemyCanMove = true;
    private boolean lives = true;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public Enemies(World world, PlayScreen screen, float x, float y) {
        this.world = world;
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
        b2body.setActive(false);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 15; i < 18; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
            enemyRoll = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();
        }
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }
    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch(currentState) {
            case ROLLING:
                region = enemyRoll.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case IDLE:
            default:
                region = enemyIdle;
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
    public void hitOnHead(Player player)
    {

    }

    /*public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch(currentState) {
            case ROLLING:
                region = enemyRoll.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case IDLE:
            default:
                region = enemyIdle;
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !rollingRight) && !region.isFlipX()) {
            region.flip(true, false);
            rollingRight = false;
        }

        else if ((b2body.getLinearVelocity().x > 0 || rollingRight) && region.isFlipX()) {
            region.flip(true, false);
            rollingRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }*/

    public State getState() {
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
    
    public void hasContact(){
        String con = System.console().readLine().toLowerCase();
        if(con.contains("begin")){
            flipDirection();
        }
    }
    
    public void flipDirection(){
        if(rollingRight){
            rollingRight = false;
        }
        else{
            rollingRight = true;
        }
    }
    
    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / TalonPlatformer.PPM, 32 / TalonPlatformer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1 / TalonPlatformer.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);

        FixtureDef fdef2 = new FixtureDef();
        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-2 / TalonPlatformer.PPM, -6 / TalonPlatformer.PPM), new Vector2(2 / TalonPlatformer.PPM, -6 / TalonPlatformer.PPM));
        fdef2.shape = feet;
        b2body.createFixture(fdef2);
    }
    
    public void movement(){
        if(rollingRight){
            b2body.applyLinearImpulse(new Vector2(0.1f, 0), b2body.getWorldCenter(), true);
        }
        else{
            b2body.applyLinearImpulse(new Vector2(-0.1f, 0), b2body.getWorldCenter(), true);
        }
    }
    
    public void resetenemy() {
        lives = true;
        enemyIsDead = false;
        enemyCanMove = true;
    }

    public boolean isenemyDead() {
        if (!lives) {
            enemyIsDead = true;
            enemyCanMove = false;
        }

        return enemyIsDead;
    }

    public boolean didEnemyFall(int yPosition) {
        if (yPosition < 0) {
            return true;
        }

        return false;
    }

    public boolean outOfbounds(int xPosition, int minX, int maxX, int yPosition, int minY, int maxY) {
        if (xPosition > maxX || xPosition < minX) {
            return true;
        }
        else if (yPosition < maxY || yPosition < minY) {
            return true;
        }

        else {
            return false;
        }
    }

}


