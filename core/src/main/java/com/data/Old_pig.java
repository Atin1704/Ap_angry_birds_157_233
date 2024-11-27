// Normal_pig.java
package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.birds.BodyRemovalManager;

import java.io.Serializable;

public class Old_pig extends Pig implements Serializable {
    private transient Texture image;

    public Old_pig(World world, BodyRemovalManager bodyRemovalManager, float xPos, float yPos, float width, float height) {
        super(world, bodyRemovalManager, "Old_pig.png", xPos, yPos, width, height);
        this.image = new Texture("Old_pig.png");
        this.health = 30.0f;
        this.sprite = new Sprite(image);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(xPos, yPos);
        this.sprite.setOriginCenter();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        this.body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2);

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
    public Old_pig(World world, BodyRemovalManager bodyRemovalManager, Old_pig pig) {
        super(world, bodyRemovalManager, "Old_pig.png", pig.xPos, pig.yPos, pig.width, pig.height);
        this.isSpriteNull = pig.isSpriteNull;
        if(!this.isSpriteNull) {
            this.image = new Texture("Old_pig.png");
            this.health = pig.health;
            this.sprite = new Sprite(image);
            this.sprite.setSize(pig.width, pig.height);
            this.sprite.setPosition(pig.xPos, pig.yPos);
            this.sprite.setOriginCenter();

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(pig.xPos, pig.yPos);
            this.body = world.createBody(bodyDef);

            CircleShape shape = new CircleShape();
            shape.setRadius(pig.width / 2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 0.9f;
            fixtureDef.restitution = 0.0f; // Low restitution coefficient
            this.body.createFixture(fixtureDef);
            shape.dispose();
            this.body.setAwake(pig.isAwake);
            this.body.setUserData(this);

        }

    }

}
