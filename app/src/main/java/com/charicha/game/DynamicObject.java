package com.charicha.game;

import com.charicha.math.Vector2;

/**
 * Created by Charicha on 1/10/2018.
 */

public class DynamicObject extends GameObject {

    /***
     If a final variable holds a reference to an object, then the state of the object may be changed by operations on the
     object, but the variable will always refer to the same object (this property of final is called non-transitivity).
     ***/


    public final Vector2 velocity;
    public final Vector2 accel;

    public DynamicObject(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2(0, 0);
        accel = new Vector2(0, 0);
    }
}
