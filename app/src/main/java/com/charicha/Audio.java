package com.charicha;

/**
 * Created by Charicha on 12/29/2017.
 */

public interface Audio {

    public Music newMusic(String fileName, boolean isLoop);

    public Sound newSound(String fileName);

}
