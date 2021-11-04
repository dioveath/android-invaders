package com.charicha.androidinvaders;

import com.charicha.game.DynamicObject3D;

/**
 * Created by Charicha on 1/24/2018.
 */

public class Shot extends DynamicObject3D {

    static final float SHOT_VELOCITY = 10f;
    static final float SHOT_RADIUS = 0.1f;

    public Shot(float x, float y, float z, float velocityZ){
        super(x, y, z, SHOT_RADIUS);
        velocity.z = velocityZ;
    }

    public void update(float deltaTime){
        position.z += velocity.z * deltaTime;
        boundSphere.center.set(position);
    }

}
