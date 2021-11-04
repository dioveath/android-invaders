package com.charicha.impl;

import android.content.Context;
import android.view.View;

import com.charicha.Input;

import java.util.List;

/**
 * Created by Charicha on 12/28/2017.
 */

public class AndroidInput implements Input {

    TouchHandler mTouchHandler;
    AccelerometerHandler accelerometerHandler;

    public AndroidInput(Context context, View view, float scaleX, float scaleY){
        mTouchHandler = new TouchHandler(view, scaleX, scaleY);
        accelerometerHandler = new AccelerometerHandler(context);
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return mTouchHandler.getTouchEvents();
    }

    @Override
    public float getAccelX() {
        return accelerometerHandler.getAccelX();
    }

    @Override
    public float getAccelY() {
        return accelerometerHandler.getAccelY();
    }

    @Override
    public float getAccelZ() {
        return accelerometerHandler.getAccelZ();
    }
}
