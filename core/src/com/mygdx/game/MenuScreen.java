package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class MenuScreen implements Screen 
{
    TalonPlatformer game;
    Texture background;
    TextureRegion myTextureRegion;
    TextureRegion drawable;
    ImageButton button;
    Stage stage;
    
    public MenuScreen(TalonPlatformer game)
    {
        this.game = game;

        Texture background = new Texture("background.jpeg");
        TextureRegion myTextureRegion = new TextureRegion(background);
        TextureRegion drawable = new TextureRegion(myTextureRegion);
        ImageButton button = new ImageButton(drawable);
        button.setPosition(0, 0);

        stage = new Stage(new ScreenViewport());
        stage.addActor(button);
        Gdx.input.setInputProcessor(stage); 
    }
    
    @Override
    public void render(float delta) 
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		dx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        batch.draw(background, 0, 0);

        game.batch.end();

        button.addListener(new EventListener()){
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
        }

        }
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
