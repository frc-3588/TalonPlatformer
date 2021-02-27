package com.mygdx.game;

public class Player {
    private int lives;
    private String playerName;

    public Player(String playerName, int lives){
        this.playerName = playerName;
        this.lives = lives;
    }
    public int getLives(){
        return lives;
    }
    public String getName(){
        return playerName;
    }

    
}