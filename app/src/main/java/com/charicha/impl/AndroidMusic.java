package com.charicha.impl;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.charicha.Music;

import java.io.IOException;

/**
 * Created by Charicha on 12/29/2017.
 */

public class AndroidMusic implements Music, MediaPlayer.OnCompletionListener{


    boolean isPrepared = false;
    MediaPlayer mediaPlayer;

    public AndroidMusic(AssetFileDescriptor assetFileDescriptor, boolean isLoop){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.setLooping(isLoop);
            mediaPlayer.prepare();
            isPrepared = true;
        } catch(IOException ioe){
            throw new RuntimeException("Couldn't load the music " + assetFileDescriptor.toString());
        }
    }

    @Override
    public void play() {
        if(mediaPlayer.isPlaying()) {
            return;
        }
        try {
            synchronized (this){
                if(!isPrepared)
                    mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch(IllegalStateException ise){
            ise.printStackTrace();
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public void pause() {
        if(isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    public void setLooping(boolean looping){
        mediaPlayer.setLooping(looping);
    }

    @Override
    public void setVolume(float volume){
        mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    @Override
    public boolean isStopped() {
        synchronized (this){
            return !isPrepared;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        synchronized (this){
            if(!mediaPlayer.isLooping())
                isPrepared = false;
        }
    }

    @Override
    public void dispose(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
    }
}
