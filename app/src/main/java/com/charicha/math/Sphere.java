package com.charicha.math;

/**
 * Created by Charicha on 1/23/2018.
 */

public class Sphere {

    public Vector3 center = new Vector3();
    public float radius;

    public Sphere(float x, float y, float z, float radius){
        center.set(x, y, z);
        this.radius = radius;
    }


}
