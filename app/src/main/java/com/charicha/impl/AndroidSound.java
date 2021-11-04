package com.charicha.impl;

import android.media.SoundPool;

import com.charicha.Sound;

/**
 * Created by Charicha on 12/29/2017.
 */

public class AndroidSound implements Sound{

    int soundID = -1;
    SoundPool mSoundPool;

    public AndroidSound(SoundPool soundPool, int soundID){
        mSoundPool = soundPool;
        this.soundID = soundID;
    }

    @Override
    public void play(float volume){
        mSoundPool.play(soundID, volume, volume, 0, 0, 1);
    }

    @Override
    public void dispose(){
        mSoundPool.unload(soundID);
    }


}
