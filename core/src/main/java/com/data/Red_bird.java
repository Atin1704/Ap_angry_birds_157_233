package com.data;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Red_bird extends Bird {
    public double speedMultiplier=200000;
    public Red_bird(World world, float xPos, float yPos, float width, float height) {
        super(world, "Red_bird.png", xPos, yPos, width, height);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        this.body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.5f;
        fixtureDef.friction = 0.9f;
        fixtureDef.restitution = 0.0f;
        this.body.createFixture(fixtureDef);
        shape.dispose();

        this.damage = 60;
        this.speedMultiplier = 1.3f;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = width;
        this.ySize = height;
        this.launchTime = 0;
        this.isLaunched = false;
        this.body.setAwake(false);
    }
}
