package com.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class main_screen implements Screen {
    Vector2 touchPos;
    private final AssetManager assetManager;
    private Texture background_image;
    private Texture Settings_icon;
    private Texture Level1;
    private Texture Level2;
    private Texture Level3;
    private Texture Saved_game;
    private Texture exit_icon;
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    FitViewport viewport;

    public main_screen(Main main,AssetManager assetManager) {
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch=main.batch;
        viewport = new FitViewport(100, 100);
        touchPos = new Vector2();

    }

    @Override
    public void show() {
        background_image = assetManager.get("main_screen_bg.png", Texture.class);
        Settings_icon = assetManager.get("Settings_icon.png", Texture.class);
        Level1 = assetManager.get("Level_1.png", Texture.class);
        Level2 = assetManager.get("Level_2.png", Texture.class);
        Level3 = assetManager.get("Level_3.png", Texture.class);
        Saved_game = assetManager.get("Saved_game_icon.png", Texture.class);
        exit_icon = assetManager.get("Exit_icon.png", Texture.class);
    }

    @Override
    public void render(float v) {
        input();
        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();


        spriteBatch.draw(background_image, 0, 0, worldWidth, worldHeight);
        spriteBatch.draw(Settings_icon, 10, 3, worldWidth/10, worldHeight/12);
        spriteBatch.draw(Level1, 33,41 , worldWidth/3, worldHeight/10);
        spriteBatch.draw(Level2, 33, 28, worldWidth/3, worldHeight/10);
        spriteBatch.draw(Level3, 33, 15, worldWidth/3, worldHeight/10);
        spriteBatch.draw(Saved_game, 33, 54, worldWidth/3, worldHeight/10);
        spriteBatch.draw(exit_icon, 80, 3, worldWidth/8, worldHeight/12);

        spriteBatch.end();

    }

    private void input() {
        if (Gdx.input.isTouched()) {
            // Get the touch position in screen coordinates and convert it to world coordinates
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos); // Converts screen coordinates to the viewport's world coordinates (100x100 system)

            // Define the coordinates and sizes for the Settings and Exit icons in the 100x100 world
            float settingsIconX = 10f;    // X position for Settings icon
            float settingsIconY = 3f;     // Y position for Settings icon
            float settingsIconWidth = viewport.getWorldWidth() / 10; // Width of the Settings icon
            float settingsIconHeight = viewport.getWorldHeight() / 12; // Height of the Settings icon

            float exitIconX = 80f;        // X position for Exit icon
            float exitIconY = 3f;         // Y position for Exit icon
            float exitIconWidth = viewport.getWorldWidth() / 8;   // Width of the Exit icon
            float exitIconHeight = viewport.getWorldHeight() / 12; // Height of the Exit icon

            // Check if the user touched within the Settings icon's bounds
            if (touchPos.x >= settingsIconX && touchPos.x <= (settingsIconX + settingsIconWidth)
                && touchPos.y >= settingsIconY && touchPos.y <= (settingsIconY + settingsIconHeight)) {
                // Settings icon was clicked
                System.out.println("Settings icon clicked");
                game_runner.setScreen(new settings_screen(game_runner, assetManager)); // Switch to the Settings screen
            }

            // Check if the user touched within the Exit icon's bounds
            if (touchPos.x >= exitIconX && touchPos.x <= (exitIconX + exitIconWidth)
                && touchPos.y >= exitIconY && touchPos.y <= (exitIconY + exitIconHeight)) {
                // Exit icon was clicked
                System.out.println("Exit icon clicked");
                Gdx.app.exit(); // Exit the game
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        //true basically means centering it, adding black bars to the side;
        // Resize your screen here. The parameters represent the new window size.

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
