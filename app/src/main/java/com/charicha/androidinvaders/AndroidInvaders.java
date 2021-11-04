package com.charicha.androidinvaders;

import com.charicha.Screen;
import com.charicha.impl.GLGame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/23/2018.
 */

public class AndroidInvaders extends GLGame{

    boolean firstTime = true;

    @Override
    public Screen getStartScreen(){
        return new PlayScreen(this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);
        if(firstTime){
            firstTime = false;
            Assets.load(this);
        } else {
            Assets.reload();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(Settings.soundEnabled)
            Assets.music.pause();
    }


}
