package com.charicha.androidinvaders;

import com.charicha.game.GameObject3D;

/**
 * Created by Charicha on 1/24/2018.
 */

public class Shield extends GameObject3D{

    static float SHIELD_RADIUS = 0.5f;

    public Shield(float x, float y, float z){
        super(x, y, z, SHIELD_RADIUS);
    }

}
