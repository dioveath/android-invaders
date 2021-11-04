package com.charicha.game;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/20/2018.
 */

public class AmbientLight {

    public float[] rgba = {0.2f, 0.2f, 0.2f, 1f};

    public AmbientLight(){
    }

    public AmbientLight(float r, float g, float b, float a){
        rgba[0] = r;
        rgba[1] = g;
        rgba[2] = b;
        rgba[3] = a;
    }

    public void setColor(float r, float g, float b, float a){
        rgba[0] = r;
        rgba[1] = g;
        rgba[2] = b;
        rgba[3] = a;
    }

    public void enable(GL10 gl){
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, rgba, 0);
    }

}
