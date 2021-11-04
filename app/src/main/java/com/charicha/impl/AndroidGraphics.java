package com.charicha.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.charicha.Game;
import com.charicha.Graphics;
import com.charicha.Pixmap;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Charicha on 12/28/2017.
 */

public class AndroidGraphics implements Graphics {

    Game mGame;
    Canvas mCanvas;
    Paint mPaint;

    public AndroidGraphics(Game game, Bitmap frameBuffer){
        mGame = game;
        mCanvas = new Canvas(frameBuffer);
        mPaint = new Paint();
    }

    @Override
    public Pixmap newPixmap(String fileName, Pixmap.PixmapFormat format){
        Bitmap.Config config;
        if(format == Pixmap.PixmapFormat.ARGB4444){
            config = Bitmap.Config.ARGB_4444;
        } else if(format == Pixmap.PixmapFormat.RGB565){
            config = Bitmap.Config.RGB_565;
        } else {
            config = Bitmap.Config.ARGB_8888;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;

        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = mGame.getFileIO().openAsset(fileName);
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        } catch(IOException ioe){
            ioe.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
        if(bitmap.getConfig() == Bitmap.Config.ARGB_4444){
            format = Pixmap.PixmapFormat.ARGB4444;
        } else if(bitmap.getConfig() == Bitmap.Config.ARGB_8888){
            format = Pixmap.PixmapFormat.ARGB8888;
        } else {
            format = Pixmap.PixmapFormat.ARGB8888;
        }
        return new AndroidPixmap(bitmap, format);
    }

    @Override
    public void clear(int color){
        mCanvas.drawRGB((color & 0xff0000) >> 16, (color & 0x00ff00) >> 8, (color & 0x0000ff));
    }

    @Override
    public void drawCircle(int x, int y, int radius, int color){
        mPaint.setColor(Color.rgb((color & 0xff0000) >> 16, (color & 0x00ff00) >> 8, (color & 0x0000ff)));
        mCanvas.drawCircle(x, y, radius, mPaint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color){
        mPaint.setColor(Color.rgb((color & 0xff0000) >> 16, (color & 0x00ff00) >> 8, (color & 0x0000ff)));
        mCanvas.drawRect(x, y, x + width - 1, y + height - 1, mPaint);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y){
        mCanvas.drawBitmap(((AndroidPixmap)pixmap).mBitmap, x, y, null);
    }

    @Override
    public int getWidth(){
        return mCanvas.getWidth();
    }

    @Override
    public int getHeight(){
        return mCanvas.getHeight();
    }

}
