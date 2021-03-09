package com.mygdx.game;

public class Player {
    private static int lives;
    private static String playerName;
    private static boolean playerIsDead = false;
    private static boolean playerCanMove = true;
    private static boolean playerCanJump = true;
    //constructor
    public Player(String playerName, int lives){
        this.playerName = playerName;
        this.lives = lives;
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