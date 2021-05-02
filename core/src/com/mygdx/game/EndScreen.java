package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;

//new
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/* Things to be done:
End Screen
- make end screen look niceer
- Switch to the play screen when the button is clicked (both computers and touch screens) - bug
*/
public class EndScreen implements Screen {
    TalonPlatformer game;
    OrthographicCamera camera;
    Music music;
    Player player;

    public EndScreen(TalonPlatformer game, Player player) {
        this.game = game;
        this.player = player;

        // playerLost = Player.isPlayerDead();//state.get smthg
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        music = Gdx.audio.newMusic(Gdx.files.internal("violin_theme.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) 
    {
        //4.24.2021 new code

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "Click anywhere to RESTART", 250, 150);//this should appear inside the box
        game.batch.end();

        game.batch.begin();
        // if(Player.getState() == State.DEAD){
            //game.font.draw(game.batch, "Game Over", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        // }else{
            // game.font.draw(game.batch, "You win!", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // }
        game.batch.end();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {
            if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
                game.setScreen(new MenuScreen(game));
                dispose();
            }
            return true;
            }

            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                dispose();
                return true;
            }
        });
        
    }

    @Override
    public void resize(int width, int height) 
    {
    

    }

    @Override
    public void pause() 
    {
    

    }

    @Override
    public void resume() 
    {
    

    }

    @Override
    public void hide() 
    {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() 
    {
        music.dispose();
    }
    
}