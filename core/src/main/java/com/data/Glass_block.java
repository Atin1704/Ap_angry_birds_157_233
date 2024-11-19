package com.data;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.birds.*;
import java.io.Serializable;

public class Glass_block extends Block implements Serializable {
    private Texture full_box;
    private Texture stick_v;
    private Texture stick_h;
    private int health=1;
    public Glass_block(AssetManager assetManager, SpriteBatch spriteBatch) {
        super(spriteBatch, assetManager);
        full_box = assetManager.get("Glass_block.png", Texture.class);
        stick_v = assetManager.get("Glass_Stick_Vertical.png", Texture.class);
        stick_h = assetManager.get("Glass_Stick_Horizontal.png", Texture.class);
    }
    @Override
    public Texture getFull_box() {
        return full_box;
    }
    @Override
    public Texture getStick_v() {
        return stick_v;
    }
    @Override
    public Texture getStick_h() {
        return stick_h;
    }
    static {
        set_value();
    }
    //@Override
    public static void set_value(){

    }
}
