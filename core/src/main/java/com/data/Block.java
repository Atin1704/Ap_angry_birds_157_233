package com.data;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.Serializable;

public class Block implements Serializable {
    private double resistance;
    private double health;
    private double position_x;
    private double position_y;
    private double velocity;
    private SpriteBatch spritebatch;
    private AssetManager assetManager;
    private Texture full_box;
    private Texture stick_v;
    private Texture stick_h;

    public Block(SpriteBatch spritebatch, AssetManager assetManager) {
        this.spritebatch = spritebatch;
        this.assetManager = assetManager;
    }
    public Texture getFull_box(){
        return full_box;
    }
    public Texture getStick_v(){
        return stick_v;
    }
    public Texture getStick_h(){
        return stick_h;
    }
    public SpriteBatch getbatch() {return spritebatch;}
    public double get_position_x() {
        return position_x;
    }
    public double get_position_y() {
        return position_y;
    }
    public double get_velocity() {
        return velocity;
    }
    public double get_resistance() {
        return resistance;
    }

    public double get_health() {
        return health;
    }
    public void set_position_x(double position_x) {
        this.position_x = position_x;
    }
    public void set_position_y(double position_y) {
        this.position_y = position_y;
    }
    public void set_velocity(double velocity) {
        this.velocity = velocity;
    }
    public void set_resistance(double resistance) {
        this.resistance = resistance;
    }
    public void set_health(double health) {
        this.health = health;
    }

}
