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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class pause_screen implements Screen {
    private final AssetManager assetManager;
    private Texture background_image_1;
    private Texture background_image_2;
    private Texture background_image_3;
    private Texture pause_menu;
    private Texture play_button;
    private Texture rotate_button;
    private Texture settings_button;
    private Texture save_button;
    private Texture home_button;


    private Main game_runner;
    private final SpriteBatch spriteBatch;
    StretchViewport viewport;
    Vector2 touchPos;
    private int level;

    public pause_screen(Main main,AssetManager assetManager,int level) {
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch=main.batch;
        viewport = new StretchViewport(1000, 1000);
        touchPos = new Vector2();
        this.level = level;

    }

    @Override
    public void show() {
        background_image_1 = assetManager.get("Level1_bg_dimmed.png", Texture.class);
//        background_image_2 = assetManager.get("//needs to be filled//level2 bg", Texture.class);
//        background_image_3 = assetManager.get("//needs to be filled//level 3 bg", Texture.class);
        pause_menu = assetManager.get("Pause_Menu.png", Texture.class);
        settings_button = assetManager.get("settings_button.png", Texture.class);
        play_button= assetManager.get("play_button.png", Texture.class);
        rotate_button = assetManager.get("rotate_button.png", Texture.class);
        save_button = assetManager.get("save_button.png", Texture.class);
        home_button = assetManager.get("home_button.png", Texture.class);


    }

    @Override
    public void render(float v) {
        input();
        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        switch (level){
            case 1:
                spriteBatch.draw(background_image_1, 0, 0, worldWidth, worldHeight);
                break;
            case 2:
                spriteBatch.draw(background_image_2, 0, 0, worldWidth, worldHeight);
                break;
            case 3:
                spriteBatch.draw(background_image_3, 0, 0, worldWidth, worldHeight);
                break;
            default:
                break;


        }
        spriteBatch.draw(pause_menu,100 , 50,worldWidth-200, worldHeight-100);
        spriteBatch.draw(play_button,435,670,100,100 );
        spriteBatch.draw(rotate_button,435,550,100,100 );
        spriteBatch.draw(settings_button,435,430,100,100 );
        spriteBatch.draw(save_button,435,310,100,100 );
        spriteBatch.draw(home_button,435,190,100,100 );




        spriteBatch.end();

    }


    private void input() {
        if (Gdx.input.isTouched()) {
            // Get the touch position in screen coordinates and convert it to world coordinates
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos); // Converts screen coordinates to the viewport's world coordinates (1000x1000 system)

            // Define button bounds
            float playButtonX = 435f;
            float playButtonY = 670f;
            float playButtonWidth = 100f;
            float playButtonHeight = 100f;

            float rotateButtonX = 435f;
            float rotateButtonY = 550f;
            float rotateButtonWidth = 100f;
            float rotateButtonHeight = 100f;

            float settingsButtonX = 435f;
            float settingsButtonY = 430f;
            float settingsButtonWidth = 100f;
            float settingsButtonHeight = 100f;

            float saveButtonX = 435f;
            float saveButtonY = 310f;
            float saveButtonWidth = 100f;
            float saveButtonHeight = 100f;

            float homeButtonX = 435f;
            float homeButtonY = 190f;
            float homeButtonWidth = 100f;
            float homeButtonHeight = 100f;

            // Play button touch detection
            if (touchPos.x >= playButtonX && touchPos.x <= (playButtonX + playButtonWidth)
                && touchPos.y >= playButtonY && touchPos.y <= (playButtonY + playButtonHeight)) {
                // Handle play button action (resume game)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if(level==1){
                            game_runner.setScreen(new level_1_screen(game_runner, assetManager));
                        }
                    }
                }, 0.25f);  // Delay of 0.25 seconds
            }

            // Rotate button touch detection
            if (touchPos.x >= rotateButtonX && touchPos.x <= (rotateButtonX + rotateButtonWidth)
                && touchPos.y >= rotateButtonY && touchPos.y <= (rotateButtonY + rotateButtonHeight)) {
                // Handle rotate button action (rotate the game)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if(level==1){
                            game_runner.setScreen(new level_1_screen(game_runner, assetManager));
                        }

                    }
                }, 0.25f);
            }

            // Settings button touch detection
            if (touchPos.x >= settingsButtonX && touchPos.x <= (settingsButtonX + settingsButtonWidth)
                && touchPos.y >= settingsButtonY && touchPos.y <= (settingsButtonY + settingsButtonHeight)) {
                // Handle settings button action
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        // Call method to open settings screen
                         Example: game_runner.setScreen(new settings_screen(game_runner, assetManager,true));
                    }
                }, 0.25f);
            }

            // Save button touch detection
            if (touchPos.x >= saveButtonX && touchPos.x <= (saveButtonX + saveButtonWidth)
                && touchPos.y >= saveButtonY && touchPos.y <= (saveButtonY + saveButtonHeight)) {
                // Handle save button action
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {

                        game_runner.setScreen(new saved_game_screen(game_runner, assetManager));

                    }
                }, 0.25f);
            }

            // Home button touch detection
            if (touchPos.x >= homeButtonX && touchPos.x <= (homeButtonX + homeButtonWidth)
                && touchPos.y >= homeButtonY && touchPos.y <= (homeButtonY + homeButtonHeight)) {
                // Handle home button action (return to main menu)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if(level==1){
                            game_runner.setScreen(new main_screen(game_runner, assetManager));
                        }
                    }
                }, 0.25f);
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
