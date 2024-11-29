// Bird.java
package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public abstract class Bird implements Serializable {
    protected  transient Texture texture;
    protected transient Body body;
    protected  transient Sprite sprite;
    protected transient World world;
    protected transient float damage;
    public  transient float  speedMultiplier;
    public transient float launchTime=0;
    public float xPos;
    public float yPos;
    public float xSize;
    public float ySize;
    public boolean isLaunched;
    public float linearVelocityX;
    public float linearVelocityY;
    public boolean isAwake;


    public Bird(World world, String texturePath, float xPos, float yPos, float width, float height) {
        this.world = world;
        this.texture = new Texture(texturePath);
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(xPos, yPos);
        this.sprite.setOriginCenter();
        this.isLaunched = false;

    }

    public Bird(  float xPos, float yPos, float width, float height) {

        this.isLaunched = false;
        this.xPos=xPos;
        this.yPos=yPos;
        this.xSize=width;
        this.ySize=height;

    }



    public void update() {
        Vector2 bodyPosition = body.getPosition();
        sprite.setPosition(
            bodyPosition.x - sprite.getWidth() / 2,
            bodyPosition.y - sprite.getHeight() / 2
        );
        xPos=bodyPosition.x; //- sprite.getWidth() / 2;
        yPos=bodyPosition.y; //- sprite.getHeight() / 2;

        linearVelocityX=body.getLinearVelocity().x;
        linearVelocityY=body.getLinearVelocity().y;



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
        this.isAwake=awake;
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
        return xPos;
    }

    public float getY() {
        return yPos;
    }

    public Body getBody() {
        return body;
    }

    public float getDamage() {
        return damage;
    }
}
