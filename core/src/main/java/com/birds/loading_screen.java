package com.birds;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** First screen of the application. Displayed after the application is created. */
public class loading_screen implements Screen {
    private final AssetManager assetManager;
    private Texture background_image;
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    FitViewport viewport;
    public loading_screen(Main main) {
        this.game_runner = main;
        background_image = new Texture("loading_screen.png");
        this.assetManager = new AssetManager();
        this.spriteBatch=main.batch;
        viewport = new FitViewport(100, 100);

    }

    @Override
    public void show() {
        assetManager.load("main_screen_bg.png", Texture.class);

    }

    @Override
    public void render(float delta) {
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
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {

    }
}
