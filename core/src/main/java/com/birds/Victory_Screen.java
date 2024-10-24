package com.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Victory_Screen implements Screen {
    private final AssetManager assetManager;
    private Texture background_image;
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    FitViewport viewport;
    Vector2 touchPos;

    private boolean isDragging = false; // Track if the user is dragging the volume bar
    private float volumePercentage = 0.5f; // Volume percentage (initially 50%)

    public Victory_Screen(Main main, AssetManager assetManager,int level) {
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch = main.batch;
        viewport = new FitViewport(100, 100);
        touchPos = new Vector2();
    }

    @Override
    public void show() {
        background_image = assetManager.get("Settings_bg.png", Texture.class);
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

        spriteBatch.end();
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            // Get the touch position in screen coordinates and convert it to world coordinates
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos); // Converts screen coordinates to the viewport's world coordinates (100x100 system)

            float exitIconX = 80f;        // X position for Exit icon
            float exitIconY = 3f;         // Y position for Exit icon
            float exitIconWidth = viewport.getWorldWidth() / 8;   // Width of the Exit icon
            float exitIconHeight = viewport.getWorldHeight() / 12; // Height of the Exit icon

            // Check if the user touched within the Exit icon's bounds
            if (touchPos.x >= exitIconX && touchPos.x <= (exitIconX + exitIconWidth)
                && touchPos.y >= exitIconY && touchPos.y <= (exitIconY + exitIconHeight)) {

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game_runner.setScreen(new main_screen(game_runner, assetManager));
                    }
                }, 0.25f);  // Delay of 0.25 seconds
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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

