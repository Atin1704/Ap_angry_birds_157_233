// Pig.java
package com.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.birds.BodyRemovalManager;

import java.io.Serializable;

public class Pig implements Serializable {
    public float health;
    public float xPos;
    public float yPos;
    public float width;
    public float height;
    public float radius;
    public boolean isSpriteNull=true;
    public float linearVelocityX;
    public float linearVelocityY;
    public boolean isAwake;
    public float initial_y;
    protected  transient Texture texture;
    protected  transient Sprite sprite;
    protected transient Body body;
    protected transient World world;
    protected transient BodyRemovalManager bodyRemovalManager;

    public Pig(World world, BodyRemovalManager bodyRemovalManager, String texturePath, float xPos, float yPos, float width, float height) {
        this.initial_y = yPos;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.world = world;
        this.bodyRemovalManager = bodyRemovalManager;

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

    // Pig.java
    public void update() {
        if (health <= 0 || (body != null && body.getPosition().y <= 253) || (body != null && (initial_y - body.getPosition().y) >= 200) ){

            if (world != null && body != null) {
                bodyRemovalManager.markForRemoval(body);
                body = null; // Set body to null after marking for removal
            }
            if (texture != null) {
                texture.dispose();
            }
            sprite = null;
            this.isSpriteNull = true;
        } else {
            if (body != null) {
                Vector2 bodyPosition = body.getPosition();
                sprite.setPosition(
                    bodyPosition.x - sprite.getWidth() / 2,
                    bodyPosition.y - sprite.getHeight() / 2
                );
                sprite.setRotation((float) Math.toDegrees(this.body.getAngle()));
                xPos=bodyPosition.x - sprite.getWidth() / 2;
                yPos=bodyPosition.y - sprite.getHeight() / 2;
                linearVelocityX=body.getLinearVelocity().x;
                linearVelocityY=body.getLinearVelocity().y;
                isAwake=body.isAwake();

            }
        }
    }

    public void draw(SpriteBatch batch) {
        if (sprite != null) {
            sprite.draw(batch);
        }
    }
}
