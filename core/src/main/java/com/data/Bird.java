package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Bird {
    protected Texture texture;
    protected Body body;
    protected Sprite sprite;
    protected double damage;
    protected double speedMultiplier;
    protected float xPos;
    protected float yPos;
    protected float xSize;
    protected float ySize;
    protected float launchTime;
    protected boolean isLaunched;

    public Bird(World world, float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isLaunched = false;
        createBody(world);
    }

    public abstract void createBody(World world);

    public void update() {
        if (body != null) {
            xPos = body.getPosition().x;
            yPos = body.getPosition().y;
            sprite.setPosition(xPos - xSize / 2, yPos - ySize / 2);
        }
    }

    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    // Getters and setters
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
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

    public float getXSize() {
        return xSize;
    }

    public void setXSize(float xSize) {
        this.xSize = xSize;
    }

    public float getYSize() {
        return ySize;
    }

    public void setYSize(float ySize) {
        this.ySize = ySize;
    }

    public float getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(float launchTime) {
        this.launchTime = launchTime;
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public void setLaunched(boolean launched) {
        isLaunched = launched;
    }
}
