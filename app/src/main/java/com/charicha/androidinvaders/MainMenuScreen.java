package com.charicha.androidinvaders;

import com.charicha.Game;
import com.charicha.Input;
import com.charicha.game.Camera2D;
import com.charicha.game.SpriteBatcher;
import com.charicha.impl.GLScreen;
import com.charicha.math.Collision;
import com.charicha.math.Rectangle;
import com.charicha.math.Vector2;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/24/2018.
 */

public class MainMenuScreen extends GLScreen {

    GL10 gl;

    Camera2D camera2D;
    Vector2 touchPos;
    List<Input.TouchEvent> touchEvents;

    Rectangle playBound;
    Rectangle optionsBound;

    SpriteBatcher spriteBatcher;

    public MainMenuScreen(Game game) {
        super(game);
        glGraphics.getGL();
        gl = glGraphics.getGL();
        camera2D = new Camera2D(glGraphics, 480,320);
        touchPos = new Vector2();

        playBound = new Rectangle(240 - 112, 100, 224, 32);
        optionsBound = new Rectangle(240 - 112, 100 - 32, 224, 32);

        spriteBatcher = new SpriteBatcher(glGraphics, 100);
    }

    @Override
    public void update(float deltaTime) {
        touchEvents = glGame.getInput().getTouchEvents();
        int len = touchEvents.size();

        for(int i = 0; i < len; i++){
            Input.TouchEvent touchEvent = touchEvents.get(i);

            if(touchEvent.type != Input.TouchEvent.TOUCH_UP)
                continue;

            camera2D.screenToWorldPoint(touchPos.set(touchEvent.x, touchEvent.y));

            if(Collision.pointInRect(touchPos, playBound)){
                glGame.changeCurrentScreen(new PlayScreen(glGame));
                Assets.playSound(Assets.clickSound);
            }

            if(Collision.pointInRect(touchPos, optionsBound)){
                glGame.changeCurrentScreen(new SettingsScreen(glGame));
                Assets.playSound(Assets.clickSound);
            }

        }
    }

    @Override
    public void render(float deltaTime) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera2D.setOrthoProjection();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glEnable(GL10.GL_TEXTURE_2D);

        spriteBatcher.beginBatch(Assets.background);
        spriteBatcher.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
        spriteBatcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        spriteBatcher.beginBatch(Assets.items);
        spriteBatcher.drawSprite(240, 240, 384, 128, Assets.logoRegion);
        spriteBatcher.drawSprite(240, 100, 224, 64, Assets.menuRegion);
        spriteBatcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    @Override
    public void resume() {
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }
}

