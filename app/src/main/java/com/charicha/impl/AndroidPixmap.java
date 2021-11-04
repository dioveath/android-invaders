package com.charicha.impl;

import android.graphics.Bitmap;

import com.charicha.Pixmap;

/**
 * Created by Charicha on 12/29/2017.
 */

public class AndroidPixmap implements Pixmap {

    Bitmap mBitmap;
    PixmapFormat mFormat;

    public AndroidPixmap(Bitmap bitmap, PixmapFormat format){
        this.mBitmap = bitmap;
        this.mFormat = format;
    }

    @Override
    public PixmapFormat getFormat() {
        return mFormat;
    }

    @Override
    public int getWidth() {
        return mBitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return mBitmap.getHeight();
    }
}
