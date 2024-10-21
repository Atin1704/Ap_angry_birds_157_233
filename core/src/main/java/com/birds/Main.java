package com.birds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public SpriteBatch batch;
    public BitmapFont font;



    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new loading_screen(this));
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
