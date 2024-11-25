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
        fixtureDef.restitution = 0.6f;
        this.body.createFixture(fixtureDef);
        shape.dispose();

        this.damage = 0;
        this.speedMultiplier = 1.0;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = width;
        this.ySize = height;
        this.launchTime = 0;
        this.isLaunched = false;
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

    public void launch(Vector2 force) {
        this.isLaunched = true;
        this.launchTime = System.currentTimeMillis();
        applyForce(force);
    }
}
