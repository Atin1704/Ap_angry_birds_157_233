package com.birds;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class main_screen implements Screen {
    private final AssetManager assetManager;
    private Texture background_image;
    private Texture Settings_icon;
    private Texture Level1;
    private Texture Level2;
    private Texture Level3;
    private Texture Saved_game;
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    FitViewport viewport;

    public main_screen(Main main,AssetManager assetManager) {
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch=main.batch;
        viewport = new FitViewport(100, 100);

    }

    @Override
    public void show() {
        background_image = assetManager.get("main_screen_bg.png", Texture.class);
        Settings_icon = assetManager.get("Settings_icon.png", Texture.class);
        Level1 = assetManager.get("Level_1.png", Texture.class);
        Level2 = assetManager.get("Level_2.png", Texture.class);
        Level3 = assetManager.get("Level_3.png", Texture.class);
        Saved_game = assetManager.get("Saved_game_icon.png", Texture.class);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();


        spriteBatch.draw(background_image, 0, 0, worldWidth, worldHeight);
        spriteBatch.draw(Settings_icon, 5, 91, worldWidth/12, worldHeight/15);
        spriteBatch.draw(Level1, 33,41 , worldWidth/3, worldHeight/10);
        spriteBatch.draw(Level2, 33, 28, worldWidth/3, worldHeight/10);
        spriteBatch.draw(Level3, 33, 15, worldWidth/3, worldHeight/10);
        spriteBatch.draw(Saved_game, 33, 54, worldWidth/3, worldHeight/10);

        spriteBatch.end();

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
