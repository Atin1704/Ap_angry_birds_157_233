package com.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;




//testing to see if black bird is instantiate correctly
//other birds are a copy of black bird so if black bird is correct then the other birds should be correct
class BirdTest {

    @Test
    void testBlackBirdConstructor() {

        float xPos = 5.0f;
        float yPos = 10.0f;
        float width = 2.0f;
        float height = 2.0f;
        float damage=10.0f;
        Black_bird blackBird = new Black_bird( xPos, yPos, width, height, false);
        assertEquals(10.0f, blackBird.getDamage());
        assertFalse(blackBird.isLaunched());
        assertEquals(xPos, blackBird.getX());
        assertEquals(yPos, blackBird.getY());
        assertEquals(width, blackBird.xSize);
        assertEquals(height, blackBird.ySize);
        assertEquals(0, blackBird.launchTime);
        assertEquals(damage, blackBird.damage);

    }
}
