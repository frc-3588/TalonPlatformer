package com.mygdx.game;

public class PlayScreen {
    private boolean playerIsDead = false;
    
    public static void main(String[] args){

        //set parameters for the character (name, # lives)
        Player myPlayer = new Player("Player 1", 3);

        //set parameters for the enemies (lives, location(x,y))
        int coordinateX = 0;
        int coordinateY = 0;
        Enemies myEnemy1 = new Enemies(1, coordinateX, coordinateY);
        //this only creates 1 enemy
        //repeat process, labeling myEnemy2, myEnemy3, etc. for more enemies

        //initiate World gravity



    }

    //CHARACTER methods
    public void livesDeductor(){
        
    }

    //ENEMIES methods
    public void enemyLivesDeductor(){

    }
    //GRAVITY/MOTIONS methods

    //

   
    
    
}