package com.mygdx.game;

public class Player {
    private static int lives;
    private static String playerName;
    private static boolean playerIsDead = false;

    public Player(String playerName, int lives){
        this.playerName = playerName;
        this.lives = lives;
    }
    public static void main(String[] args){
        //test run for logic code
        setName("bob");
        setLives(3);
        if(playerIsDead){
            System.out.println(playerName + " has died.");
        }
        
    }
    public int getLives(){
        return lives;
    }
    public String getName(){
        return playerName;
    }
    public boolean isPlayerDead(){
        if(lives > 0){
            playerIsDead = false;
        }else{
            playerIsDead = true;
        }
        return playerIsDead;
    }
    public static void setLives(int a){
        lives = a;
    }
    public static void setName(String name){
        playerName = name;
    }
    public static void playerLostALife(){
        int x = lives;
        setLives(x-1);
    }

    
}