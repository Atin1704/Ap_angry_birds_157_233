package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Red_bird extends Bird {
    public Red_bird(World world, float xPos, float yPos) {
        super(world, xPos, yPos);
        this.texture = new Texture("Red_bird.png");
        this.sprite = new Sprite(texture);
        this.damage = 10.0;
        this.speedMultiplier = 1.0;
        this.xSize = texture.getWidth();
        this.ySize = texture.getHeight();
        this.sprite.setSize(xSize, ySize);
        this.sprite.setPosition(xPos, yPos);
    }

    @Override
    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Ensure bird is a dynamic body
        bodyDef.position.set(xPos, yPos);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(xSize / 2, ySize / 2); // Ensure the dimensions are correctly set

        body.createFixture(shape, 1.0f);
        shape.dispose();
    }
}
