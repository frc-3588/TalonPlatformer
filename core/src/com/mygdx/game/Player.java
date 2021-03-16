package com.mygdx.game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Sprite {
    private static int lives;
    private static String playerName;
    private static boolean playerIsDead = false;
    private static boolean playerCanMove = true;
    private static boolean playerCanJump = true;
    public World world;
    public Body b2body;

    //constructor
    public Player(String playerName, int lives, World world){
        this.playerName = playerName;
        this.lives = lives;
        this.world = world;
        definePlayer();
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        // Temporary
        bdef.position.set(32 / TalonPlatformer.PPM, 32 / TalonPlatformer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        // Temporary radius of circle
        shape.setRadius(5 / TalonPlatformer.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public static void main(String[] args){
        //test run for logic code
        setName("bob");
        setLives(3);
        while(!playerIsDead){
            //mvmt controls
        }
        playerCanJump = false;
        playerCanMove = false;
        System.out.println(playerName + " has died.");
        
    }

    ///PLAYER LIVES/////////
    public int getLives(){
        return lives;
    }
    public static void setLives(int a){
        lives = a;
    }
    public static void playerLostALife(){
        int x = lives;
        setLives(x-1);
    }
    public boolean isPlayerDead(){
        if(lives > 0){
            playerIsDead = false;
        }else{
            playerIsDead = true;
        }
        return playerIsDead;
    }

    /////PLAYER NAME METHODS////////
    public String getName(){
        return playerName;
    }
    public static void setName(String name){
        playerName = name;
    }

    /////PLAYER MOVEMENT CTRLS/////

    public void moveRight(){

    }
    public void moveLeft(){
        
    }
    
}