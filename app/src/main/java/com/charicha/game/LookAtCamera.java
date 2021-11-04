package com.charicha.game;

import android.opengl.GLU;

import com.charicha.math.Vector3;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/22/2018.
 */

public class LookAtCamera {

    public final Vector3 position;
    public final Vector3 lookAt;
    public final Vector3 upVec;
    public float fieldOfView;
    public float aspectRatio;
    public float zNear, zFar;

    public LookAtCamera(float fieldOfView, float aspectRatio, float zNear, float zFar){
        this.fieldOfView = fieldOfView;
        this.aspectRatio = aspectRatio;
        this.zNear = zNear;
        this.zFar = zFar;
        this.position = new Vector3();
        this.lookAt = new Vector3(0, 0, -1);
        this.upVec = new Vector3(0, 1, 0);
    }

    public void setMatrices(GL10 gl){
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, fieldOfView, aspectRatio, zNear, zFar);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, position.x, position.y, position.z, lookAt.x, lookAt.y, lookAt.z, upVec.x, upVec.y, upVec.z);

    }

}
