package com.data;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.birds.*;
import java.io.Serializable;

public class Black_bird extends Bird implements Serializable {
    private Texture image;
    private int health=6;
    public Black_bird(AssetManager assetManager, SpriteBatch spriteBatch) {
        super(spriteBatch, assetManager);
        image = assetManager.get("Black_bird.png", Texture.class);
    }
    @Override
    public Texture getimage() {
        return image;
    }
    static {
        set_value();
    }
    //@Override
    public static void set_value(){

    }
}
