package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TalonPlatformer extends Game {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    public BitmapFont font;

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;
    // public static AssetManager manager;

    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        // manager = new AssetManager();
        // manager.load("8bit_trial_and_demo.mp3", Music.class);
        // manager.load("clockwork_chamber_demo.mp3", Music.class);
        // manager.finishLoading();
        font = new BitmapFont();
        setScreen(new MenuScreen(this));
        //setScreen(new PlayScreen(this));
    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }
}