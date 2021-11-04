package com.charicha.game;

import com.charicha.impl.GLGraphics;
import com.charicha.math.Vector2;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/11/2018.
 */

public class Camera2D {

    GLGraphics glGraphics;
    GL10 gl;
    public float x, y;
    public float scalex, scaley;
    public final float frustumWidth, frustumHeight;

    public Camera2D(GLGraphics glGraphics, float frustumWidth, float frustumHeight){
        this(glGraphics, frustumWidth/2, frustumHeight/2, frustumWidth, frustumHeight);
    }

    public Camera2D(GLGraphics glGraphics, float x, float y, float frustumWidth, float frustumHeight){
        this.glGraphics = glGraphics;
        this.gl = glGraphics.getGL();
        this.x = x;
        this.y = y;
        this.frustumWidth = frustumWidth;
        this.frustumHeight = frustumHeight;
        this.scalex = 1;
        this.scaley = 1;
    }

    public void screenToWorldPoint(Vector2 touch){
        touch.x = (touch.x/(float) glGraphics.getWidth()) * frustumWidth * scalex + this.x - frustumWidth * scalex/2;
        touch.y = (1 - (touch.y / (float) glGraphics.getHeight())) * frustumHeight * scaley + this.y - frustumHeight * scaley/2;
    }

    public void setOrthoProjection(){
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(this.x - frustumWidth/2 * scalex,
                this.x + frustumWidth/2 * scalex,
                this.y - frustumHeight/2 * scaley,
                this.y + frustumHeight/2 * scaley,
                -1, 1);
    }

}
