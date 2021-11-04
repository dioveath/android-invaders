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
 * Created by Charicha on 1/23/2018.
 */

public class PlayScreen extends GLScreen {

    enum GameState {Running, Paused, Gameover};

    GameState curGameState = GameState.Running;

    World world;
    World.WorldListener worldListener;
    WorldRenderer worldRenderer;
    int lastScore;
    int lastLives;
    int lastWaves;
    String scoreString;

    Rectangle pauseBound;
    Rectangle resumeBound;
    Rectangle quitBound;
    Rectangle leftBound;
    Rectangle rightBound;
    Rectangle shotBound;

    Camera2D camera2D;
    Vector2 touchPoint;
    List<Input.TouchEvent> touchEvents;

    SpriteBatcher spriteBatcher;
    GL10 gl;

    public PlayScreen(Game game) {
        super(game);
        world = new World();
        worldListener = (new World.WorldListener() {
            @Override
            public void explosion() {
                Assets.playSound(Assets.explosionSound);
            }

            @Override
            public void shot() {
                Assets.playSound(Assets.shotSound);
            }
        });
        world.setWorldListener(worldListener);
        worldRenderer = new WorldRenderer(glGraphics);
        lastScore = 0;
        lastLives = world.ship.lives;
        lastWaves = world.waves;
        scoreString = "lives: " + lastLives + " waves: " + lastWaves + " scores: " + lastScore + " FPS: " + FPSCounter.FPS;

        pauseBound = new Rectangle(480-64, 320-64, 64, 64);
        resumeBound = new Rectangle(240-80, 160, 160, 32);
        quitBound = new Rectangle(240-80, 160-32, 160, 32);
        shotBound = new Rectangle(480-64, 0, 64, 64);
        leftBound = new Rectangle(0, 0, 64, 64);
        rightBound = new Rectangle(64, 0, 64, 64);

        camera2D = new Camera2D(glGraphics, 480, 320);
        touchPoint = new Vector2();

        spriteBatcher = new SpriteBatcher(glGraphics, 200);
        gl = glGraphics.getGL();

    }

    @Override
    public void update(float deltaTime) {
        switch (curGameState){
            case Running:
                updateRunning(deltaTime);
                break;
            case Paused:
                updatePaused();
                break;
            case Gameover:
                updateGameover();
                break;
        }
    }

    private void updateRunning(float deltaTime){
        touchEvents = mGame.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            Input.TouchEvent touchEvent = touchEvents.get(i);

            if(touchEvent.type != Input.TouchEvent.TOUCH_UP)
                continue;

            camera2D.screenToWorldPoint(touchPoint.set(touchEvent.x, touchEvent.y));

            if(Collision.pointInRect(touchPoint, pauseBound)){
                Assets.playSound(Assets.clickSound);
                curGameState = GameState.Paused;
                continue;
            }
            if(Collision.pointInRect(touchPoint, shotBound)){
                world.shoot();
            }
        }

        world.update(deltaTime, calculateInputAcceleration());

        if(world.ship.lives != lastLives || world.waves != lastWaves || world.score != lastScore){
            lastScore = world.score;
            lastLives = world.ship.lives;
            lastWaves = world.waves;
            scoreString = "lives: " + lastLives + " waves: " + lastWaves + " scores: " + lastScore + " FPS: " + FPSCounter.FPS;
        }
        if(world.isGameOver()){
            curGameState = GameState.Gameover;
        }
    }

    private void updatePaused(){
        touchEvents = glGame.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            Input.TouchEvent touchEvent = touchEvents.get(i);
            if(touchEvent.type != Input.TouchEvent.TOUCH_UP)
                continue;

            camera2D.screenToWorldPoint(touchPoint.set(touchEvent.x, touchEvent.y));

            if(Collision.pointInRect(touchPoint, resumeBound)){
                Assets.playSound(Assets.clickSound);
                curGameState = GameState.Running;
            }
            if(Collision.pointInRect(touchPoint, quitBound)){
                Assets.playSound(Assets.clickSound);
                mGame.changeCurrentScreen(new MainMenuScreen(mGame));
            }

        }
    }

    private float calculateInputAcceleration(){
        float accelx = 0;
        if(Settings.touchEnabled){
        } else {
            accelx = glGame.getInput().getAccelY();
        }
        return accelx;
    }

    private void updateGameover(){
        touchEvents = glGame.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++){
            if(touchEvents.get(i).type == Input.TouchEvent.TOUCH_UP){
                Assets.playSound(Assets.clickSound);
                mGame.changeCurrentScreen(new MainMenuScreen(mGame));
            }
        }
    }

    @Override
    public void render(float deltaTime) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        camera2D.setOrthoProjection();

        gl.glEnable(GL10.GL_TEXTURE_2D);
        spriteBatcher.beginBatch(Assets.background);
        spriteBatcher.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
        spriteBatcher.endBatch();
        gl.glDisable(GL10.GL_TEXTURE_2D);

        worldRenderer.render(world, deltaTime);

        switch(curGameState){
            case Running:
                renderRunning();
                break;
            case Paused:
                renderPaused();
                break;
            case Gameover:
                renderGameover();
                break;
        }

        FPSCounter.logFrame();
    }

    private void renderRunning(){
        camera2D.setOrthoProjection();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        spriteBatcher.beginBatch(Assets.items);
        spriteBatcher.drawSprite(480-32, 320-32, 64, 64, Assets.pauseButtonRegion);
        Assets.textWriter.writeText(spriteBatcher, scoreString, 240, 320-20);
        if(Settings.touchEnabled){
            spriteBatcher.drawSprite(32, 32, 64, 64, Assets.leftRegion);
            spriteBatcher.drawSprite(96, 32, 64, 64, Assets.rightRegion);
        }
        spriteBatcher.drawSprite(480-32, 32, 64, 64, Assets.fireRegion);
        spriteBatcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    private void renderPaused(){
        camera2D.setOrthoProjection();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        spriteBatcher.beginBatch(Assets.items);
        Assets.textWriter.writeText(spriteBatcher, scoreString, 10, 320-20);
        spriteBatcher.drawSprite(240, 160, 160, 64, Assets.pauseRegion);
        spriteBatcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    private void renderGameover(){
        camera2D.setOrthoProjection();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        spriteBatcher.beginBatch(Assets.items);
        Assets.textWriter.writeText(spriteBatcher, scoreString, 10, 320-20);
        spriteBatcher.drawSprite(240, 160, 128, 64, Assets.gameoverRegion);
        spriteBatcher.endBatch();

        gl.glDisable(GL10.GL_TEXTURE_2D);
        gl.glDisable(GL10.GL_BLEND);
    }



    @Override
    public void resume() {
        gl.glClearColor(0, 0, 0, 1);
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
    }

    @Override
    public void pause() {
        curGameState = GameState.Paused;
    }

    @Override
    public void dispose() {

    }
}
