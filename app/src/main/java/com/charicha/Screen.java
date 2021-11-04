package com.charicha;

/**
 * Created by Charicha on 12/28/2017.
 */

public abstract class Screen {

    protected Game mGame;

    public Screen(Game game){
        this.mGame = game;
    }

    public abstract void update(float deltaTime);

    public abstract void render(float deltaTime);

    public abstract void resume();

    public abstract void pause();

    public abstract void dispose();

}
