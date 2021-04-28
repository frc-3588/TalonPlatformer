package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class MenuScreen implements Screen 
{
    TalonPlatformer game;
    Texture background;
    
    public MenuScreen(TalonPlatformer game)
    {
        this.game = game;
        Texture background = new Texture("Menu.jpg");
    }
    
    @Override
    public void render(float delta) 
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isTouched()){
            game.setScreen(new PlayScreen(game));
        }

        game.batch.begin();
        batch.draw(background, 0, 0);

        game.batch.end();
    }

    @Override
    public void dispose() 
    {
        background.dispose();
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
    public void show() 
    {   

    }
}
