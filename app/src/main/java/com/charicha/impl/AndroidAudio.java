package com.charicha.impl;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.charicha.Audio;
import com.charicha.Music;
import com.charicha.Sound;

import java.io.IOException;

/**
 * Created by Charicha on 12/29/2017.
 */

public class AndroidAudio implements Audio {

    AssetManager mAssetManager;
    SoundPool mSoundPool;

    public AndroidAudio(Activity activity){
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mAssetManager = activity.getAssets();
        mSoundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

    }

    public Sound newSound(String fileName){
        try {
            AssetFileDescriptor afDescriptor = mAssetManager.openFd(fileName);
            int id = mSoundPool.load(afDescriptor, 1);
            return new AndroidSound(mSoundPool, id);
        } catch(IOException ioe){
            throw new RuntimeException("Couldn't load the sound, '" + fileName + "': " + ioe.getMessage());
        }
    }

    public Music newMusic(String fileName, boolean isLoop){
        try {
            AssetFileDescriptor afDescriptor = mAssetManager.openFd(fileName);
            return new AndroidMusic(afDescriptor, isLoop);
        } catch(IOException ioe){
            throw new RuntimeException("Couldn't load the music, '" + fileName + "': " + ioe.getMessage());
        }
    }

}
