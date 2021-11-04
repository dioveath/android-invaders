package com.charicha.game;

import android.opengl.GLU;
import android.opengl.Matrix;

import com.charicha.math.Vector3;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/23/2018.
 */

public class EulerCamera {

    public Vector3 position;
    public float fieldOfView;
    public float aspectRatio;
    public float zNear, zFar;
    public float pitch, yaw;

    public EulerCamera(float fieldOfView, float aspectRatio, float zNear, float zFar){
        this.fieldOfView = fieldOfView;
        this.aspectRatio = aspectRatio;
        this.zNear = zNear;
        this.zFar = zFar;
        this.position = new Vector3(0, 0, 0);
    }

    public void setCameraRotation(float pitch, float yaw){
        this.pitch = pitch;
        if(this.pitch > 90) {
            this.pitch = 90;
        }
        if(this.pitch < -90) {
            this.pitch = -90;
        }
        this.yaw = yaw;
    }

    public void rotateCamera(float pitch, float yaw){
        this.pitch += pitch;
        if(this.pitch > 90) {
            this.pitch = 90;
        }
        if(this.pitch < -90) {
            this.pitch = -90;
        }
        this.yaw += yaw;
    }

    public void setMatrices(GL10 gl){
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, fieldOfView, aspectRatio, zNear, zFar);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glRotatef(-pitch, 1, 0, 0);
        gl.glRotatef(-yaw, 0, 1, 0);
        gl.glTranslatef(-position.x, -position.y, -position.z);
    }


    final float[] matrix = new float[16];
    final float[] inVec = {0, 0, -1, 1};
    final float[] outVec = new float[4];
    final Vector3 direction = new Vector3(0, 0, -1);

    public Vector3 getDirection(){
        Matrix.setIdentityM(matrix, 0);
        Matrix.rotateM(matrix, 0, yaw, 0, 1, 0);
        Matrix.rotateM(matrix, 0, pitch, 1, 0, 0);
        Matrix.multiplyMV(outVec, 0, matrix, 0, inVec, 0);
        return direction.set(outVec[0], outVec[1], outVec[2]);
    }



}
