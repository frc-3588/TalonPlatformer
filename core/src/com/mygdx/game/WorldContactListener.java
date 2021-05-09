package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        // TODO Auto-generated method stub
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head")
        {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

             if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
             {
                 ((InteractiveTileObject) object.getUserData()).onHeadHit();
             }
        }

        if(fixA.getUserData() == "feet" || fixB.getUserData() == "feet")
        {
            Fixture head = fixA.getUserData() == "feet" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

             if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
             {
                 ((InteractiveTileObject) object.getUserData()).onHeadHit();
             }
        }
    }

    @Override
    public void endContact(Contact contact) {
        // TODO Auto-generated method stub
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub
        
    }
    
}