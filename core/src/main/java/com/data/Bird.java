// Bird.java
package com.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.birds.CollisionHandler;

import java.io.Serializable;
import java.util.ArrayList;

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
    public boolean exploded = false;
    public boolean is_special = false;


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

    public void special_ability(ArrayList<Obstacle> obstacles, ArrayList<Pig> pigs, CollisionHandler collisionHandler){
        if(speedMultiplier == 1.0f){
            explode(obstacles, pigs, collisionHandler);
        }
        else if(speedMultiplier == 5.0f){
            big_size();
        }
    }


    public void explode(ArrayList<Obstacle> obstacles, ArrayList<Pig> pigs, CollisionHandler collisionHandler) {
        if (speedMultiplier != 1.0f || exploded)
            return;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBody() != null && body.getPosition().dst(obstacle.getBody().getPosition()) <= 150f) {
                obstacle.setHealth(obstacle.getHealth() - 70);
                if (obstacle.getHealth() <= 0) {
                    collisionHandler.removeObstacleFromWorld(obstacle);
                }
            }
        }
        for (Pig pig : pigs) {
            if (pig.getBody() != null && body.getPosition().dst(pig.getBody().getPosition()) <= 150f) {
                pig.setHealth(pig.getHealth() - 70);
                if (pig.getHealth() <= 0) {
                    collisionHandler.removePigFromWorld(pig);
                }
            }
        }
        exploded = true;
    }

    // Bird.java
    public void big_size() {
        if (exploded) {
            return;
        }

        // Double the size of the sprite
        sprite.setSize(sprite.getWidth() * 2, sprite.getHeight() * 2);

        // Adjust the position of the sprite to keep it centered
        sprite.setOriginCenter();
        sprite.setPosition(
            body.getPosition().x - sprite.getWidth() / 2,
            body.getPosition().y - sprite.getHeight() / 2
        );

        // Update the body's fixture to match the new size
        for (Fixture fixture : body.getFixtureList()) {
            Shape shape = fixture.getShape();
            if (shape instanceof CircleShape) {
                ((CircleShape) shape).setRadius(sprite.getWidth() / 2);
            }
        }

        exploded = true;
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

    public void set_is_special(boolean activated) {
        this.is_special = activated;
    }

    public boolean get_is_special() {
        return is_special;
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
