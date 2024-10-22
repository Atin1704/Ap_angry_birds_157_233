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

    private Main game_runner;
    private final SpriteBatch spriteBatch;
    FitViewport viewport;
    Vector2 touchPos;


    public level_1_screen(Main main,AssetManager assetManager) {
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch=main.batch;
        viewport = new FitViewport(1200, 1000);
        touchPos = new Vector2();

    }

    @Override
    public void show() {
        background_image = assetManager.get("Level1_bg.png", Texture.class);
        red_bird = assetManager.get("Red_bird.png", Texture.class);
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
        spriteBatch.draw(slingshot,140,140,60,150);
        spriteBatch.draw(red_bird, 162,240,35,35);
        spriteBatch.draw(yellow_bird, 95,140,35,35);
        spriteBatch.draw(black_bird, 40,140,45,45);
        spriteBatch.draw(wood_block)



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
