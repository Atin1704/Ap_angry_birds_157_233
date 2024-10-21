package com.birds;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** First screen of the application. Displayed after the application is created. */
//public class loading_screen implements Screen {
//    private final AssetManager assetManager;
//    private Texture background_image;
//    private Texture loading;
//    private Texture loading_green;
//    private Main game_runner;
//    private final SpriteBatch spriteBatch;
//    FitViewport viewport;
//    public loading_screen(Main main) {
//        this.game_runner = main;
//        background_image = new Texture("loading_screen.png");
//        loading = new Texture("loading_bar.png");
//        loading_green = new Texture("loading_bar_green.png");
//        this.assetManager = new AssetManager();
//        this.spriteBatch=main.batch;
//        viewport = new FitViewport(100, 100);
//
//    }
//
//    @Override
//    public void show() {
//        assetManager.load("main_screen_bg.png", Texture.class);
//
//    }
//
//    @Override
//    public void render(float delta) {
//        ScreenUtils.clear(Color.BLACK);
//        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
//        spriteBatch.begin();
//
//        float worldWidth = viewport.getWorldWidth();
//        float worldHeight = viewport.getWorldHeight();
//
//        spriteBatch.draw(background_image, 0, 0, worldWidth, worldHeight);
//        spriteBatch.draw(loading, 28, 5, worldWidth/2, worldHeight/19);
//        spriteBatch.draw(loading_green, 28, 5, worldWidth/5, worldHeight/19);
//
//
//        spriteBatch.end();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height, true);
//        //true basically means centering it, adding black bars to the side;
//        // Resize your screen here. The parameters represent the new window size.
//
//    }
//
//    @Override
//    public void pause() {
//        // Invoked when your application is paused.
//    }
//
//    @Override
//    public void resume() {
//        // Invoked when your application is resumed after pause.
//    }
//
//    @Override
//    public void hide() {
//        // This method is called when another screen replaces this one.
//    }
//
//    @Override
//    public void dispose() {
//
//    }
//}


//package com.birds;

//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.utils.viewport.FitViewport;



public class loading_screen implements Screen {
    private final AssetManager assetManager;
    private Texture background_image;
    private Texture loading;
    private Texture loading_green;
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    private FitViewport viewport;


    // Loading bar control variables
    private float loadingBarWidth;
    private float maxLoadingBarWidth;
    private final float totalLoadingTime = 7.0f; // 7 seconds for the loading bar
    private long startTime; // Time when the loading starts

    public loading_screen(Main main) {
        this.game_runner = main;
        background_image = new Texture("loading_screen.png");
        loading = new Texture("loading_bar.png");
        loading_green = new Texture("loading_bar_green.png");
        this.assetManager = new AssetManager();
        this.spriteBatch = main.batch;
        viewport = new FitViewport(100, 100);
        loadingBarWidth = 0; // Initialize the width of the loading bar

    }

    @Override
    public void show() {
        // Load assets
        assetManager.load("main_screen_bg.png", Texture.class);
        assetManager.load("Settings_icon.png", Texture.class);
        assetManager.load("Level_1.png", Texture.class);
        assetManager.load("Level_2.png", Texture.class);
        assetManager.load("Level_3.png", Texture.class);
        assetManager.load("Saved_game_icon.png", Texture.class);
        assetManager.load("Exit_icon.png", Texture.class);
        assetManager.load("Angry_Birds.png", Texture.class);
        assetManager.load("Settings_bg.png", Texture.class);
        assetManager.load("Saved_game_1_icon.png", Texture.class);
        assetManager.load("Saved_game_2_icon.png", Texture.class);
        assetManager.load("Saved_game_3_icon.png", Texture.class);
        assetManager.load("Saved_game_top.png", Texture.class);





        // Set the starting time
        startTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        maxLoadingBarWidth = worldWidth / 2; // Maximum width of the loading bar

        spriteBatch.draw(background_image, 0, 0, worldWidth, worldHeight);
        spriteBatch.draw(loading, 28, 5, worldWidth / 2, worldHeight / 19);

        // Calculate how much time has passed
        float elapsedTime = (TimeUtils.timeSinceMillis(startTime)) / 1000f;

        // Update loading bar width based on elapsed time
        if (elapsedTime < totalLoadingTime) {
            loadingBarWidth = (elapsedTime / totalLoadingTime) * maxLoadingBarWidth;
        } else {
            loadingBarWidth = maxLoadingBarWidth;
        }

        // Draw the green loading bar
        spriteBatch.draw(loading_green, 28, 5, loadingBarWidth, worldHeight / 19);

        spriteBatch.end();

        // After 7 seconds or when assets are loaded, switch to the main screen
        if (elapsedTime >= totalLoadingTime && assetManager.update()) {
            assetManager.finishLoading(); // Ensure all assets are fully loaded
            game_runner.setScreen(new main_screen(game_runner, assetManager)); // Proceed to the main screen
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
    public void dispose() {
        background_image.dispose();
        loading.dispose();
        loading_green.dispose();
        assetManager.dispose();
    }
}
