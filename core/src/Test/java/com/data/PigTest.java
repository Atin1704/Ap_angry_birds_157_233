package com.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//testing to see if normal pig is instantiate correctly
//other birds are a copy of normal pig so if normal pig is correct then the other pigs should be correct
class PigTest {

    @Test
    void testNormalPigInitialization() {
        float xPos = 5.0f;
        float yPos = 10.0f;
        float width = 2.0f;
        float height = 2.0f;
        boolean isAwake = false;
        float health=15.0f;
        Normal_pig normalPig = new Normal_pig(xPos, yPos, width, height, isAwake);

        assertEquals(xPos, normalPig.getXPos());
        assertEquals(yPos, normalPig.getYPos());
        assertEquals(width, normalPig.getWidth());
        assertEquals(height, normalPig.getHeight());
        assertEquals(isAwake, normalPig.isAwake);
        assertEquals(health, normalPig.getHealth());
    }
}
