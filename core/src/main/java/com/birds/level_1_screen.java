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
    private Slingshot slingshot;
    private Pig np_1;
    private Pig kp_1;
    private Pig op_1;
    private Bird rb_2;
    private Bird rb_1;
    private Bird bb_1;
    private Bird yb_1;
    private Block wb_1;
    private Block wb_2;
    private Block wb_3;
    private Block wb_4;
    private Block wb_5;
    private Block wb_6;
    private Block wb_7;
    private Block gb_1;
    private Block gb_2;
    private Block gb_3;
    private Block gb_4;
    private Block gb_5;
    private Block gb_6;
    private Block sb_1;
    private Block sb_2;
    private Block sb_3;
    private Block sb_4;
    private Block sb_5;
    private Block sb_6;

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
        slingshot = new Slingshot(main.batch,assetManager);
        rb_2 = new Red_bird(assetManager, main.batch);
        np_1 = new Normal_pig(assetManager, main.batch);
        kp_1 = new King_pig(assetManager, main.batch);
        op_1 = new Old_pig(assetManager, main.batch);
        bb_1 = new Black_bird(assetManager, main.batch);
        yb_1 = new Yellow_bird(assetManager, main.batch);
        wb_1 = new Wood_block(assetManager, main.batch);
        wb_2 = new Wood_block(assetManager, main.batch);
        wb_3 = new Wood_block(assetManager, main.batch);
        wb_4 = new Wood_block(assetManager, main.batch);
        wb_5 = new Wood_block(assetManager, main.batch);
        wb_6 = new Wood_block(assetManager, main.batch);
        wb_7 = new Wood_block(assetManager, main.batch);
        gb_1 = new Glass_block(assetManager, main.batch);
        gb_2 = new Glass_block(assetManager, main.batch);
        gb_3 = new Glass_block(assetManager, main.batch);
        gb_4 = new Glass_block(assetManager, main.batch);
        gb_5 = new Glass_block(assetManager, main.batch);
        gb_6 = new Glass_block(assetManager, main.batch);
        sb_1 = new Stone_block(assetManager, main.batch);
        sb_2 = new Stone_block(assetManager, main.batch);
        sb_3 = new Stone_block(assetManager, main.batch);
        sb_4 = new Stone_block(assetManager, main.batch);
        sb_5 = new Stone_block(assetManager, main.batch);
        sb_6 = new Stone_block(assetManager, main.batch);
    }

    @Override
    public void show() {
        background_image = assetManager.get("Level2_bg.png", Texture.class);
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
        rb_1.getbatch().draw(rb_1.getimage(),260,400,50,50);
        slingshot.getbatch().draw(slingshot.getimage(),230,220,75,250);
        yb_1.getbatch().draw(yb_1.getimage(),105,220,55,55);
        rb_2.getbatch().draw(rb_2.getimage(),170,220,50,50);
        bb_1.getbatch().draw(bb_1.getimage(),20,220,65,80);
        sb_1.getbatch().draw(sb_1.getStick_h(),700,220, 50, 20);
        sb_2.getbatch().draw(sb_2.getStick_h(),750,220, 50, 20);
        sb_3.getbatch().draw(sb_3.getStick_h(),870,220, 50, 20);
        sb_4.getbatch().draw(sb_4.getStick_h(),920,220, 50, 20);
        wb_1.getbatch().draw(wb_1.getStick_h(),720,240, 60, 20);
//        spriteBatch.draw(wood_block, 700, 220, 100, 100);
//        spriteBatch.draw(glass_block, 800, 220, 100, 100);
//        spriteBatch.draw(stone_block, 900, 220, 100, 100);
//        spriteBatch.draw(glass_block, 750, 320, 100, 100);
//        spriteBatch.draw(wood_block, 850, 320, 100, 100);
//        spriteBatch.draw(normal_pig, 775, 325, 50, 50);
        spriteBatch.draw(pause_button, 1080, 910, 65, 65);

        spriteBatch.end();
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();

        }
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
