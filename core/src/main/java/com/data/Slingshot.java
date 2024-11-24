package com.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Slingshot {
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private Texture image;
    private Sprite sprite;
    private Body body;
    private float xPos;
    private float yPos;
    private float width;
    private float height;

    public Slingshot(World world, SpriteBatch spriteBatch, AssetManager assetManager, float xPos, float yPos, float width, float height) {
        this.spriteBatch = spriteBatch;
        this.assetManager = assetManager;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.image = assetManager.get("Slingshot.png", Texture.class);
        this.sprite = new Sprite(image);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(xPos - width / 2, yPos - height / 2);
        createBody(world);
    }

    private void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(xPos, yPos - height / 2); // Adjust position to ensure it is above the ground
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

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
