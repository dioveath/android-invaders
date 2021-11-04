package com.charicha.androidinvaders;

import android.util.Log;

/**
 * Created by Charicha on 1/24/2018.
 */

public class FPSCounter {

    static int frames;
    static long prevTime;
    static int FPS;

    public static void logFrame(){
        frames++;
        if(System.nanoTime() - prevTime >= 1000000000){
            Log.d("FPS: ", "" + frames);
            FPS = frames;
            frames = 0;
            prevTime = System.nanoTime();
        }
    }

}
