package com.birds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//to check whether levels aren't being instantiated correctly

class LevelTest {

    @Test
    void testLevel1ScreenInitialization() {
        // Call the last constructor of level_1_screen
        level_1_screen level = new level_1_screen();

        // Check that the birds ArrayList has size 3
        assertEquals(3, level.birds.size(), "Birds ArrayList should have size 3");

        // Check that the pigs ArrayList has size 2
        assertEquals(2, level.pigs.size(), "Pigs ArrayList should have size 2");

        // Check that the obstacles ArrayList has size 5
        assertEquals(5, level.obstacles.size(), "Obstacles ArrayList should have size 5");

        assertEquals(false, level.isDragging );

        assertEquals(false, level.allBirdsLaunchedTimerStarted);

        assertEquals(false, level.allBirdsLaunchedFlag);







    }
}
