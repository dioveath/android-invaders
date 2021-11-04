package com.charicha.androidinvaders;

import com.charicha.game.AmbientLight;
import com.charicha.game.DirectionalLight;
import com.charicha.game.LookAtCamera;
import com.charicha.game.SpriteBatcher;
import com.charicha.game.TextureRegion;
import com.charicha.impl.GLGraphics;
import com.charicha.math.Vector3;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/24/2018.
 */

public class WorldRenderer {

    GLGraphics glGraphics;
    GL10 gl;
    LookAtCamera lookAtCamera;
    AmbientLight ambientLight;
    DirectionalLight directionalLight;
    SpriteBatcher spriteBatcher;
    float invaderAngle = 0;

    public WorldRenderer(GLGraphics glGraphics){
        this.glGraphics = glGraphics;
        this.gl = glGraphics.getGL();
        lookAtCamera = new LookAtCamera(67, glGraphics.getWidth()/(float)glGraphics.getHeight(), 0.1f, 100f);
        lookAtCamera.position.set(0, 6, 2);
        lookAtCamera.lookAt.set(0, 0, -4);
        ambientLight = new AmbientLight();
        directionalLight = new DirectionalLight();
        directionalLight.setDirection(1, 0.5f, 0);
        spriteBatcher = new SpriteBatcher(glGraphics, 100);
    }

    public void render(World world, float deltaTime){
        lookAtCamera.position.x = world.ship.position.x;
        lookAtCamera.lookAt.x = world.ship.position.x;
        lookAtCamera.setMatrices(gl);

        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_COLOR_MATERIAL);
        ambientLight.enable(gl);
        directionalLight.enable(gl, GL10.GL_LIGHT0);

        renderShip(gl, world.ship);
        renderInvaders(gl, world.invaders, deltaTime);

        gl.glDisable(GL10.GL_TEXTURE_2D);

        renderShields(gl, world.shields);
        renderShots(gl, world.shots);

        gl.glDisable(GL10.GL_COLOR_MATERIAL);
        gl.glDisable(GL10.GL_LIGHTING);
        gl.glDisable(GL10.GL_DEPTH_TEST);
    }

    private void renderShip(GL10 gl, Ship ship){
        if(ship.currentState == Ship.State.Exploding){
            gl.glDisable(GL10.GL_LIGHTING);
            renderExplosion(gl, ship.position, ship.internalStateTime);
            gl.glEnable(GL10.GL_LIGHTING);
        } else {
            Assets.shipTexture.bind();
            Assets.shipModel.bindVertices();
            gl.glPushMatrix();
            gl.glTranslatef(ship.position.x, ship.position.y, ship.position.z);
            gl.glRotatef(ship.velocity.x / Ship.SHIP_VELOCITY * 90, 0, 0, -1);
            Assets.shipModel.draw(GL10.GL_TRIANGLES, 0, Assets.shipModel.numVertices());
            gl.glPopMatrix();
            Assets.shipModel.unbindVertices();
        }
    }

    private void renderInvaders(GL10 gl, List<Invader> invaders, float deltaTime){
        invaderAngle += 45 * deltaTime; //It would've been great if angle was inside invader itself;
        // Second thought:: little hack for performance probably as we need only 1 variable to track;

        Assets.invaderTexture.bind();
        Assets.invaderModel.bindVertices();
        int len = invaders.size();

        for(int i = 0; i < len; i++){
            Invader invader = invaders.get(i);
            if(invader.currentState == Invader.State.Dead){
                gl.glDisable(GL10.GL_LIGHTING);
                Assets.invaderModel.unbindVertices();
                renderExplosion(gl, invader.position, invader.internalStateTime);
                Assets.invaderTexture.bind();
                Assets.invaderModel.bindVertices();
                gl.glEnable(GL10.GL_LIGHTING);
            } else {
                gl.glPushMatrix();
                gl.glTranslatef(invader.position.x, invader.position.y, invader.position.z);
                gl.glRotatef(invaderAngle, 0, 1, 0);
                Assets.invaderModel.draw(GL10.GL_TRIANGLES, 0, Assets.invaderModel.numVertices());
                gl.glPopMatrix();
            }
        }
        Assets.invaderModel.unbindVertices();
    }


    private void renderShields(GL10 gl, List<Shield> shields){
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glColor4f(0, 0, 1, 0.4f);
        Assets.shieldModel.bindVertices();

        int len = shields.size();

        for(int i = 0; i < len; i++){
            Shield shield = shields.get(i);
            gl.glPushMatrix();
            gl.glTranslatef(shield.position.x, shield.position.y, shield.position.z);
            Assets.shieldModel.draw(GL10.GL_TRIANGLES, 0, Assets.shieldModel.numVertices());
            gl.glPopMatrix();
        }

        Assets.shieldModel.unbindVertices();
        gl.glColor4f(1, 1, 1, 1f);
        gl.glDisable(GL10.GL_BLEND);
    }

    private void renderShots(GL10 gl, List<Shot> shots){
        gl.glColor4f(1, 1, 0, 1);
        Assets.shotModel.bindVertices();
        int len = shots.size();

        for(int i = 0; i < len; i++){
            Shot shot = shots.get(i);
            gl.glPushMatrix();
            gl.glTranslatef(shot.position.x, shot.position.y, shot.position.z);
            Assets.shotModel.draw(GL10.GL_TRIANGLES, 0, Assets.shotModel.numVertices());
            gl.glPopMatrix();
        }
        Assets.shotModel.unbindVertices();
        gl.glColor4f(1, 1, 1, 1);
    }

    private void renderExplosion(GL10 gl, Vector3 position, float stateime){
        TextureRegion frame = Assets.explosionAnim.getKeyFrame(stateime, false);
        gl.glEnable(GL10.GL_BLEND);

        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        spriteBatcher.beginBatch(Assets.explosionTexture);
        spriteBatcher.drawSprite(0, 0, 2, 2, frame);
        spriteBatcher.endBatch();
        gl.glPopMatrix();

        gl.glDisable(GL10.GL_BLEND);
    }

}
