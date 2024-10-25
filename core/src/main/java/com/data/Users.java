package com.data;
import java.io.Serializable;

public class Users implements Serializable {
    private static String name;
    private static int age;
    private static double xp;

    public double getXp() {
            return xp;
    }
    public void setXp(double xp) {
        this.xp = xp;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;

    }
//    public void Interact_main(){
//        Mainmenu menu = new Mainmenu();
//        menu.start();
//    }

}

