// CollisionHandler.java
package com.birds;

import com.badlogic.gdx.physics.box2d.*;
import com.data.Bird;
import com.data.Obstacle;
import com.data.Pig;



import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionHandler implements ContactListener {
    private static final float VELOCITY_THRESHOLD = 1.0f;
    private static final float BLOCK_DAMAGE_MULTIPLIER = 1.0f;
    private static final float BIRD_DAMAGE_MULTIPLIER = 0.5f;
    private static final float MAX_DAMAGE = 1.0f;
    private static boolean damaageEnabled = false;

    public void enableDamage() {
        damaageEnabled = true;
    }

    @Override
    public void beginContact(Contact contact) {
        if(!damaageEnabled)
            return;

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        Vector2 velA =  bodyA.getLinearVelocity();
        Vector2 velB =  bodyB.getLinearVelocity();

        if(velA.len() < VELOCITY_THRESHOLD && velB.len() < VELOCITY_THRESHOLD)
            return;

        Object userDataA = bodyA.getUserData();
        Object userDataB = bodyB.getUserData();

        System.out.println("Collision between " + userDataA.getClass().getName() + " and " + userDataB.getClass().getName());

        if(userDataA instanceof Pig) {
            handlePigCollision((Pig) userDataA, userDataB, bodyA, bodyB);
        }
        else if(userDataB instanceof Pig) {
            handlePigCollision((Pig) userDataB, userDataA, bodyB, bodyA);
        }
    }

    private void handlePigCollision(Pig pig, Object otherObject, Body pigBody, Body otherBody) {
        float impactForce = calculateImpactForce(pigBody, otherBody);
        System.out.println("Impact force: " + impactForce);

        if(impactForce < BIRD_DAMAGE_MULTIPLIER)
            return;

        float damage = 0;
        if(otherObject instanceof Bird) {
            damage = Math.min(impactForce*BIRD_DAMAGE_MULTIPLIER, MAX_DAMAGE);
            System.out.println("Bird hit pig - current health : " + pig.getHealth()+ " damage: " + damage);
        } else if (otherObject instanceof Obstacle) {
            damage = Math.min(impactForce*BLOCK_DAMAGE_MULTIPLIER, MAX_DAMAGE);
            System.out.println("Block hit pig - current health : " + pig.getHealth() + " damage: " + damage);
        }

        if(damage > 0)
        {
            pig.setHealth(pig.getHealth() - damage);
            System.out.println("Pig health after damage: " + pig.getHealth());
        }
    }

    private float calculateImpactForce(Body bodyA, Body bodyB)
    {
        Vector2 velA = bodyA.getLinearVelocity();
        Vector2 velB = bodyB.getLinearVelocity();

        Vector2 relativeVelocity = new Vector2(velA.x - velB.x, velA.y - velB.y);
        float impactSpeed = relativeVelocity.len();

        if(impactSpeed < VELOCITY_THRESHOLD)
            return 0;

        float massA = bodyA.getMass();
        float massB = bodyB.getMass();
        float scaledImpact = (impactSpeed*Math.min(massA, massB))*0.1f;

        return Math.min(scaledImpact, 100.0f);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}



