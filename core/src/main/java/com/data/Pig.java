package com.data;
import java.io.Serializable;

public class Pig implements Serializable {
    private double resistance;
    private double health;
    private double position_x;
    private double position_y;
    private double radius;
    private double velocity;
    public Pig() {

    }
    public double get_radius() {
        return radius;
    }
    public double get_position_x() {
        return position_x;
    }
    public double get_position_y() {
        return position_y;
    }
    public double get_resistance() {
        return resistance;
    }
    public double get_health() {
        return health;
    }
    public void set_radius(double radius) {
        this.radius = radius;
    }
    public void set_position_x(double position_x) {
        this.position_x = position_x;
    }
    public void set_position_y(double position_y) {
        this.position_y = position_y;
    }
    public void set_resistance(double resistance) {
        this.resistance = resistance;
    }
    public void set_health(double health) {
        this.health = health;
    }

}
