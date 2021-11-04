package com.charicha.impl;

import com.charicha.Game;
import com.charicha.Screen;

/**
 * Created by Charicha on 1/13/2018.
 */

public abstract class GLScreen extends Screen {

    protected GLGame glGame;
    protected GLGraphics glGraphics;

    public GLScreen(Game game) {
        super(game);
        this.glGame = (GLGame)game;
        this.glGraphics = ((GLGame)game).getGLGraphics();
    }
}
