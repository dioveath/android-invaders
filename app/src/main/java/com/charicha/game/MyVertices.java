package com.charicha.game;

import com.charicha.impl.GLGraphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/9/2018.
 */

public class MyVertices  {

    GLGraphics glGraphics;
    GL10 gl;
    boolean hasColor = false;
    boolean hasTexture = false;

    FloatBuffer mFloatBuffer;
    ShortBuffer mShortBuffer;
    int maxVertices = 0;
    int maxIndices = 0;
    int VERTEX_SIZE = 0;

    public MyVertices(GLGraphics glGraphics, int maxVertices, int maxIndices, boolean hasTexture, boolean hasColor){
        this.glGraphics = glGraphics;
        this.gl = glGraphics.getGL();
        this.maxVertices = maxVertices;

        this.VERTEX_SIZE = (2 + (hasTexture ? 2 : 0) + (hasColor ? 4: 0)) * 4; // 4 bytes of float
        this.maxIndices = maxIndices;
        this.hasColor = hasColor;
        this.hasTexture = hasTexture;

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(VERTEX_SIZE * maxVertices);
        byteBuffer.order(ByteOrder.nativeOrder());
        this.mFloatBuffer = byteBuffer.asFloatBuffer();
        this.mFloatBuffer.clear();

        if(maxIndices != 0){
            byteBuffer = ByteBuffer.allocateDirect(maxIndices * 2); //Short.SIZE / 8
            byteBuffer.order(ByteOrder.nativeOrder());
            this.mShortBuffer = byteBuffer.asShortBuffer();
            this.mShortBuffer.clear();
        } else {
            this.mShortBuffer = null;
        }
    }

    public void addVertices(float[] vertices, int offset, int length){
        this.mFloatBuffer.clear();
        this.mFloatBuffer.put(vertices, offset, length);
        this.mFloatBuffer.flip();
    }

    public void addIndices(short[] indices, int offset, int length){
        if(mShortBuffer == null)
            throw new RuntimeException("Error: maxIndices is 0, Can't add indices");
        this.mShortBuffer.clear();
        this.mShortBuffer.put(indices, offset, length);
        this.mShortBuffer.flip();
    }

    public void bind(){
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        mFloatBuffer.position(0);
        gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, mFloatBuffer);

        if(hasTexture){
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            mFloatBuffer.position(2);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, mFloatBuffer);
        }
        if(hasColor){
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            mFloatBuffer.position(hasTexture ? 4 : 2);
            gl.glColorPointer(4, GL10.GL_FLOAT, VERTEX_SIZE, mFloatBuffer);
        }
    }

    public void draw(int drawElement, int offset, int numVertices){
        if(mShortBuffer != null){
            mShortBuffer.position(offset);
            gl.glDrawElements(drawElement, numVertices, GL10.GL_UNSIGNED_SHORT, mShortBuffer);
        } else {
            gl.glDrawArrays(drawElement, offset, numVertices);
        }
    }

    public void unbind(){
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        if(hasColor)
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        if(hasTexture)
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

}
