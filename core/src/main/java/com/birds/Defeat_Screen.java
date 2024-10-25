package com.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Defeat_Screen implements Screen {
    private Texture background_image;
    private Texture laughing_pig;
    private Texture lost_message;
    private Texture restart_message;
    private Texture main_message;

    private final AssetManager assetManager;
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    StretchViewport viewport;
    Vector2 touchPos;
    private int level;

    public Defeat_Screen(Main gameRunner, AssetManager assetManager,int level) {
        this.game_runner = gameRunner;
        this.assetManager = assetManager;
        this.spriteBatch=gameRunner.batch;
        viewport = new StretchViewport(1000, 1000);
        touchPos = new Vector2();
        this.level = level;

    }

    @Override
    public void show() {
        background_image = assetManager.get("lostgame_bg.png", Texture.class);
        laughing_pig = assetManager.get("laughing_pig.png", Texture.class);
        lost_message = assetManager.get("Lost_message.png", Texture.class);
        restart_message = assetManager.get("Restart_message.png", Texture.class);
        main_message = assetManager.get("Backtomain_message.png", Texture.class);

    }

    @Override
    public void render(float v) {
        input();
        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(background_image,0,0,worldWidth,worldHeight);
        spriteBatch.draw(laughing_pig,50,0,400,400);
        spriteBatch.draw(lost_message,250,750,500,200);
        spriteBatch.draw(restart_message,350,520,300,150);
        spriteBatch.draw(main_message,350,300,300,150);








        spriteBatch.end();

    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();

        }
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            // Coordinates for the restart button
            float restartButtonX = 350f;
            float restartButtonY = 520f;
            float restartButtonWidth = 300f;
            float restartButtonHeight = 150f;

            // Coordinates for the back to main menu button
            float mainMenuButtonX = 350f;
            float mainMenuButtonY = 300f;
            float mainMenuButtonWidth = 300f;
            float mainMenuButtonHeight = 150f;

            // Check if the restart button is touched
            if (touchPos.x >= restartButtonX && touchPos.x <= (restartButtonX + restartButtonWidth)
                && touchPos.y >= restartButtonY && touchPos.y <= (restartButtonY + restartButtonHeight)) {

                // Restart the current level (based on the level variable)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (level == 1) {
                            game_runner.setScreen(new level_1_screen(game_runner, assetManager));
                        }
                        // Add more levels as needed with else-if or switch cases
                    }
                }, 0.25f);  // Delay of 0.25 seconds
            }

            // Check if the main menu button is touched
            if (touchPos.x >= mainMenuButtonX && touchPos.x <= (mainMenuButtonX + mainMenuButtonWidth)
                && touchPos.y >= mainMenuButtonY && touchPos.y <= (mainMenuButtonY + mainMenuButtonHeight)) {

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
    public void resize(int i, int i1) {
        viewport.update( i, i1, true);

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
