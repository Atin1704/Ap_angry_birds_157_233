package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.birds.BodyRemovalManager;

import java.io.Serializable;

public class Stone_stick_vert extends  Obstacle implements Serializable {
    private Texture image;

    public Stone_stick_vert(World world, BodyRemovalManager brm, float xPos, float yPos, float width, float height) {
        super(world, brm, "Stone_Stick_Vertical.png", xPos, yPos, width, height);
        this.image = new Texture("Stone_Stick_Vertical.png");
        this.health = 10.0f;
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
        this.body.setUserData(this);
    }
}
