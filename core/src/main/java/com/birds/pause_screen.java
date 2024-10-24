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
    FitViewport viewport;
    Vector2 touchPos;
    private int level;

    public pause_screen(Main main,AssetManager assetManager,int level) {
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch=main.batch;
        viewport = new FitViewport(100, 100);
        touchPos = new Vector2();
        this.level = level;

    }

    @Override
    public void show() {
        background_image_1 = assetManager.get("", Texture.class);
        background_image_2 = assetManager.get("//needs to be filled//level2 bg", Texture.class);
        background_image_3 = assetManager.get("//needs to be filled//level 3 bg", Texture.class);
        pause_menu = assetManager.get("Pause_Menu.png", Texture.class);
        settings_button = assetManager.get("settings_button.png", Texture.class);
        play_button= assetManager.get("play_button.png", Texture.class);
        rotate_button = assetManager.get("rotate_button.png", Texture.class);
        save_button = assetManager.get("save_button.png", Texture.class);
        home_button = assetManager.get("home_button.png", Texture.class);


    }

    @Override
    public void render(float v) {
//        input();
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
                spriteBatch.draw(background_image_2, 0, 0, worldWidth, worldHeight);
                break;
            case 3:
                spriteBatch.draw(background_image_3, 0, 0, worldWidth, worldHeight);
                break;


        }

//        spriteBatch.draw(pause_menu, 0, 0, worldWidth, worldHeight);
//        spriteBatch.draw(play_button,23 , 64, 60, 20);
//        spriteBatch.draw(rotate_button, 33,41 , worldWidth/3, worldHeight/10);
//        spriteBatch.draw(save_button, 33,28 , worldWidth/3, worldHeight/10);
//        spriteBatch.draw(settings_button, 33,15 , worldWidth/3, worldHeight/10);
//        //spriteBatch.draw(home_button, 80, 3, worldWidth/8, worldHeight/12);  this has to be decided;;;



        spriteBatch.end();

    }

//needs to be completed ,depends highly on how we implement screens
//    private void input() {
//        if (Gdx.input.isTouched()) {
//            // Get the touch position in screen coordinates and convert it to world coordinates
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
//            viewport.unproject(touchPos); // Converts screen coordinates to the viewport's world coordinates (100x100 system)
//
//            float exitIconX = 80f;        // X position for Exit icon
//            float exitIconY = 3f;         // Y position for Exit icon
//            float exitIconWidth = viewport.getWorldWidth() / 8;   // Width of the Exit icon
//            float exitIconHeight = viewport.getWorldHeight() / 12; // Height of the Exit icon
//
//            // Check if the user touched within the Exit icon's bounds
//            if (touchPos.x >= exitIconX && touchPos.x <= (exitIconX + exitIconWidth)
//                && touchPos.y >= exitIconY && touchPos.y <= (exitIconY + exitIconHeight)) {
//
//
//
//                Timer.schedule(new Timer.Task() {
//                    @Override
//                    public void run() {
//                        game_runner.setScreen(new main_screen(game_runner, assetManager));
//                    }
//                }, 0.25f);  // Delay of 0.5 seconds (500ms)
//            }
//        }
//    }

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
