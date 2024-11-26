package com.birds;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;




import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class BodyRemovalManager {
    private final World world;
    private final List<Body> bodiesToRemove;

    public BodyRemovalManager(World world) {
        this.world = world;
        this.bodiesToRemove = new ArrayList<>();
    }

    public void markForRemoval(Body body) {
        bodiesToRemove.add(body);
    }

    public void removeMarkedBodies() {
        for (Body body : bodiesToRemove) {
            world.destroyBody(body);
        }
        bodiesToRemove.clear();
    }
}
