package com.mygdx.game;

import uk.co.carelesslabs.Control;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.world; // Box2D is a physics library

public class World {
    // The instance of the world, the world object holds all of the physics objects/bodies
    //  and simulates the reactions between them. It does not, however, render the objects.
    public World world;

    // Used to render objects which would be invisible
    private WorldDebugRenderer debugRenderer;

    public World() {
        // Initializing a world with no gravity
        // world = new World(new Vector2(.0f, .0f), true);

        // Initializing a world with gravity
        world = new World(new Vector2(0, -10), true); // 0 horizontal gravity and -10 vertical gravity

        // Initialize a debug renderer *For testing purposes and not for the released version*
        debugRenderer = new Box2DDebugRenderer();
    }

    public void tick(OrthographicCamera camera, Control control) {
        if (control.debug) debugRenderer.render(world, camera.combined);

        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
        world.clearForces();
    }
}