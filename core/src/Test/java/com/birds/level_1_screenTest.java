package com.birds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class level_1_screenTest {

    @Test
    void testLevel1ScreenInstantiation() {
        level_1_screenTest screenTest = new level_1_screenTest();
        assertNotNull(screenTest, "level_1_screenTest should be instantiated");
    }
}
