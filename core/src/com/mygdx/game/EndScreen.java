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
- Either load a win or lose image for the background depending on what happened in the play screen
- Loaded image for play again button
- Switch to the play screen when the button is clicked (both computers and touch screens)
*/
public class EndScreen implements Screen {
    TalonPlatformer game;

    float rectX = 300;
    float rectY = 150;
    float width = 100;
    float height = 75;

    public EndScreen(TalonPlatformer game) {
        this.game = game;
    }
    @Override
    public void show() 
    {
        /*Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.ENTER) {
                    game.setScreen(new MenuScreen(game));
                }

                return true;
            }
        });
        */
        //new
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                int renderY = Gdx.graphics.getHeight() - y;
                if (Vector2.dst(rectX, rectY, x, renderY) < 50) {//find some variable to call "50"
                    game.setScreen(new MenuScreen(game));
                }
                return true;
            }
        });

    }

    @Override
    public void render(float delta) 
    {
     Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "You win!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Press this button to restart.", rectX, rectY);

       //new 

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(58, 29, 0, 1); //brown is (58, 29, 0)
        game.shapeRenderer.rect(rectX, rectY, width, height);
        game.shapeRenderer.end();
        //
        
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
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void dispose() 
    {
    

    }
    
}