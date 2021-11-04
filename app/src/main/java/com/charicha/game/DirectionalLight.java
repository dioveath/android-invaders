package com.charicha.game;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/21/2018.
 */

public class DirectionalLight {

    private final float[] ambient = {0.2f, 0.2f, 0.2f, 1f};
    private final float[] diffuse = {1f, 1f, 1f, 1f};
    private final float[] specular = {0f, 0f, 0f, 1f};
    private final float[] direction = {1, 0, 0, 0};
    private int lastLightId;

    public void setAmbient(float r, float g, float b, float a){
        ambient[0] = r;
        ambient[1] = g;
        ambient[2] = b;
        ambient[3] = a;
    }

    public void setDiffuse(float r, float g, float b, float a){
        diffuse[0] = r;
        diffuse[1] = g;
        diffuse[2] = b;
        diffuse[3] = a;
    }

    public void setSpecular(float r, float g, float b, float a){
        specular[0] = r;
        specular[1] = g;
        specular[2] = b;
        specular[3] = a;
    }

    public void setDirection(float x, float y, float z){ //Liked the OpenGLES way// so no negating
        direction[0] = x;
        direction[1] = y;
        direction[2] = z;
    }

    public void enable(GL10 gl, int lightId){
        gl.glEnable(lightId);
        gl.glLightfv(lightId, GL10.GL_AMBIENT, ambient, 0);
        gl.glLightfv(lightId, GL10.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(lightId, GL10.GL_SPECULAR, specular, 0);
        gl.glLightfv(lightId, GL10.GL_POSITION, direction, 0);
        lastLightId = lightId;
    }

    public void disable(GL10 gl){
        gl.glDisable(lastLightId);
    }

}
