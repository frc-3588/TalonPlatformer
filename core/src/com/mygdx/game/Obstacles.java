package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Obstacles extends InteractiveTileObject{
    public Obstacles(World world, TiledMap map, Rectangle bounds)
    {
        super(world, map, bounds);
        fixture.setUserData(this);
    }
    
    @Override
    public void onHeadHit() 
    {
        PlayScreen.setContact(true);
    }
    
}