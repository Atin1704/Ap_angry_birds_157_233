// Bird.java
package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bird {
    protected Texture texture;
    protected Body body;
    protected Sprite sprite;
    protected World world;
    protected double damage;
    public double speedMultiplier;
    protected float xPos;
    protected float yPos;
    protected float xSize;
    protected float ySize;
    protected float launchTime;
    protected boolean isLaunched;

    public Bird(World world, String texturePath, float xPos, float yPos, float width, float height) {
        this.world = world;
        this.texture = new Texture(texturePath);
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(xPos, yPos);
        this.sprite.setOriginCenter();
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

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
        body.setTransform(x, y, body.getAngle());
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public Body getBody() {
        return body;
    }

    public void applyForce(Vector2 force) {
        if (body.getType() != BodyDef.BodyType.DynamicBody) {
            body.setType(BodyDef.BodyType.DynamicBody);
        }
        body.applyLinearImpulse(force, body.getWorldCenter(), true);
    }

    public void setIsLaunched(boolean isLaunched) {
        this.isLaunched = isLaunched;
    }

    // Method to apply force and launch the bird
    public void launch(Vector2 force) {
        if (!isLaunched) {
            getBody().applyLinearImpulse(force, getBody().getWorldCenter(), true);
            setIsLaunched(true);
        }
    }

    public boolean isLaunched() {
        return isLaunched;
    }
}
