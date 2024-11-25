package com.data;

import com.badlogic.gdx.physics.box2d.World;

public class Black_bird extends Bird {
    public double speedMultiplier=1;
    public Black_bird(World world, float xPos, float yPos, float width, float height) {
        super(world, "Black_bird.png", xPos, yPos, width, height);
    }
}
