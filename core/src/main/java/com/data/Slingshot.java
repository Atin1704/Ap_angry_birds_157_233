package com.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Slingshot {
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private Texture image;
    private Sprite sprite;
    private Body body;
    private float xPos;
    private float yPos;
    private float radius;

    public Slingshot(World world, SpriteBatch spriteBatch, AssetManager assetManager, float xPos, float yPos) {
        this.spriteBatch = spriteBatch;
        this.assetManager = assetManager;
        this.xPos = xPos;
        this.yPos = yPos;
        this.image = assetManager.get("Slingshot.png", Texture.class);
        this.sprite = new Sprite(image);
        this.radius = Math.max(image.getWidth(), image.getHeight()) / 2f;
        this.sprite.setSize(image.getWidth(), image.getHeight());
        this.sprite.setPosition(xPos - radius, yPos - radius);
        createBody(world);
    }

    private void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(xPos, yPos);
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        body.createFixture(shape, 0.0f);
        shape.dispose();
    }

    public void render() {
        sprite.draw(spriteBatch);
    }

    public Texture getImage() {
        return image;
    }

    public SpriteBatch getBatch() {
        return spriteBatch;
    }

    public Body getBody() {
        return body;
    }
}
