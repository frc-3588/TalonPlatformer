package com.mygdx.game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Sprite{
    private int lives = 3;
    private String playerName;
    private boolean playerIsDead = false;
    private boolean playerCanMove = true;
    private boolean playerCanJump = true;
    public World world;
    public Body b2body;
    private int xPosition;
    private int yPosition;

    //constructor
    public Player(World world, String playerName) {
        this.world = world;
        definePlayer();
        this.playerName = playerName;
    }

    public void resetPlayer(){
        lives = 3;
        playerIsDead = false;
        playerCanMove = true;
        playerCanJump = true;
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / TalonPlatformer.PPM, 32 / TalonPlatformer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / TalonPlatformer.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    ///PLAYER LIVES/////////
    public void setLives(int x){
        lives = x;
    }
    public void lostALife(){
        int x = lives;
        setLives(x-1);
    }
    public boolean isPlayerDead(){
        if(lives == 0){
            playerIsDead = true;
            playerCanJump = false;
            playerCanMove = false;
        }
        return playerIsDead;
    }

    /////PLAYER NAME////////
    public String getName(){
        return playerName;
    }
    public void setName(String name){
        playerName = name;
    }

    //PLAYER LOCATION//
    public boolean didPlayerFall(int yPosition){
        if(yPosition < 0){
            return true;
        }
    }

    public boolean outOfBounds(int xPosition, int minX, int maxX, int yPosition, int minY, int maxY){
        if(xPosition > maxX || xPosition < minX){
            return true;
        }else if(yPosition > maxY || yPosition < minY){
            return true;
        }else{
            return false;
        }
    }
    /*
    

    

    
    */
    
}