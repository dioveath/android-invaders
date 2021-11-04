package com.charicha.impl;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.charicha.Audio;
import com.charicha.FileIO;
import com.charicha.Game;
import com.charicha.Graphics;
import com.charicha.Input;
import com.charicha.Screen;

/**
 * Created by Charicha on 12/28/2017.
 */

public abstract class AndroidGame extends Activity implements Game{

    AndroidFastRenderView mRenderView;
    AndroidGraphics mGraphics;
    AndroidInput mInput;
    AndroidFileIO mFileIO;
    AndroidAudio mAudio;

    Bitmap mFrameBuffer;
    Screen mCurrentScreen;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int bufferWidth = isLandscape ? 1280 : 720;
        int bufferHeight = isLandscape ? 720 : 1280;

        mFrameBuffer = Bitmap.createBitmap(bufferWidth, bufferHeight, Bitmap.Config.RGB_565);

        float scaleX = (float)bufferWidth / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float)bufferHeight / getWindowManager().getDefaultDisplay().getHeight();

        mRenderView = new AndroidFastRenderView(this, mFrameBuffer);
        mGraphics = new AndroidGraphics(this, mFrameBuffer);
        mInput = new AndroidInput(this, mRenderView, scaleX, scaleY);
        mFileIO = new AndroidFileIO(this);
        mAudio = new AndroidAudio(this);

        mCurrentScreen = getStartScreen();
        setContentView(mRenderView);
    }

    @Override
    public void onResume(){
        super.onResume();
        mCurrentScreen.resume();
        mRenderView.resume();
    }

    @Override
    public void onPause(){
        mCurrentScreen.pause();
        mRenderView.pause();
        super.onPause();

        if(isFinishing())
            mCurrentScreen.dispose();
    }

    @Override
    public Graphics getGraphics() {
        return mGraphics;
    }

    @Override
    public Input getInput() {
        return mInput;
    }

    @Override
    public FileIO getFileIO(){
        return mFileIO;
    }

    @Override
    public Audio getAudio(){
        return mAudio;
    }

    @Override
    public void changeCurrentScreen(Screen screen) {
        if(screen == null)
            throw new RuntimeException("Null Screen Error!");
        mCurrentScreen.pause();
        mCurrentScreen.dispose();
        screen.resume();
        screen.update(0);
        mCurrentScreen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return mCurrentScreen;
    }

    @Override
    public abstract Screen getStartScreen();

}
