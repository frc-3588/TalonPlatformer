package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hud {
    //Instance Variables
    private int keyCount;
    private Player player;
    private int lives;
    private static int worldTimer;
    private Viewport viewport;
    private static float timeCount;
    private static Player playersIsDead;

    public Stage stage;
    
    //Labels that will be used for displaying the hud.
    private Label keyLabel;
    private Label livesLabel;
    private Label timeLabel;
    private static Label countdownLabel;
    private Label keycountLabel;
    private Label livecountLabel;


    //I need to establish a display for keycount, the timer, and the amount of lives.
    public Hud (Player player)
    {
            Table table = new Table();
            Table.top();
            table.setFillParent(true);
            viewport = new FitViewport(400, 208, new OrthographicCamera());
            stage = new Stage(viewport, sb);

            //Initializing of instance variables
            keyCount = 1;
            worldTimer = 300;
            this.player = player;
            lives = player.getLives();
            
            //Defining what the labels will display, what font and what color the labels will be
            keyLabel = new Label("KEYS", new Label.Labelstyle(new Bitmapfont(), Color.WHITE));
            livesLabel = new Label("LIVES", new Label.Labelstyle(new Bitmapfont(), Color.WHITE));
            timeLabel = new Label("TIME", new Label.Labelstyle(new Bitmapfont(), Color.WHITE));
            
            /*These labels are special, they are able to format the string into any digit integer.
            For example, 3 digits would be 001 for keycount as an example.*/
            countdownLabel = new Label("%03d", new Label.Labelstyle(new Bitmapfont(), Color.WHITE));
            keycountLabel = new Label("%01d", new Label.Labelstyle(new Bitmapfont(), Color.WHITE));
            livecountLabel = new Label("%01d", new Label.Labelstyle(new Bitmapfont(), Color.WHITE));
            

            
            //The table that is used to display the labels
            table.add(keyLabel).expandX().padTop(10);
            table.add(livesLabel).expandX().padTop(10);
            table.add(timeLabel).expandX().padTop(10);
            table.row();
            table.add(countdownLabel).expandX();
            table.add(keycountLabel).expandX();
            table.add(livecountLabel).expandX();
            stage.addactor(table);


    
    }
    public static void update(float dt){
            timeCount += dt;
            if(timeCount >= 1){
                    worldTimer --;
                    countdownLabel.setText(String.format("%03D", worldTimer));
                    timeCount = 0;
            }    }
            }
}