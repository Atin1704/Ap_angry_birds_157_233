package com.data;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.birds.*;

import java.io.Serializable;
public class Bird implements Serializable {
    private double velocity;
    private double impact;
    private double health;
    private double position_x;
    private double position_y;
    private SpriteBatch spritebatch;
    private AssetManager assetManager;
    private Texture image;

    public Bird(SpriteBatch spritebatch, AssetManager assetManager) {
        this.spritebatch = spritebatch;
        this.assetManager = assetManager;
    }
    public Texture getimage(){
        return image;
    }
    public double getHealth() {
        return health;
    }
    public double getPosition_x() {
        return position_x;
    }
    public double getPosition_y() {
        return position_y;
    }
    public double getVelocity() {
        return velocity;
    }
    public double getImpact() {
        return impact;
    }
    public SpriteBatch getbatch() {return spritebatch;}
    public void setHealth(double health) {
        this.health = health;
    }
    public void setPosition_x(double position_x) {
        this.position_x = position_x;
    }
    public void setPosition_y(double position_y) {
        this.position_y = position_y;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
    public void setImpact(double impact) {
        this.impact = impact;
    }
    public boolean Special_ab_active(){
        return false;
    }
    public void hit_pig(Pig piggy){
        Physics_logic coll = new Physics_logic();
        coll.collision_handle();
        coll.after_collision();
        double res = piggy.get_resistance();
        double h2 = piggy.get_health();
    }
    public void hit_block(Block block){
        Physics_logic coll = new Physics_logic();
        coll.collision_handle();
        coll.after_collision();
        double res = block.get_resistance();
        double h2 = block.get_health();
    }
    //public static void set_value();
    //public abstract void start();
}
