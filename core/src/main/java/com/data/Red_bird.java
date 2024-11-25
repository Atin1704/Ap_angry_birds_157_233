package com.data;

import com.badlogic.gdx.physics.box2d.World;

public class Red_bird extends Bird {
    public double speedMultiplier=200000;
    public Red_bird(World world, float xPos, float yPos, float width, float height) {
        super(world, "Red_bird.png", xPos, yPos, width, height);
    }
}
