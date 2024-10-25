package com.data;


public class Slingshot {
    protected final double x_coordinate = 0.0;
    protected final double y_coordinate = 0.0;
    protected final double max_stretch = 0.0;
    protected final double min_stretch = 0.0;
    protected double angle;

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
