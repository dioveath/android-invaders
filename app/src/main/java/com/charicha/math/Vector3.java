package com.charicha.math;

import android.opengl.Matrix;

/**
 * Created by Charicha on 1/20/2018.
 */

public class Vector3 {

    private static final float[] matrix = new float[16];
    private static final float[] inVec = new float[4];
    private static final float[] outVec = new float[4];

    public float x, y, z;

    public Vector3(){
    }

    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 other){
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public Vector3 copy(){
        return new Vector3(x, y, z);
    }

    public Vector3 set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3 set(Vector3 other){
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        return this;
    }

    public Vector3 add(Vector3 other){
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public Vector3 add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3 subtract(Vector3 other){
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public Vector3 subtract(float x, float y, float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3 scalarMul(float value){
        this.x *= value;
        this.y *= value;
        this.z *= value;
        return this;
    }

    public float length(){
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3 norm(){
        float len = length();
        if(len != 0){
            this.x /= len;
            this.y /= len;
            this.z /= len;
        }
        return this;
    }

    public float dist(Vector3 other){
        float dx = other.x - this.x;
        float dy = other.y - this.y;
        float dz = other.z - this.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public float dist(float x, float y, float z){
        float dx = x - this.x;
        float dy = y - this.y;
        float dz = z - this.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public float distSquared(Vector3 other){
        float dx = other.x - this.x;
        float dy = other.y - this.y;
        float dz = other.z - this.z;
        return (dx * dx + dy * dy + dz * dz);
    }

    public float distSquared(float x, float y, float z){
        float dx = x - this.x;
        float dy = y - this.y;
        float dz = z - this.z;
        return (dx * dx + dy * dy + dz * dz);
    }

    public Vector3 rotate(float angle, float xAxis, float yAxis, float zAxis){
        inVec[0] = x;
        inVec[1] = y;
        inVec[2] = z;
        inVec[3] = 1;
        Matrix.setIdentityM(matrix, 0);
        Matrix.rotateM(matrix, 0, angle, xAxis, yAxis, zAxis);
        Matrix.multiplyMV(outVec, 0, matrix, 0, inVec, 0);
        x = outVec[0];
        y = outVec[1];
        z = outVec[2];
        return this;
    }



}
