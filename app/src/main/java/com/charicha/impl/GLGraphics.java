package com.charicha.impl;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 12/31/2017.
 */

public class GLGraphics  {

    GLSurfaceView glView;
    GL10 gl;

    public GLGraphics(GLSurfaceView glView){
        this.glView = glView;
    }

    public GL10 getGL(){
        return gl;
    }

    public void setGL(GL10 gl){
        this.gl = gl;
    }

    public int getWidth(){
        return glView.getWidth();
    }

    public int getHeight(){
        return glView.getHeight();
    }

}
