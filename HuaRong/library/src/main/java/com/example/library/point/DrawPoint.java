package com.example.library.point;

/**
 * Created by Administrator on 2019-08-16.
 */

public class DrawPoint {

    public float x;
    public float y;
    public float width;

    public DrawPoint set(float x, float y, float width) {
        this.x = x;
        this.y = y;
        this.width = width;
        return this;
    }
}
