package com.mygdx.game;

public class Enemies {
    private int enemyLives;
    private int positionX;
    private int positionY;

    public Enemies(int enemyLives, int positionX, int positionY){
        this.enemyLives = enemyLives;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getEnemyLives(){
        return enemyLives;
    }
    
    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }
}