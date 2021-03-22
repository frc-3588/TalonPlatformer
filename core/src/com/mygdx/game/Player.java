package com.mygdx.game;

public class Player {
    private int lives = 3;
    private String playerName;
    private boolean playerIsDead = false;
    private boolean playerCanMove = true;
    private boolean playerCanJump = true;

    //constructor
    public Player(String playerName){
        this.playerName = playerName;
    }

    public void resetPlayer(){
        lives = 3;
        playerIsDead = false;
        playerCanMove = true;
        playerCanJump = true;
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
    
}