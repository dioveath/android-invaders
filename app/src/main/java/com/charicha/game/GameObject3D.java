package com.charicha.game;

import com.charicha.math.Sphere;
import com.charicha.math.Vector3;

/**
 * Created by Charicha on 1/23/2018.
 */

public class GameObject3D {

    public final Vector3 position = new Vector3();
    public final Sphere boundSphere;

    public GameObject3D(float x, float y, float z, float radius){
        position.set(x, y, z);
        this.boundSphere = new Sphere(x, y, z, radius);
    }

}
