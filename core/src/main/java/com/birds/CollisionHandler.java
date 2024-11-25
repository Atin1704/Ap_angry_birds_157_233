package com.birds;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionHandler implements ContactListener {
    //need to handle collisions between birds, pigs and obstacles
    //Birds and pigs hsould collide and bird's damage should be subtracted from blocks,
    //those blocks which have zero helath hsould stop renderinh
    //similarly for pigs birds
    //birds health hsould
    //if the birds velocitybis less thna acertain velocity it hsould be removed as its damage baility ouwld be affcted
    //if baird collides with the gound or nay of its fourd edge sit dies
    //similar for any obstacle or pig
    //pigs and blocks would only collide with eachother if the velocity difference between them is substantial
    //if the velocity difference is less than a certain value they would not collide
    //if the bird collides with the ground or any of its four edges it dies
    //two birds wont collide with each other
    //if one bird.body(is not awke) and it affects



    @Override
    public void beginContact(Contact contact) {
        System.out.println("Collision detected");
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("Collision ended");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
