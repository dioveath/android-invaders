package com.charicha.math;

/**
 * Created by Charicha on 1/9/2018.
 */

public class Vector2 {

    public static final float TO_RADIANS = (1 / 180) * (float) Math.PI;
    public static final float TO_DEGREES = (1 / (float) Math.PI) * 180;

    public float x, y;

    public Vector2(){
    }

    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 other){
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2 set(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2 set(Vector2 other){
        this.x = other.x;
        this.y = other.y;
        return this;
    }

    public float length(){
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public float angle(){
        return (float) Math.atan2(this.y, this.x);
    }

    public Vector2 setLength(float length){
        float angle = this.angle();
        this.x = (float) Math.cos(angle) * length;
        this.y = (float) Math.sin(angle) * length;
        return this;
    }

    public Vector2 setAngle(float angle){
        float length = this.length();
        this.x = (float) Math.cos(angle) * length;
        this.y = (float) Math.sin(angle) * length;
        return this;
    }

    public Vector2 add(float x, float y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2 add(Vector2 other){
        this.x += other.x;
        this.y += other.y;
        return this;
    }

   public Vector2 subtract(float x, float y){
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2 subtract(Vector2 other){
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2 scalarMul(float scal){
        this.x *= scal;
        this.y *= scal;
        return this;
    }

    public Vector2 normSelf(){
        float len = length();
        if(len != 0){
            this.x /= len;
            this.y /= len;
        }
        return this;
    }
//    :: Was thinking it was good to have the norm function returning a new Vector2 rather than operating and
//    :: retuning self
//    public Vector2 normNew(){
//        float len = length();
//        Vector2 temp = new Vector2(0, 0);
//        if(len != 0){
//            temp.x = this.x/len;
//            temp.y = this.y/len;
//        }
//        return temp;
//    }

    public Vector2 rotate(float angle){
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        float newx = this.x * cos - sin * this.y;
        float newy = this.x * sin + cos * this.y;

        this.x = newx;
        this.y = newy;
        return this;
    }

    public float dist(float x, float y){
        float dx = x - this.x;
        float dy = y - this.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public float dist(Vector2 other){
        float dx = other.x - this.x;
        float dy = other.y - this.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public float distSquared(float x, float y){
        float dx = x - this.x;
        float dy = y - this.y;
        return dx * dx + dy * dy;
    }

    public float distSquared(Vector2 other){
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        return dx * dx + dy * dy;
    }

}
