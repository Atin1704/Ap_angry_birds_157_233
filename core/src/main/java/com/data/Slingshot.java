// Slingshot.java
package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Slingshot {
    private Texture texture;
    private Sprite sprite;
    private float x;
    private float y;
    private float width;
    private float height;

    public Slingshot(World world, float x, float y, float width, float height) {
        this.texture = new Texture("Slingshot.png");
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(x, y);
        this.sprite.setOriginCenter();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drag(float x, float y) {
        // Implement dragging logic if needed
    }

    public void update() {
        // Implement update logic if needed
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
