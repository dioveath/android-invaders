package com.charicha.math;

/**
 * Created by Charicha on 1/10/2018.
 */

public class Circle {

    public Vector2 center;
    public float radius;

    public Circle(float x, float y, float radius){
        this.center = new Vector2(x, y);
        this.radius = radius;
    }

}
