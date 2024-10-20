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
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    FitViewport viewport;

    public main_screen(Main main,AssetManager assetManager) {
        this.game_runner = main;
        this.assetManager = new AssetManager();
        this.spriteBatch=main.batch;
        viewport = new FitViewport(100, 100);

    }

    @Override
    public void show() {
        background_image = assetManager.get("main_screen_bg.png", Texture.class);

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();


        spriteBatch.draw(background_image, 0, 0, worldWidth, worldHeight);

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
