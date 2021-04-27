package com.mygdx.game;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.TalonPlatformer;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map) {
        // Bodies and fixtures
         BodyDef bdef = new BodyDef();
         // Temporary
         PolygonShape shape = new PolygonShape();
         FixtureDef fdef = new FixtureDef();
         Body body;

         // Creating a body and fixture for every corresponding object in the each of the map layers

         // Create ground bodies/fixtures
         for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
             com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();
             
             bdef.type = BodyDef.BodyType.StaticBody;
             bdef.position.set((rect.getX() + rect.getWidth() / 2) / TalonPlatformer.PPM, (rect.getY() + rect.getHeight() / 2) / TalonPlatformer.PPM);
             
             body = world.createBody(bdef);
             
             shape.setAsBox(rect.getWidth() / 2 / TalonPlatformer.PPM, rect.getHeight() / 2 / TalonPlatformer.PPM);
             fdef.shape = shape;
             body.createFixture(fdef);
            }

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();
            
            new Obstacles(world, map, rect);
            
            //  bdef.type = BodyDef.BodyType.StaticBody;
            //  bdef.position.set((rect.getX() + rect.getWidth() / 2) / TalonPlatformer.PPM, (rect.getY() + rect.getHeight() / 2) / TalonPlatformer.PPM);
            
            //  body = world.createBody(bdef);
    
            //  shape.setAsBox(rect.getWidth() / 2 / TalonPlatformer.PPM, rect.getHeight() / 2 / TalonPlatformer.PPM);
            //  fdef.shape = shape;
            //  body.createFixture(fdef);
            
        }
    }
}