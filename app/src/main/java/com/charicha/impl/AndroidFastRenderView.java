package com.charicha.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.charicha.Game;

/**
 * Created by Charicha on 12/28/2017.
 */

public class AndroidFastRenderView extends SurfaceView implements Runnable{

    Game mGame;
    SurfaceHolder mSurfaceHolder;
    Bitmap mFrameBuffer;
    Thread mRenderThread = null;
    volatile boolean running = false;

    public AndroidFastRenderView(Game game, Bitmap frameBuffer){
        super((Context)game);
        mGame = game;
        mSurfaceHolder = getHolder();
        mFrameBuffer = frameBuffer;
    }

    public void resume(){
        running = true;
        mRenderThread = new Thread(this);
        mRenderThread.start();
    }

    @Override
    public void run(){
        Canvas canvas;
        long previousTime = System.nanoTime();
        while(running){
            if(!mSurfaceHolder.getSurface().isValid())
                continue;
            float deltaTime = (System.nanoTime() - previousTime) / 1000000000f;
            previousTime = System.nanoTime();

            mGame.getCurrentScreen().update(deltaTime);
            mGame.getCurrentScreen().render(deltaTime);

            canvas = mSurfaceHolder.lockCanvas();
            canvas.drawBitmap(mFrameBuffer, null, canvas.getClipBounds(), null);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause(){
        running = false;
        while(true){
            try {
                mRenderThread.join();
                return;
            } catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

}
