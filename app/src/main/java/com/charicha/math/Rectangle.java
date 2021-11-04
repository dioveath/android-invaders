package com.charicha.math;

/**
 * Created by Charicha on 1/10/2018.
 */

public class Rectangle {

    public Vector2 lowerLeft;
    public float width, height;

    public Rectangle(float x, float y, float width, float height){
        this.lowerLeft = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

}
