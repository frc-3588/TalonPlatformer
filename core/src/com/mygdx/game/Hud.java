package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Player.State;

import java.text.MessageFormat;
import java.text.NumberFormat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Hud implements Disposable{
    //Instance Variables
    private int keyCount;
    private Player player;
    private int lives;
    private int worldTimer;
    private Viewport viewport;
    public float timeCount;
    public int score;
    public Player playerIsDead;
    public Player playerLostALife;
    public float dt;

    public Stage stage;

    //For displaying the hud
    private Label keyLabel;
    private Label livesLabel;
    private Label timeLabel;
    private static Label countdownLabel;
    private static Label keycountLabel;
    private Label livecountLabel;

    //keycount, lives, pause screen
    public Hud (Player player, SpriteBatch sb)
    {
            Table table = new Table();
            table.top();
            table.setFillParent(true);
            viewport = new FitViewport(400, 208, new OrthographicCamera());
            stage = new Stage(viewport, sb);

            //Initilizating of the instance variables
            keyCount = 0;
            worldTimer = 800;
            timeCount = 0;
            this.player = player;
            lives = player.lives;
            
           //Defining what the labels will show, what font, etc etc
            keyLabel = new Label("KEYS", new LabelStyle(new BitmapFont(), Color.WHITE));
            livesLabel = new Label("LIVES", new LabelStyle(new BitmapFont(), Color.WHITE));
            timeLabel = new Label("TIME", new LabelStyle(new BitmapFont(), Color.WHITE));
            
            /*These labels are special, they are able to format the string into any digit integer.
            For example, 3 digits would be 001 for keycount as an example. */
            
            countdownLabel = new Label(Integer.toString(worldTimer), new LabelStyle(new BitmapFont(), Color.WHITE));
            keycountLabel = new Label(Integer.toString(keyCount), new LabelStyle(new BitmapFont(), Color.WHITE));
            livecountLabel = new Label(Integer.toString(lives), new LabelStyle(new BitmapFont(), Color.WHITE));
            
            //Table that is used to display the labels
            table.add(keyLabel).expandX().padTop(10);
            table.add(livesLabel).expandX().padTop(10);
            table.add(timeLabel).expandX().padTop(10);
            table.row();
            table.add(keycountLabel).expandX();
            table.add(livecountLabel).expandX();
            table.add(countdownLabel).expandX();   

            stage.addActor(table);

    
    }

    public void update(float deltaTime, int liveCount, boolean playerLostALife, boolean playerIsDead)
    {
            /* This is for the timer, it is basically taking worldTimer, which is set to 300 and saying that if timeCount, 
            which is set to 0, is greater or equal to 1, you subtract from worldTimer. The setText is to display all of it.
            */
            timeCount += deltaTime;
            if(timeCount >= 1)
            {
                worldTimer --;
                countdownLabel.setText(Integer.toString(worldTimer));
                timeCount = 0;    
            }
            
            //This is saying if worldTimer is less than 1, then worldTimer is actually set to 0. The setText is there to set it and make it display the value.
            if(worldTimer < 1)
            {
                worldTimer = 0;
                countdownLabel.setText(Integer.toString(worldTimer));
            }
            
        //It is saying if the physics body of the player is at this position, you add 1 to the keyCount if the keyCount is less than 1.
        //Level One
        if(player.b2body.getPosition().x >= 25.2 && player.b2body.getPosition().x <= 25.4 
         && player.b2body.getPosition().y >= 1.5 && player.b2body.getPosition().y <= 1.7 && keyCount == 0)
        {
            keyCount ++;
        }
        //Level Two
        else if(player.b2body.getPosition().x >= 50.8 && player.b2body.getPosition().x <= 50.96 
        && player.b2body.getPosition().y >= 0.3837 && player.b2body.getPosition().y <= 0.5437 && keyCount == 1)
        {
            keyCount++;
        }
        // // Level Three
        else if(player.b2body.getPosition().x >= 76.16 && player.b2body.getPosition().x <= 76.32 
        && player.b2body.getPosition().y >= 0.8662 && player.b2body.getPosition().y <= 1.0262 && keyCount == 2)
        {
            keyCount++;
        }
        // // Level Four
        else if(player.b2body.getPosition().x >= 102.08 && player.b2body.getPosition().x <= 102.24 
        && player.b2body.getPosition().y >= 1.6697 && player.b2body.getPosition().y <= 1.8297 && keyCount == 3)
        {

            keyCount++;
        }
        // // Level Five
        else if(player.b2body.getPosition().x >= 127.84 && player.b2body.getPosition().x <= 128 
        && player.b2body.getPosition().y >= 0.3824 && player.b2body.getPosition().y <= 0.5424 && keyCount == 4)
        {
            keyCount++;
        }

        keycountLabel.setText(Integer.toString(keyCount));
        
        if(player.getState() == State.DEAD)
        {
                        lives = 0;
        }
                        lives = player.getLives();
                        livecountLabel.setText(Integer.toString(lives));

        if(worldTimer == 0)
        {
                        player.setState();
        }            
    }
    
    public int getKeycount()
    {
        return keyCount;
    }
    
    public float getTime() 
    {
        return timeCount;
    }
        @Override
    public void dispose()
    {
        stage.dispose();
    }
}