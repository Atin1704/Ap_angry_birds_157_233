package com.data;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Slingshot {
    protected final double x_coordinate = 0.0;
    protected final double y_coordinate = 0.0;
    protected final double max_stretch = 0.0;
    protected final double min_stretch = 0.0;
    protected double angle;
    private SpriteBatch spritebatch;
    private AssetManager assetManager;
    private Texture image;

    public Slingshot(SpriteBatch spritebatch, AssetManager assetManager) {
        this.spritebatch = spritebatch;
        this.assetManager = assetManager;
        image = assetManager.get("Slingshot.png", Texture.class);
    }
    public Texture getimage(){
        return image;
    }
    public SpriteBatch getbatch() {return spritebatch;}

    public static void start(){}

    public double getAngle() {
        return angle;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }
    public double getMax_stretch() {
        return max_stretch;
    }
    public double getMin_stretch() {
        return min_stretch;
    }
    public double getX_coordinate() {
        return x_coordinate;
    }
    public double getY_coordinate() {
        return y_coordinate;
    }
    public void Load_bird(Bird bird){

    }
    public void path() {
        Physics_logic trajectory = new Physics_logic();
        trajectory.track_trajectory();
    }
}
