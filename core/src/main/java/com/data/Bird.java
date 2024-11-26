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
    protected float damage;
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
        this.isLaunched = false;

    }

    public void update() {
        Vector2 bodyPosition = body.getPosition();
        sprite.setPosition(
            bodyPosition.x - sprite.getWidth() / 2,
            bodyPosition.y - sprite.getHeight() / 2
        );
        sprite.setRotation((float) Math.toDegrees(this.body.getAngle()));
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
        body.setTransform(x, y, body.getAngle());
    }

    public void setVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity);
    }

    public void setAwake(boolean awake) {
        body.setAwake(awake);
    }

    public void setGravityScale(float scale) {
        body.setGravityScale(scale);
    }

    public void setLaunched(boolean launched) {
        this.isLaunched = launched;
    }

    public boolean isLaunched() {
        return isLaunched;
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

    public float getDamage() {
        return damage;
    }
}
