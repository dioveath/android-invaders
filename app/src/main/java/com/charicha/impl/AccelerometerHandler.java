package com.charicha.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Charicha on 1/15/2018.
 */

public class AccelerometerHandler implements SensorEventListener{

    float x;
    float y;
    float z;

    public AccelerometerHandler(Context context){
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0){
            Sensor accelerometer = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public float getAccelX(){
        return x;
    }

    public float getAccelY(){
        return y;
    }

    public float getAccelZ(){
        return z;
    }
}
