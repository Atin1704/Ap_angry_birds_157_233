package com.birds;

import java.io.*;
import java.util.Stack;

public class Database {
    private static final String FILE_PATH = "data8.ser"; // Adjust file path as needed
    private static Stack<Level> levelStack = new Stack<>();

    // Load the stack from the file, if null, initialize it
    public static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(FILE_PATH)))) {
            levelStack = (Stack<Level>) ois.readObject();
            System.out.println("Data loaded successfully.");
            System.out.println(levelStack.size());
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found. Initializing with empty data.");
            levelStack = new Stack<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Save the stack to the file
    public static void store() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(FILE_PATH)))) {
            oos.writeObject(levelStack);
            System.out.println("Data stored successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter for the stack
    public static Stack<Level> getLevelStack() {
        System.out.println("Level stack retrieved.");
        return levelStack;

    }

    // Setter for the stack
    public static void setLevelStack(Stack<Level> stack) {
        levelStack = stack;
    }

    // Method for adding data to the stack
    public static void addLevel(Level level) {
        levelStack.push(level);
        System.out.println("Level added to stack.");
        System.out.println(levelStack.size());
    }
}
