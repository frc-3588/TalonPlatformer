package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TalonPlatformer extends Game {
	SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		setScreen(new MenuScreen(this));
	}
	
	@Override
	public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
	}
}
