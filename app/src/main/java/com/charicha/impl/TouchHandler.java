package com.charicha.impl;

import android.view.MotionEvent;
import android.view.View;

import com.charicha.Input;

import java.util.ArrayList;
import java.util.List;

import static com.charicha.Input.TouchEvent.TOUCH_DOWN;
import static com.charicha.Input.TouchEvent.TOUCH_DRAGGED;
import static com.charicha.Input.TouchEvent.TOUCH_UP;

/**
 * Created by Charicha on 12/28/2017.
 */

public class TouchHandler implements View.OnTouchListener {

    public final int MAX_TOUCHPOINTS = 10;

    int[] x = new int[MAX_TOUCHPOINTS];
    int[] y = new int[MAX_TOUCHPOINTS];
    int[] pointerIds = new int[MAX_TOUCHPOINTS];
    boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];

    List<Input.TouchEvent> mTouchEvents = new ArrayList<>();
    List<Input.TouchEvent> mTouchEventsBuffer = new ArrayList<>();
    float scaleX;
    float scaleY;

    Pool<Input.TouchEvent> mTouchEventPool;

    public TouchHandler(View view, float scaleX, float scaleY){
        view.setOnTouchListener(this);
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        Pool.PoolObjectFactory<Input.TouchEvent> factory = new Pool.PoolObjectFactory<Input.TouchEvent>() {
            @Override
            public Input.TouchEvent createObject() {
                return new Input.TouchEvent();
            }
        };
        mTouchEventPool = new Pool<>(factory, 100);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        synchronized (this){
            int maskedAction = (motionEvent.getAction() & MotionEvent.ACTION_MASK);
            int pointerIndex = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

            int pointerCount = motionEvent.getPointerCount();
            Input.TouchEvent touchEvent;
            for(int i = 0; i < MAX_TOUCHPOINTS; i++){
                if(i >= pointerCount){
                    isTouched[i] = false;
                    pointerIds[i] = -1;
                    x[i] = 0;
                    y[i] = 0;
                    continue;
                }

                if(maskedAction != MotionEvent.ACTION_MOVE && i != pointerIndex){
                    continue;
                }

                switch(maskedAction){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchEvent = mTouchEventPool.getObject();
                        isTouched[i] = true;
                        touchEvent.type = TOUCH_DOWN;
                        x[i] = touchEvent.x = (int) (motionEvent.getX(i) * scaleX);
                        y[i] = touchEvent.y = (int) (motionEvent.getY(i) * scaleY);
                        pointerIds[i] = motionEvent.getPointerId(i);
                        mTouchEventsBuffer.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE:
                        touchEvent = mTouchEventPool.getObject();
                        isTouched[i] = false;
                        touchEvent.type = TOUCH_UP;
                        x[i] = touchEvent.x = (int) (motionEvent.getX(i) * scaleX);
                        y[i] = touchEvent.y = (int) (motionEvent.getY(i) * scaleY);
                        pointerIds[i] = -1;
                        mTouchEventsBuffer.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchEvent = mTouchEventPool.getObject();
                        isTouched[i] = true;
                        touchEvent.type = TOUCH_DRAGGED;
                        x[i] = touchEvent.x = (int) (motionEvent.getX(i) * scaleX);
                        y[i] = touchEvent.y = (int) (motionEvent.getY(i) * scaleX);
                        pointerIds[i] = motionEvent.getPointerId(i);
                        mTouchEventsBuffer.add(touchEvent);
                        break;
                }
            }

        }
        return true;
    }


    public List<Input.TouchEvent> getTouchEvents(){
        synchronized (this){
            int len = mTouchEvents.size();
            for(int i = 0; i < len; i++){
                mTouchEventPool.addFreeObject(mTouchEvents.get(i));
            }
            mTouchEvents.clear();
            mTouchEvents.addAll(mTouchEventsBuffer);
            mTouchEventsBuffer.clear();
            return mTouchEvents;
        }
    }

    public int getX(int id){
        synchronized (this){
            int index = getIndex(id);
            if(index >= 0 && index < MAX_TOUCHPOINTS){
                return x[index];
            }
            return 0;
        }
    }

    public int getY(int id){
        synchronized (this){
            int index = getIndex(id);
            if(index >= 0 && index < MAX_TOUCHPOINTS)
                return y[index];
            return 0;
        }
    }

    public boolean isTouchdown(int id){
        synchronized (this){
            int index = getIndex(id);
            if(index >= 0 && index < MAX_TOUCHPOINTS)
                return isTouched[index];
            return false;
        }
    }

    public int getIndex(int id){
        for(int i = 0; i < 10; i++){
            if(id == pointerIds[i]) {
                return i;
            }
        }
        return -1;
    }
}
