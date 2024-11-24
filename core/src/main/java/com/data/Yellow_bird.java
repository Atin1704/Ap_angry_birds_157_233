package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Yellow_bird extends Bird {
    public Yellow_bird(World world, float xPos, float yPos, float xSize, float ySize) {
        super(world, xPos, yPos, xSize, ySize);
        this.texture = new Texture("Yellow_bird.png");
        this.sprite = new Sprite(texture);
        this.damage = 10.0;
        this.speedMultiplier = 2.0; // Ensure speed multiplier is set
        this.sprite.setSize(xSize, ySize);
        this.sprite.setPosition(xPos, yPos);
    }

    @Override
    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(Math.max(xSize, ySize) / 2);

        body.createFixture(shape, 1.0f);
        shape.dispose();
    }
}
