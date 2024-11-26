package com.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class pause_screen implements Screen {
    private AssetManager assetManager;
    private Texture background_image_1;
    private Texture background_image_2;
    private Texture background_image_3;
    private Texture pause_menu;
    private Texture pause_menu_gs;
    private Texture play_button;
    private Texture rotate_button;
    private Texture settings_button;
    private Texture save_button;
    private Texture home_button;
    private boolean saved = false;
    private boolean scheduledRedirect = false;

    private Main game_runner;
    private  SpriteBatch spriteBatch;
    StretchViewport viewport;
    Vector2 touchPos;
    private int level;
    level_1_screen level1;
    level_2_screen level2;
    level_3_screen level3;

    public pause_screen(Main main, AssetManager assetManager, int level, level_1_screen currentScreen) {

        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch = main.batch;
        viewport = new StretchViewport(1000, 1000);
        touchPos = new Vector2();
        this.level = level;
        this.level1=currentScreen;
    }

    public pause_screen(Main main, AssetManager assetManager, int level, level_2_screen currentScreen) {

        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch = main.batch;
        viewport = new StretchViewport(1000, 1000);
        touchPos = new Vector2();
        this.level = level;
        this.level2=currentScreen;


    }

    public pause_screen(Main main, AssetManager assetManager, int level, level_3_screen currentScreen) {

            this.game_runner = main;
            this.assetManager = assetManager;
            this.spriteBatch = main.batch;
            viewport = new StretchViewport(1000, 1000);
            touchPos = new Vector2();
            this.level = level;
            this.level3=currentScreen;

    }

    @Override
    public void show() {
        background_image_1 = assetManager.get("Level1_bg_dimmed.png", Texture.class);
        pause_menu = assetManager.get("Pause_Menu.png", Texture.class);
        settings_button = assetManager.get("settings_button.png", Texture.class);
        play_button = assetManager.get("play_button.png", Texture.class);
        rotate_button = assetManager.get("rotate_button.png", Texture.class);
        save_button = assetManager.get("save_button.png", Texture.class);
        home_button = assetManager.get("home_button.png", Texture.class);
        pause_menu_gs = assetManager.get("Pause_Menu_gamesaved.png", Texture.class);
    }

    @Override
    public void render(float v) {
        input();
        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        switch (level) {
            case 1:
                spriteBatch.draw(background_image_1, 0, 0, worldWidth, worldHeight);
                break;
            case 2:
                spriteBatch.draw(background_image_1, 0, 0, worldWidth, worldHeight);
                break;
            case 3:
                spriteBatch.draw(background_image_1, 0, 0, worldWidth, worldHeight);
                break;
            default:
                break;
        }

        if (!saved) {
            spriteBatch.draw(pause_menu, 100, 50, worldWidth - 200, worldHeight - 100);
            spriteBatch.draw(play_button, 435, 670, 100, 100);
            spriteBatch.draw(rotate_button, 435, 530, 100, 100);
            spriteBatch.draw(save_button, 435, 390, 100, 100);
            spriteBatch.draw(home_button, 435, 250, 100, 100);

        } else {
            spriteBatch.draw(pause_menu_gs, 100, 50, worldWidth - 200, worldHeight - 100);
            if (!scheduledRedirect) {
                scheduledRedirect = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game_runner.setScreen(new main_screen(game_runner, assetManager));
                    }
                }, 4.0f); // 4 seconds delay
            }
        }
        spriteBatch.end();
    }

    private void input() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        Gdx.app.exit();
    }
    if (Gdx.input.justTouched() && !saved) {
        touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);

        // Define button bounds (Play, Rotate, Settings, Save, Home)
        float playButtonX = 435f, playButtonY = 670f, playButtonWidth = 100f, playButtonHeight = 100f;
        float rotateButtonX = 435f, rotateButtonY = 530f;
        float saveButtonX = 435f, saveButtonY = 390f;
        float homeButtonX = 435f, homeButtonY = 250f;

        if (touchPos.x >= playButtonX && touchPos.x <= (playButtonX + playButtonWidth)
            && touchPos.y >= playButtonY && touchPos.y <= (playButtonY + playButtonHeight)) {
            game_runner.click.play();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (level == 1) {
                        game_runner.setScreen(level1);
                    }
                    if (level == 2) {
                        game_runner.setScreen(level2);
                    }
                    if (level == 3) {
                        game_runner.setScreen(level3);
                    }
                }
            }, 0.25f);
        }

        if (touchPos.x >= rotateButtonX && touchPos.x <= (rotateButtonX + playButtonWidth)
            && touchPos.y >= rotateButtonY && touchPos.y <= (rotateButtonY + playButtonHeight)) {
            game_runner.click.play();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (level == 1) {
                        game_runner.setScreen(new level_1_screen(game_runner, assetManager));
                    }
                    if (level == 2) {
                        game_runner.setScreen(new level_2_screen(game_runner, assetManager));
                    }
                    if (level == 3) {
                        game_runner.setScreen(new level_3_screen(game_runner, assetManager));
                    }
                }
            }, 0.25f);
        }

        if (touchPos.x >= saveButtonX && touchPos.x <= (saveButtonX + playButtonWidth)
            && touchPos.y >= saveButtonY && touchPos.y <= (saveButtonY + playButtonHeight)) {
            game_runner.click.play();
            // Upcast level_1_screen to Level and save it to the stack in Database
            if (level == 1) {
                Level levelToSave = level1;
                Database.addLevel(levelToSave);
                System.out.println("Level 1 saved");
            } else if (level == 2) {
                Level levelToSave = level2;
                Database.addLevel(levelToSave);
                System.out.println("Level 2 saved");
            } else if (level == 3) {
                Level levelToSave = level3;
                Database.addLevel(levelToSave);
                System.out.println("Level 3 saved");
            }
            saved = true;
        }

        if (touchPos.x >= homeButtonX && touchPos.x <= (homeButtonX + playButtonWidth)
            && touchPos.y >= homeButtonY && touchPos.y <= (homeButtonY + playButtonHeight)) {
            game_runner.click.play();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    game_runner.setScreen(new main_screen(game_runner, assetManager));
                }
            }, 0.25f);
        }
    }
}


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
