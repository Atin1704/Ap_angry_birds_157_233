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

public class saved_game_screen implements Screen {
    private final AssetManager assetManager;
    private Texture background_image;
    private Texture saved_top;
    private Texture saved_1;
    private Texture saved_2;
    private Texture saved_3;
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    FitViewport viewport;
    Vector2 touchPos;
    private Texture exit_icon;

    public saved_game_screen(Main main,AssetManager assetManager) {
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch=main.batch;
        viewport = new FitViewport(100, 100);
        touchPos = new Vector2();

    }

    @Override
    public void show() {
        background_image = assetManager.get("Settings_bg.png", Texture.class);
        saved_top = assetManager.get("Saved_game_top.png", Texture.class);
        saved_1 = assetManager.get("Saved_game_1_icon.png", Texture.class);
        saved_2 = assetManager.get("Saved_game_2_icon.png", Texture.class);
        saved_3 = assetManager.get("Saved_game_3_icon.png", Texture.class);
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
        spriteBatch.draw(saved_top,15 , 70, 70, 80);
        spriteBatch.draw(saved_1, 33,41 , worldWidth/3, worldHeight/10);
        spriteBatch.draw(saved_2, 33, 28, worldWidth/3, worldHeight/10);
        spriteBatch.draw(saved_3, 33, 15, worldWidth/3, worldHeight/10);
        spriteBatch.draw(exit_icon, 80, 3, worldWidth/8, worldHeight/12);



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
                }, 0.25f);  // Delay of 0.5 seconds (500ms)
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
