package com.charicha.impl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.charicha.Audio;
import com.charicha.FileIO;
import com.charicha.Game;
import com.charicha.Graphics;
import com.charicha.Input;
import com.charicha.Screen;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/1/2018.
 */

public abstract class GLGame extends Activity implements Game, GLSurfaceView.Renderer{

    enum GLGameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }

    GLSurfaceView mGLSurfaceView;
    GLGraphics mGLGraphics;
    Audio mAudio;
    Input mInput;
    FileIO mFileIO;
    Screen mCurrentScreen;

    GLGameState mCurrentState = GLGameState.Initialized;
    Object stateChanged = new Object(); //To synchronize the UI and rendering threads.
    long startTime = System.nanoTime();

    @Override
    public void onCreate(Bundle savedInstanceState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setRenderer(this);

        mGLGraphics = new GLGraphics(mGLSurfaceView);
        mAudio = new AndroidAudio(this);
        mInput = new AndroidInput(this, mGLSurfaceView, 1, 1);
        mFileIO = new AndroidFileIO(this);

        setContentView(mGLSurfaceView);
    }

    @Override
    public void onResume(){
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    public void onPause(){
        synchronized(stateChanged){
            mCurrentState = GLGameState.Paused;
            if(isFinishing()){
                mCurrentState = GLGameState.Finished;
            }
            while(true){
                try {
                    stateChanged.wait();
                    break;
                } catch(InterruptedException ie){
                    ie.printStackTrace();
                }
            }
        }
        mGLSurfaceView.onPause();
        super.onPause();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        mGLGraphics.setGL(gl10);
        synchronized (stateChanged){
            if(mCurrentState == GLGameState.Initialized)
                mCurrentScreen = getStartScreen();
            mCurrentState = GLGameState.Running;
            mCurrentScreen.resume();
            startTime = System.nanoTime();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLGameState state = null;
        synchronized (this){
            state = mCurrentState;
        }
        if(state == GLGameState.Running){
            float deltaTime = (System.nanoTime() - startTime) / 1000000000f;
            startTime = System.nanoTime();
            mCurrentScreen.update(deltaTime);
            mCurrentScreen.render(deltaTime);
        }
        if(state == GLGameState.Paused){
            mCurrentScreen.pause();
            synchronized (stateChanged){
                this.mCurrentState = GLGameState.Idle;
                stateChanged.notify();
            }
        }
        if(state == GLGameState.Finished){
            mCurrentScreen.pause();
            mCurrentScreen.dispose();
            synchronized (stateChanged){
                this.mCurrentState = GLGameState.Idle;
                stateChanged.notify();
            }
        }
    }

    @Override
    public Graphics getGraphics() {
        throw new IllegalStateException("We are using OpenGL!");
    }

    public GLGraphics getGLGraphics(){
        return mGLGraphics;
    }

    @Override
    public Input getInput() {
        return mInput;
    }

    @Override
    public FileIO getFileIO() {
        return mFileIO;
    }

    @Override
    public Audio getAudio() {
        return mAudio;
    }

    @Override
    public void changeCurrentScreen(Screen screen) {
        if(screen == null)
            throw new RuntimeException("Screen can't be null");
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
}
