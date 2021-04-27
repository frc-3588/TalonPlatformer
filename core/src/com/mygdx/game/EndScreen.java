package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;

//new
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/* Things to be done:
End Screen
- Loaded image for play again button - done
- Switch to the play screen when the button is clicked (both computers and touch screens)-done
*/
public class EndScreen implements Screen {
    TalonPlatformer game;

    float rectX = 300;
    float rectY = 150;
    float width = 100;
    float height = 75;

    Texture texture = new Texture("Texture.png");

    public EndScreen(TalonPlatformer game) {
        this.game = game;

        playerLost = Player.isPlayerDead();//state.get smthg
    }
    @Override
    public void show() 
    {
        //new
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                int renderY = Gdx.graphics.getHeight() - y;
                if (Vector2.dst(rectX, rectY, x, renderY) < 50) {
                    game.setScreen(new MenuScreen(game));
                }
                return true;
            }
        });

    }

    @Override
    public void render(float delta) 
    {
        //4.24.2021 new code

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		dx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        batch.draw(background, 0, 0);
        game.batch.end();

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(58, 29, 0, 1);
        game.shapeRenderer.rect(rectX, rectY, width, height);
        game.shapeRenderer.end();

        game.batch.begin();
        if(Player.getState() == State.DEAD){
            game.font.draw(game.batch, "Game Over", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);

        }else{
            game.font.draw(game.batch, "You win!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);

        }
        game.font.draw(game.batch, "Press this button to restart.", rectX, rectY);
        game.batch.end();
        
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

    }

    @Override
    public void dispose() 
    {
        background.dispose();
    }
    
}