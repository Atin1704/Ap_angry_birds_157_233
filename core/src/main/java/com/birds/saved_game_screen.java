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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.data.AssetNotLoaded;
import com.data.GameNotFound;

public class saved_game_screen implements Screen {
    private final AssetManager assetManager;
    private Texture background_image;
    private Texture saved_top;
    private Texture saved_1;
    private Texture saved_2;
    private Texture saved_3;
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    StretchViewport viewport;
    Vector2 touchPos;
    private Texture exit_icon;

    public saved_game_screen(Main main,AssetManager assetManager) {
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch=main.batch;
        viewport = new StretchViewport(100, 100);
        touchPos = new Vector2();

    }

    @Override
    public void show() {
        background_image = assetManager.get("Settings_bg.png", Texture.class);
        saved_top = assetManager.get("Saved_game_icon.png", Texture.class);
        saved_1 = assetManager.get("Saved_game_1_icon.png", Texture.class);
        saved_2 = assetManager.get("Saved_game_2_icon.png", Texture.class);
        saved_3 = assetManager.get("Saved_game_3_icon.png", Texture.class);
        exit_icon = assetManager.get("Exit_icon.png", Texture.class);

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
        spriteBatch.draw(saved_top,23 , 64, 50, 20);
        spriteBatch.draw(saved_1, 36,41 , 25, worldHeight/10);
        spriteBatch.draw(saved_2, 36,28 , 25, worldHeight/10);
        spriteBatch.draw(saved_3, 36,15 , 25, worldHeight/10);
        spriteBatch.draw(exit_icon, 80, 3, worldWidth/8, worldHeight/12);



        spriteBatch.end();

    }

    private void input() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        Gdx.app.exit();
    }
    if (Gdx.input.justTouched()) {
        touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);

        float exitIconX = 80f;
        float exitIconY = 3f;
        float exitIconWidth = viewport.getWorldWidth() / 8;
        float exitIconHeight = viewport.getWorldHeight() / 12;

        if (touchPos.x >= exitIconX && touchPos.x <= (exitIconX + exitIconWidth)
            && touchPos.y >= exitIconY && touchPos.y <= (exitIconY + exitIconHeight)) {
            game_runner.click.play();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    game_runner.setScreen(new main_screen(game_runner, assetManager));
                }
            }, 0.25f);
        }

        float saved1X = 36f;
        float saved1Y = 41f;
        float saved1Width = 25f;
        float saved1Height = viewport.getWorldHeight() / 10;

        if (touchPos.x >= saved1X && touchPos.x <= (saved1X + saved1Width)
            && touchPos.y >= saved1Y && touchPos.y <= (saved1Y + saved1Height)) {
            game_runner.click.play();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (!Database.getLevelStack().isEmpty()) {
                        Level level = Database.getLevelStack().pop();
                        if (level instanceof level_1_screen) {
                            game_runner.setScreen((level_1_screen) level);
                        } else if (level instanceof level_2_screen) {
                            game_runner.setScreen((level_2_screen) level);
                        } else if (level instanceof level_3_screen) {
                            game_runner.setScreen((level_3_screen) level);
                        }
                    }
                }
            }, 0.25f);
        }

        float saved2X = 36f;
        float saved2Y = 28f;
        float saved2Width = 25f;
        float saved2Height = viewport.getWorldHeight() / 10;

        if (touchPos.x >= saved2X && touchPos.x <= (saved2X + saved2Width)
            && touchPos.y >= saved2Y && touchPos.y <= (saved2Y + saved2Height)) {
            game_runner.click.play();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (Database.getLevelStack().size() > 1) {
                        Level level = Database.getLevelStack().remove(Database.getLevelStack().size() - 2);
                        if (level instanceof level_1_screen) {
                            game_runner.setScreen((level_1_screen) level);
                        } else if (level instanceof level_2_screen) {
                            game_runner.setScreen((level_2_screen) level);
                        } else if (level instanceof level_3_screen) {
                            game_runner.setScreen((level_3_screen) level);
                        }
                    }
                }
            }, 0.25f);
        }

        float saved3X = 36f;
        float saved3Y = 15f;
        float saved3Width = 25f;
        float saved3Height = viewport.getWorldHeight() / 10;

        if (touchPos.x >= saved3X && touchPos.x <= (saved3X + saved3Width)
            && touchPos.y >= saved3Y && touchPos.y <= (saved3Y + saved3Height)) {
            game_runner.click.play();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (Database.getLevelStack().size() > 2) {
                        Level level = Database.getLevelStack().remove(Database.getLevelStack().size() - 3);
                        if (level instanceof level_1_screen) {
                            game_runner.setScreen((level_1_screen) level);
                        } else if (level instanceof level_2_screen) {
                            game_runner.setScreen((level_2_screen) level);
                        } else if (level instanceof level_3_screen) {
                            game_runner.setScreen((level_3_screen) level);
                        }
                    }
                }
            }, 0.25f);
        }
    }
}


    public void throwing() throws GameNotFound {
        int a = 0;
        int b = 1;
        if(a==0){
            b++;
        }
        else if(a==1){
            throw new GameNotFound();
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
