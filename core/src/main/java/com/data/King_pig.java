package com.data;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.Serializable;

public class King_pig extends Pig implements Serializable {
    private Texture image;
    private int health=3;
    public King_pig(AssetManager assetManager, SpriteBatch spriteBatch) {
        super(spriteBatch, assetManager);
        image = assetManager.get("King_pig.png", Texture.class);
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
