package com.charicha.game;

/**
 * Created by Charicha on 1/12/2018.
 */

public class Animation {

    public TextureRegion[] allFrames;
    public float frameDuration = 0;

    public Animation(float frameDuration, TextureRegion... keyFrames){
        this.frameDuration = frameDuration;
        this.allFrames = keyFrames;
    }

    public TextureRegion getKeyFrame(float stateTime, boolean isLoop){
        int frameIndex = (int) (stateTime/frameDuration);
        if(isLoop)
            return allFrames[frameIndex % allFrames.length];
        else
            return allFrames[Math.min(allFrames.length - 1, frameIndex)];
    }

}
