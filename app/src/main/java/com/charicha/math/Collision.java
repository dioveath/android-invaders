package com.charicha.math;

/**
 * Created by Charicha on 1/10/2018.
 */

public class Collision {

    public static boolean isColliding(Circle c1, Rectangle r1){
        float closestx = c1.center.x;
        float closesty = c1.center.y;

        if(closestx < r1.lowerLeft.x){
            closestx = r1.lowerLeft.x;
        } else if(closestx > r1.lowerLeft.x + r1.width) {
            closestx = r1.lowerLeft.x + r1.width;
        }

        if(closesty < r1.lowerLeft.y){
            closesty = r1.lowerLeft.y;
        } else if(closesty > r1.lowerLeft.y + r1.height){
            closesty = r1.lowerLeft.y + r1.height;
        }
        return c1.center.distSquared(closestx, closesty) <= (c1.radius * c1.radius);
    }

    public static boolean isColliding(Rectangle r1, Rectangle r2){
        if(r1.lowerLeft.x <= r2.lowerLeft.x + r2.width &&
                r1.lowerLeft.x + r1.width >= r2.lowerLeft.x &&
                r1.lowerLeft.y <= r2.lowerLeft.y + r2.height &&
                r1.lowerLeft.y + r1.height >= r2.lowerLeft.y)
            return true;
        return false;
    }

    public static boolean overlapCircles(Circle c1, Circle c2){
        float radiusSum = c1.radius + c2.radius;
        return c1.center.distSquared(c2.center) <= (radiusSum * radiusSum);
    }

    public static boolean pointInRect(float x, float y, Rectangle r){
        if(x >= r.lowerLeft.x && x <= r.lowerLeft.x + r.width && y >= r.lowerLeft.y && y <= r.lowerLeft.y + r.height)
            return true;
        return false;
    }

    public static boolean pointInRect(Vector2 point, Rectangle r){
        if(point.x >= r.lowerLeft.x && point.x <= r.lowerLeft.x + r.width && point.y >= r.lowerLeft.y && point.y <= r.lowerLeft.y + r.height)
            return true;
        return false;
    }

    public static boolean pointInCircle(float x, float y, Circle c){
        return c.center.distSquared(x, y) <= (c.radius * c.radius);
    }

    public static boolean pointInCircle(Vector2 point, Circle c){
        return c.center.distSquared(point.x, point.y) <= (c.radius * c.radius);
    }

    public static boolean overlapSpheres(Sphere s1, Sphere s2){
        float distSquared = s2.center.distSquared(s1.center);
        float radii = s1.radius + s2.radius;
        return distSquared <= (radii * radii);
    }

    public static boolean pointInSphere(Vector3 point, Sphere sphere){
        float distSquared = point.distSquared(sphere.center);
        return (distSquared < (sphere.radius * sphere.radius));
    }

    public static boolean pointInSphere(float x, float y, float z, Sphere sphere){
        float distSquared = sphere.center.distSquared(x, y, z);
        return (distSquared < (sphere.radius * sphere.radius));
    }

}
