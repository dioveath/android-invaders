package com.charicha.game;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/21/2018.
 */

public class Vertices3 {

    GL10 gl;

    IntBuffer intBuffer;
    ShortBuffer shortBuffer;
    boolean hasTexture;
    boolean hasColor;
    boolean hasNormal;

    int[] tmpBuffer;
    int vertexSize;

    public Vertices3(GL10 gl, int maxVertices, int maxIndices, boolean hasTexture, boolean hasColor, boolean hasNormal){
        this.gl = gl;
        this.hasTexture = hasTexture;
        this.hasColor = hasColor;
        this.hasNormal = hasNormal;

        vertexSize = (3 + (hasTexture ? 2 : 0) + (hasColor ? 4 : 0) + (hasNormal ? 3 : 0)) * 4;
        tmpBuffer = new int[maxVertices * (vertexSize / 4)];

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(maxVertices * vertexSize);
        byteBuffer.order(ByteOrder.nativeOrder());
        intBuffer = byteBuffer.asIntBuffer();

        if(maxIndices > 0){
            byteBuffer = ByteBuffer.allocateDirect(maxIndices * 2);
            byteBuffer.order(ByteOrder.nativeOrder());
            shortBuffer = byteBuffer.asShortBuffer();
        } else {
            shortBuffer = null;
        }

    }

    public void setVertices(float[] verticesInfo, int offset, int length){
        intBuffer.clear();
        for(int i = offset, j = 0; i < offset + length; i++, j++){
            tmpBuffer[j] = Float.floatToRawIntBits(verticesInfo[i]);
        }
        intBuffer.put(tmpBuffer, 0, length);
        intBuffer.flip();
    }

    public void setIndices(short[] indices, int offset, int length){
        shortBuffer.clear();
        shortBuffer.put(indices, offset, length);
        shortBuffer.flip();
    }

    public void bindVertices(){
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        intBuffer.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, vertexSize, intBuffer);

        if(hasTexture){
            intBuffer.position(3);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, intBuffer);
        }
        if(hasColor){
            intBuffer.position(3 + (hasTexture ? 2 : 0));
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, intBuffer);
        }
        if(hasNormal){
            intBuffer.position(3 + (hasTexture ? 2 : 0) + (hasColor ? 4 : 0));
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glNormalPointer(GL10.GL_FLOAT, vertexSize, intBuffer);
        }
    }

    public void draw(int primitiveType, int offset, int numVertices){
        if(shortBuffer != null){
            shortBuffer.position(offset);
            gl.glDrawElements(primitiveType, numVertices, GL10.GL_UNSIGNED_SHORT, shortBuffer);
        } else {
            gl.glDrawArrays(GL10.GL_TRIANGLES, offset, numVertices);
        }
    }

    public void unbindVertices(){
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        if(hasTexture)
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        if(hasColor)
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        if(hasNormal)
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }

    public int numIndices(){
        if(shortBuffer == null)
            return 0;
        return shortBuffer.limit();
    }

    public int numVertices(){
        return intBuffer.limit() / (vertexSize/4);
    }

}
