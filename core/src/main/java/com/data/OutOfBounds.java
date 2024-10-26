package com.data;

public class OutOfBounds extends RuntimeException {
    public OutOfBounds(){
        super("Bird/block/pig has breached the level bounds");
    }
}
