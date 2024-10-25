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
import com.data.*;

public class level_1_screen implements Screen {
    private final AssetManager assetManager;
    private Texture background_image;
    private Texture pause_button;
    private Texture red_bird;
    private Texture yellow_bird;
    private Texture black_bird;
    private Texture normal_pig;
    private Texture king_pig;
    private Texture old_pig;
    private Texture wood_block;
    private Texture glass_block;
    private Texture stone_block;
    private Texture slingshot;
    private Bird rb_1;

    private Main game_runner;
    private final SpriteBatch spriteBatch;
    StretchViewport viewport;
    Vector2 touchPos;

    public level_1_screen(Main main, AssetManager assetManager) {
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch = main.batch;
        viewport = new StretchViewport(1200, 1000);
        touchPos = new Vector2();
        rb_1 = new Red_bird(assetManager, main.batch);
    }

    @Override
    public void show() {
        background_image = assetManager.get("Level2_bg.png", Texture.class);
        //red_bird = assetManager.get("Red_bird.png", Texture.class);
        black_bird = assetManager.get("Black_bird.png", Texture.class);
        yellow_bird = assetManager.get("Yellow_bird.png", Texture.class);
        stone_block = assetManager.get("Stone_block.png", Texture.class);
        glass_block = assetManager.get("Glass_block.png", Texture.class);
        wood_block = assetManager.get("Wooden_block.png", Texture.class);
        king_pig = assetManager.get("King_pig.png", Texture.class);
        normal_pig = assetManager.get("Normal_pig.png", Texture.class);
        old_pig = assetManager.get("Old_pig.png", Texture.class);
        slingshot = assetManager.get("Slingshot.png", Texture.class);
        pause_button = assetManager.get("Pause_icon.png", Texture.class);
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
        spriteBatch.draw(slingshot, 180, 220, 75, 250);
        //spriteBatch.draw(red_bird, 210, 400, 45, 45);
        rb_1.getbatch().draw(rb_1.getimage(),210,400,45,45);
        spriteBatch.draw(yellow_bird, 110, 220, 45, 45);
        spriteBatch.draw(black_bird, 25, 220, 60, 60);
        spriteBatch.draw(wood_block, 700, 220, 100, 100);
        spriteBatch.draw(glass_block, 800, 220, 100, 100);
        spriteBatch.draw(stone_block, 900, 220, 100, 100);
        spriteBatch.draw(glass_block, 750, 320, 100, 100);
        spriteBatch.draw(wood_block, 850, 320, 100, 100);
        spriteBatch.draw(normal_pig, 775, 325, 50, 50);
        spriteBatch.draw(pause_button, 1080, 910, 65, 65);

        spriteBatch.end();
    }

    private void input() {
        // Handle touch input for pause button
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            float pauseButtonX = 1100f;
            float pauseButtonY = 920f;
            float pauseButtonWidth = 45f;
            float pauseButtonHeight = 45f;

            if (touchPos.x >= pauseButtonX && touchPos.x <= (pauseButtonX + pauseButtonWidth)
                && touchPos.y >= pauseButtonY && touchPos.y <= (pauseButtonY + pauseButtonHeight)) {

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game_runner.setScreen(new pause_screen(game_runner, assetManager, 1));
                    }
                }, 0.25f);  // Delay of 0.25 seconds
            }
        }

        // Handle "Enter" key press for victory screen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game_runner.setScreen(new Victory_Screen(game_runner, assetManager,1));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DEL)) {
            game_runner.setScreen(new Defeat_Screen(game_runner, assetManager,1));
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
