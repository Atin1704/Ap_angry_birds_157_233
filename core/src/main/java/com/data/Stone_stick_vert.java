package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.birds.BodyRemovalManager;

import java.io.Serializable;

public class Stone_stick_vert extends  Obstacle implements Serializable {
    private transient Texture image;

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

    public Stone_stick_vert(World world, BodyRemovalManager bodyRemovalManager, Stone_stick_vert block) {
        super(world, bodyRemovalManager, "Stone_Stick_Vertical.png", block.xPos,block.yPos, block.width, block.height);
        this.isSpriteNull = block.isSpriteNull;
        if(!this.isSpriteNull) {
            this.image = new Texture("Stone_Stick_Vertical.png");
            this.health = block.health;
            this.sprite = new Sprite(image);
            this.sprite.setSize(block.width, block.height);
            this.sprite.setPosition(block.xPos, block.yPos);
            this.sprite.setOriginCenter();

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(block.xPos, block.yPos);
            this.body = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(block.width / 2, block.height / 2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 0.9f;
            fixtureDef.restitution = 0.0f; // Low restitution coefficient
            this.body.createFixture(fixtureDef);
            shape.dispose();
            this.body.setAwake(block.isAwake);
            this.body.setUserData(this);

        }

    }
}
