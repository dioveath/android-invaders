package com.charicha.game;

import com.charicha.impl.GLGraphics;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/11/2018.
 */

public class SpriteBatcher {

    GLGraphics glGraphics;
    GL10 gl;

    MyVertices mVertices;
    int numSprites;

    float[] floatBuffer;
    int floatBufferIndex;

    public SpriteBatcher(GLGraphics glGraphics, int maxSprites){
        this.glGraphics = glGraphics;
        this.gl = glGraphics.getGL();

        this.floatBuffer = new float[4 * 4 * maxSprites];
        this.mVertices = new MyVertices(glGraphics, 4 * maxSprites, 6 * maxSprites, true, false);
        this.floatBufferIndex = 0;
        this.numSprites = 0;

        short[] indices = new short[6 * maxSprites];
        short j = 0;
        for(int i = 0; i < indices.length; i += 6, j += 4){
            indices[i + 0] = (short) (j + 0);
            indices[i + 1] = (short) (j + 1);
            indices[i + 2] = (short) (j + 2);
            indices[i + 3] = (short) (j + 2);
            indices[i + 4] = (short) (j + 3);
            indices[i + 5] = (short) (j + 0);
        }
        mVertices.addIndices(indices, 0, indices.length);
    }

    public void beginBatch(Texture texture){
        texture.bind();
        floatBufferIndex = 0;
        this.numSprites = 0;
    }

    public void endBatch(){
        mVertices.addVertices(floatBuffer, 0, floatBufferIndex);
        mVertices.bind();
        mVertices.draw(GL10.GL_TRIANGLES, 0, numSprites * 6);
        mVertices.unbind();
    }

    public void drawSprite(float x, float y, float width, float height, TextureRegion textureRegion){
        float x1 = x - width/2;
        float x2 = x + width/2;
        float y1 = y - height/2;
        float y2 = y + height/2;

        floatBuffer[floatBufferIndex++] = x1;
        floatBuffer[floatBufferIndex++] = y1;
        floatBuffer[floatBufferIndex++] = textureRegion.u1;
        floatBuffer[floatBufferIndex++] = textureRegion.v2;

        floatBuffer[floatBufferIndex++] = x2;
        floatBuffer[floatBufferIndex++] = y1;
        floatBuffer[floatBufferIndex++] = textureRegion.u2;
        floatBuffer[floatBufferIndex++] = textureRegion.v2;

        floatBuffer[floatBufferIndex++] = x2;
        floatBuffer[floatBufferIndex++] = y2;
        floatBuffer[floatBufferIndex++] = textureRegion.u2;
        floatBuffer[floatBufferIndex++] = textureRegion.v1;

        floatBuffer[floatBufferIndex++] = x1;
        floatBuffer[floatBufferIndex++] = y2;
        floatBuffer[floatBufferIndex++] = textureRegion.u1;
        floatBuffer[floatBufferIndex++] = textureRegion.v1;

        numSprites++;
    }

    public void drawSprite(float x, float y, float width, float height, float angle, TextureRegion textureRegion){
        float halfWidth = width/2;
        float halfHeight = height/2;

        float cos = (float) Math.cos(angle / 180 * Math.PI);
        float sin = (float) Math.sin(angle / 180 * Math.PI);

        float x1 = -halfWidth * cos - (-halfHeight * sin);
        float y1 = -halfWidth * sin + (-halfHeight * cos);
        float x2 = halfWidth * cos - (-halfHeight * sin);
        float y2 = halfWidth * sin + (-halfHeight * cos);
        float x3 = halfWidth * cos - (halfHeight * sin);
        float y3 = halfWidth * sin + (halfHeight * cos);
        float x4 = -halfWidth * cos - (halfHeight * sin);
        float y4 = -halfWidth * sin + (halfHeight * cos);

        x1 += x;
        y1 += y;
        x2 += x;
        y2 += y;
        x3 += x;
        y3 += y;
        x4 += x;
        y4 += y;

        floatBuffer[floatBufferIndex++] = x1;
        floatBuffer[floatBufferIndex++] = y1;
        floatBuffer[floatBufferIndex++] = textureRegion.u1;
        floatBuffer[floatBufferIndex++] = textureRegion.v2;

        floatBuffer[floatBufferIndex++] = x2;
        floatBuffer[floatBufferIndex++] = y2;
        floatBuffer[floatBufferIndex++] = textureRegion.u2;
        floatBuffer[floatBufferIndex++] = textureRegion.v2;

        floatBuffer[floatBufferIndex++] = x3;
        floatBuffer[floatBufferIndex++] = y3;
        floatBuffer[floatBufferIndex++] = textureRegion.u2;
        floatBuffer[floatBufferIndex++] = textureRegion.v1;

        floatBuffer[floatBufferIndex++] = x4;
        floatBuffer[floatBufferIndex++] = y4;
        floatBuffer[floatBufferIndex++] = textureRegion.u1;
        floatBuffer[floatBufferIndex++] = textureRegion.v1;

        numSprites++;
    }

}
