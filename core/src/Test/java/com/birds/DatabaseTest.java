package com.birds;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
//testing to see if database is storing and retrieving levels correctly;
class DatabaseTest {

    @Test
    void testLevelStorageAndRetrieval() {
        // Create a Level instance
        Level level = new Level();

        // Add it to the stack
        Database.addLevel(level);

        // Store the stack
        Database.store();

        // Load the stack
        Database.load();

        // Verify if the level exists in the loaded stack
        Stack<Level> loadedStack = Database.getLevelStack();
        assertFalse(loadedStack.isEmpty());
        assertTrue(areLevelsEqual(level, loadedStack.peek()));
        loadedStack.pop();
    }

    private boolean areLevelsEqual(Level level1, Level level2) {
        return serialize(level1).equals(serialize(level2));
    }

    private String serialize(Level level) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(level);
            return bos.toString("ISO-8859-1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
