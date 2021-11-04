package com.charicha;

/**
 * Created by Charicha on 12/29/2017.
 */

public interface Music {

    public void play();

    public void pause();

    public void setLooping(boolean looping);

    public void setVolume(float volume);

    public boolean isPlaying();

    public boolean isLooping();

    public boolean isStopped();

    public void dispose();

}
