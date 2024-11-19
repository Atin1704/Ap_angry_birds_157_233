package com.data;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.Serializable;

public class Old_pig extends Pig implements Serializable {
    private Texture image;
    private int health=2;
    public Old_pig(AssetManager assetManager, SpriteBatch spriteBatch) {
        super(spriteBatch, assetManager);
        image = assetManager.get("Old_pig.png", Texture.class);
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
