package com.charicha.game;

import com.charicha.math.Vector3;

/**
 * Created by Charicha on 1/23/2018.
 */

public class DynamicObject3D extends GameObject3D{

    public final Vector3 velocity;
    public final Vector3 accel;

    public DynamicObject3D(float x, float y, float z, float radius){
        super(x, y, z, radius);
        velocity = new Vector3();
        accel = new Vector3();
    }

}
