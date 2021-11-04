package com.charicha.androidinvaders;

import com.charicha.game.DynamicObject3D;

/**
 * Created by Charicha on 1/24/2018.
 */

public class Ship extends DynamicObject3D{

    static final float SHIP_RADIUS = 1f;
    static final float SHIP_VELOCITY = 10f;
    static final float SHIP_EXPLOSION_TIME = 1.6f;

    enum State {Alive, Exploding};


    State currentState = State.Alive;
    int lives = 3;
    float internalStateTime = 0;


    public Ship(float x, float y, float z){
        super(x, y, z, SHIP_RADIUS);
    }

    public void update(float deltaTime, float accely){
        switch (currentState){
            case Alive:
                velocity.set(accely/10 * SHIP_VELOCITY, 0, 0);
                position.add(velocity.x * deltaTime, 0, 0);

                if(position.x < World.WORLD_MIN_X)
                    position.x = World.WORLD_MIN_X;
                else if(position.x > World.WORLD_MAX_X)
                    position.x = World.WORLD_MAX_X;

                boundSphere.center.set(position);
                break;
            case Exploding:
                if(internalStateTime >= SHIP_EXPLOSION_TIME){
                    lives--;
                    internalStateTime = 0;
                    currentState = State.Alive;
                }
                break;
        }
        internalStateTime += deltaTime;
    }

    public void kill(){
        internalStateTime = 0;
        currentState = State.Exploding;
        velocity.x = 0;
    }

}
