package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Glass_stick_ver extends Obstacle{
    private Texture image;
    private int health;

    public Glass_stick_ver(World world, float xPos, float yPos, float width, float height) {
        super(world, "Glass_Stick_Vertical.png", xPos, yPos, width, height);
        this.image = new Texture("Glass_Stick_Vertical.png");
        this.health = 10;
        this.sprite = new Sprite(image);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(xPos, yPos);
        this.sprite.setOriginCenter();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        this.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.9f;
        fixtureDef.restitution = 0.0f; // Low restitution coefficient
        this.body.createFixture(fixtureDef);
        shape.dispose();
        this.body.setAwake(false);
    }
}
