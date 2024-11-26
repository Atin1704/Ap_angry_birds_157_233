// Main.java
package com.birds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Music music;
    public Sound click;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new loading_screen(this));
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        music = Gdx.audio.newMusic(Gdx.files.internal("Main_bg_music.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        click = Gdx.audio.newSound(Gdx.files.internal("Click.mp3"));
        Database.load();
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        Database.store();
    }

    // Add this method to set the volume of the music
    public void setMusicVolume(float volume) {
        if (music != null) {
            music.setVolume(volume);
        }
    }
}
