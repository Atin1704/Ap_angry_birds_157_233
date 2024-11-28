package com.data;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//testing to see if glass bird is instantiate correctly
//other obstacles are a copy of glass block so if glass block is correct then the other obstacles should be correct
class ObstacleTest {

    @Test
    void testGlassBlockInitialization() {
        float xPos = 5.0f;
        float yPos = 10.0f;
        float width = 2.0f;
        float height = 2.0f;
        boolean isAwake = false;
        float health=15.0f;

        Glass_block glassBlock = new Glass_block(xPos, yPos, width, height, isAwake);

        assertEquals(xPos, glassBlock.getXPos());
        assertEquals(yPos, glassBlock.getYPos());
        assertEquals(width, glassBlock.getWidth());
        assertEquals(height, glassBlock.getHeight());
        assertEquals(isAwake, glassBlock.isAwake);
        assertEquals(health, glassBlock.getHealth());
    }
}
