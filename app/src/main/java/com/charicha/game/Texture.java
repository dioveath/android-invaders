package com.charicha.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.GLUtils;

import com.charicha.impl.GLGame;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Charicha on 1/2/2018.
 */

public class Texture  {

    GLGame mGame;
    String mFilename;
    GL10 gl;

    int textureId;
    int minFilter;
    int magFilter;

    public int width;
    public int height;

    boolean isMipmapped = false;

    public Texture(GLGame game, String mFilename){
        this(game, mFilename, false);
    }

    public Texture(GLGame game, String fileName, boolean isMipmapped){
        mGame = game;
        mFilename = fileName;
        gl = game.getGLGraphics().getGL();
        load();
    }

    public void load(){
        InputStream inputStream = null;
        try {
            inputStream = mGame.getFileIO().openAsset(mFilename);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            width = bitmap.getWidth();
            height = bitmap.getHeight();

            int[] textureIds = new int[1];
            gl.glGenTextures(1, textureIds, 0);
            textureId = textureIds[0];
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

            if(isMipmapped){
                loadMipmap(bitmap);
            } else {
                setFilters(GL10.GL_LINEAR, GL10.GL_LINEAR);
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
                gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);

                bitmap.recycle();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        } finally {
            try {
                    inputStream.close();
            } catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

    private void loadMipmap(Bitmap bitmap){
        int newWidth;
        int newHeight;
        setFilters(GL10.GL_LINEAR_MIPMAP_NEAREST, GL10.GL_LINEAR);
        int level = 0;

        while(true){
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);
            newWidth = bitmap.getWidth()/2;
            newHeight = bitmap.getHeight()/2;
            if(newWidth <= 0)
                break;

            Bitmap newBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(bitmap,
                    new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                    new Rect(0, 0, newWidth, newHeight), null);
            bitmap.recycle();
            bitmap = newBitmap;
            level++;
        }

        bitmap.recycle();
    }

    public void bind(){
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
    }

    public void reload(){
        load();
//        bind();
//        setFilters(minFilter, magFilter);
//        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
    }

    public void unbind(){
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
    }

    public void dispose(){
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        gl.glDeleteTextures(1, new int[textureId], 0);
    }

    public void setFilters(int minFilter, int magFilter){
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilter);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);
    }

}
