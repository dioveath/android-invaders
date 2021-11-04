package com.charicha;

/**
 * Created by Charicha on 12/28/2017.
 */

public interface Game {

    public Graphics getGraphics();

    public Input getInput();

    public FileIO getFileIO();

    public Audio getAudio();

    public void changeCurrentScreen(Screen screen);

    public Screen getStartScreen();

    public Screen getCurrentScreen();

}
