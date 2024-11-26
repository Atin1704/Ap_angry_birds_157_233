// Pig.java
package com.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

public class Pig implements Serializable {
    protected float health;
    protected float xPos;
    protected float yPos;
    protected float width;
    protected float height;
    protected Texture texture;
    protected Sprite sprite;
    protected Body body;
    protected World world;
    protected float radius;

    public Pig(World world, String texturePath, float xPos, float yPos, float width, float height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.health = 100;
        texture = new Texture(texturePath);
        sprite = new Sprite(texture);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void update() {
        if (health <= 0) {
            world.destroyBody(body);
            texture.dispose();
            sprite = null;
            body = null;
        } else {
            Vector2 bodyPosition = body.getPosition();
            sprite.setPosition(
                bodyPosition.x - sprite.getWidth() / 2,
                bodyPosition.y - sprite.getHeight() / 2
            );
            sprite.setRotation((float) Math.toDegrees(this.body.getAngle()));
        }
    }

    public void draw(SpriteBatch batch) {
        if (sprite != null) {
            sprite.draw(batch);
        }
    }
}
