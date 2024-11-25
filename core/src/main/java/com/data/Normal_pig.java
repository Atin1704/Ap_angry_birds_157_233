// Normal_pig.java
package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Normal_pig extends Pig implements Serializable {
    private Texture image;
    private int health;

    public Normal_pig(World world, float xPos, float yPos, float width, float height) {
        super(world, "Normal_pig.png", xPos, yPos, width, height);
        this.image = new Texture("Normal_pig.png");
        this.health = 50;
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
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f; // Low restitution coefficient
        this.body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void update() {
        Vector2 bodyPosition = body.getPosition();
        sprite.setPosition(
            bodyPosition.x - sprite.getWidth() / 2,
            bodyPosition.y - sprite.getHeight() / 2
        );
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
