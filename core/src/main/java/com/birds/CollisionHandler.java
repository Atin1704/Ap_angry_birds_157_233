// CollisionHandler.java
package com.birds;

import com.badlogic.gdx.physics.box2d.*;
import com.data.Bird;
import com.data.Obstacle;
import com.data.Pig;
import com.badlogic.gdx.math.Vector2;

public class CollisionHandler implements ContactListener {
    private static final float VELOCITY_THRESHOLD = 3.0f;
    private static final float BLOCK_DAMAGE_MULTIPLIER = 8f;
    private static final float BIRD_DAMAGE_MULTIPLIER = 7.5f;
    private static final float MAX_DAMAGE = 10.00f;
    private static boolean damageEnabled = true;
    private final Body groundBody;

    public CollisionHandler(Body groundBody) {
        this.groundBody = groundBody;
    }

    public void enableDamage() {
        damageEnabled = true;
    }

    @Override
    public void beginContact(Contact contact) {
        if (!damageEnabled)
            return;

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        Vector2 velA = bodyA.getLinearVelocity();
        Vector2 velB = bodyB.getLinearVelocity();

        if (velA.len() < VELOCITY_THRESHOLD && velB.len() < VELOCITY_THRESHOLD)
            return;

        Object userDataA = bodyA.getUserData();
        Object userDataB = bodyB.getUserData();

        if (userDataA instanceof Pig) {
            if (userDataB instanceof Obstacle) {
                handlePigCollision((Pig) userDataA, userDataB, bodyA, bodyB);
            }
        } else if (userDataB instanceof Pig) {
            if (userDataA instanceof Obstacle) {
                handlePigCollision((Pig) userDataB, userDataA, bodyB, bodyA);
            }
        } else if (userDataA instanceof Obstacle) {
            handleBlockCollision((Obstacle) userDataA, userDataB, bodyA, bodyB);
        } else if (userDataB instanceof Obstacle) {
            handleBlockCollision((Obstacle) userDataB, userDataA, bodyB, bodyA);
        }
    }

    private void handlePigCollision(Pig pig, Object otherObject, Body pigBody, Body otherBody) {
        float impactForce = calculateImpactForce(pigBody, otherBody);

        if (impactForce < VELOCITY_THRESHOLD)
            return;

        float damage = 0;
        if (otherObject instanceof Bird) {
            damage = Math.min(impactForce * BIRD_DAMAGE_MULTIPLIER, MAX_DAMAGE);
        } else if (otherObject instanceof Obstacle) {
            damage = Math.min(impactForce * BLOCK_DAMAGE_MULTIPLIER, MAX_DAMAGE);
        }

        if (damage > 0) {
            pig.setHealth(pig.getHealth() - damage);
            if (pig.getHealth() <= 0) {
                pig.update(); // This will handle the removal of the pig
                removePigFromWorld(pig);
            }
        }
    }



    private void handleBlockCollision(Obstacle obstacle, Object otherObject, Body obstacleBody, Body otherBody) {
        float impactForce = calculateImpactForce(obstacleBody, otherBody);

        if (impactForce < VELOCITY_THRESHOLD)
            return;

        float damage = 0;
        if (otherObject instanceof Bird) {
            damage = Math.min(impactForce * BIRD_DAMAGE_MULTIPLIER, MAX_DAMAGE);
        } else if (otherObject instanceof Pig) {
            damage = Math.min(impactForce * BLOCK_DAMAGE_MULTIPLIER, MAX_DAMAGE);
        }

        if (damage > 0) {
            obstacle.setHealth(obstacle.getHealth() - damage);
            if (obstacle.getHealth() <= 0) {
                System.out.println("Obstacle Destroyed.");
                obstacle.update(); // This will handle the removal of the obstacle
                removeObstacleFromWorld(obstacle);
            }
        }
    }

    // CollisionHandler.java
    private void removeObstacleFromWorld(Obstacle obstacle) {
        if (obstacle.getWorld() != null && obstacle.getBody() != null) {
            obstacle.getWorld().destroyBody(obstacle.getBody());
        }
        if (obstacle.getTexture() != null) {
            obstacle.getTexture().dispose();
        }
        obstacle.setSprite(null);
        obstacle.setBody(null);
    }

    private void removePigFromWorld(Pig pig) {
        if (pig.getWorld() != null && pig.getBody() != null) {
            pig.getWorld().destroyBody(pig.getBody());
        }
        if (pig.getTexture() != null) {
            pig.getTexture().dispose();
        }
        pig.setSprite(null);
        pig.setBody(null);
    }

    private float calculateImpactForce(Body bodyA, Body bodyB) {
        Vector2 velA = bodyA.getLinearVelocity();
        Vector2 velB = bodyB.getLinearVelocity();

        Vector2 relativeVelocity = new Vector2(velA.x - velB.x, velA.y - velB.y);
        float impactSpeed = relativeVelocity.len();

        if (impactSpeed < VELOCITY_THRESHOLD)
            return 0;

        float massA = bodyA.getMass();
        float massB = bodyB.getMass();
        float scaledImpact = (impactSpeed * Math.min(massA, massB)) * 0.1f;

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
