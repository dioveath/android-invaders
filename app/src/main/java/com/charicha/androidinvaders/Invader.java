package com.charicha.androidinvaders;

import com.charicha.game.DynamicObject3D;

/**
 * Created by Charicha on 1/24/2018.
 */

public class Invader extends DynamicObject3D {

    static final float INVADER_RADIUS = 0.75f;
    static final float INVADER_EXPLOSION_TIME = 1.6f;
    static final float INVADER_VELOCITY = 1;

    enum State {Alive, Dead};
    enum MovementState {Left, Down, Right};

    State currentState = State.Alive;
    MovementState movementState = MovementState.Left;

    boolean wasLastMStateLeft = true;
    float movedDistance = World.WORLD_MAX_X/2;

    float internalStateTime = 0;

    public Invader(float x, float y, float z){
        super(x, y, z, INVADER_RADIUS);
    }

    public void update(float deltaTime, float multiplier){
        if(currentState == State.Alive){
            movedDistance += deltaTime * INVADER_VELOCITY * multiplier;
            switch(movementState){
                case Left:
                    position.x -= deltaTime * INVADER_VELOCITY * multiplier;
                    if(movedDistance > World.WORLD_MAX_X){
                        movementState = MovementState.Down;
                        movedDistance = 0;
                        wasLastMStateLeft = true;
                    }
                    break;
                case Right:
                    position.x += deltaTime * INVADER_VELOCITY * multiplier;
                    if(movedDistance > World.WORLD_MAX_X){
                        movementState = MovementState.Down;
                        movedDistance = 0;
                        wasLastMStateLeft = false;
                    }
                    break;
                case Down:
                    position.z += deltaTime * INVADER_VELOCITY * multiplier;
                    if(movedDistance > 1){
                        if(wasLastMStateLeft)
                            movementState = MovementState.Right;
                        else
                            movementState = MovementState.Left;
                        movedDistance = 0;
                    }
                    break;
            }
            boundSphere.center.set(position);
        }
        internalStateTime += deltaTime;
    }

    public void kill(){
        currentState = State.Dead;
        internalStateTime = 0;
    }

}
