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

public class SettingsScreen extends GLScreen {

    GL10 gl;

    Camera2D camera2D;
    Vector2 touchPos;
    List<Input.TouchEvent> touchEvents;

    Rectangle touchBound;
    Rectangle accelBound;
    Rectangle soundBound;
    Rectangle backBound;

    SpriteBatcher spriteBatcher;


    public SettingsScreen(Game game) {
        super(game);
        gl = glGraphics.getGL();

        camera2D = new Camera2D(glGraphics, 480, 320);
        touchPos = new Vector2();

        touchBound = new Rectangle(120 - 32, 160 - 32, 64, 64);
        accelBound = new Rectangle(240 - 32, 160 - 32, 64, 64);
        soundBound = new Rectangle(360 - 32, 160 - 32, 64, 64);
        backBound = new Rectangle(32, 32, 64, 64);

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

            if(Collision.pointInRect(touchPos, touchBound)){
                Settings.touchEnabled = true;
                Assets.playSound(Assets.clickSound);
                Settings.saveSettings(glGame.getFileIO());
            }

            if(Collision.pointInRect(touchPos, accelBound)){
                Settings.touchEnabled = false;
                Assets.playSound(Assets.clickSound);
                Settings.saveSettings(glGame.getFileIO());
            }

            if(Collision.pointInRect(touchPos, soundBound)){
                Settings.soundEnabled = !Settings.soundEnabled;
                Assets.playSound(Assets.clickSound);
                Settings.saveSettings(glGame.getFileIO());
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
        spriteBatcher.drawSprite(touchBound.lowerLeft.x + touchBound.width/2,
                touchBound.lowerLeft.y + touchBound.height/2,
                touchBound.width, touchBound.height, Settings.touchEnabled ? Assets.touchEnabledRegion : Assets.touchRegion);

        spriteBatcher.drawSprite(accelBound.lowerLeft.x + accelBound.width/2,
                accelBound.lowerLeft.y + accelBound.height/2,
                accelBound.width, accelBound.height, Settings.touchEnabled ? Assets.accelEnabledRegion : Assets.accelRegion);

        spriteBatcher.drawSprite(soundBound.lowerLeft.x + soundBound.width/2,
                soundBound.lowerLeft.y + soundBound.height/2,
                soundBound.width, soundBound.height, Assets.soundRegion);

        spriteBatcher.drawSprite(32, 32, 64, 64, Assets.leftRegion);
        spriteBatcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    @Override
    public void resume() {
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glColor4f(0, 0, 0, 1);
    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }
}
