package com.charicha;

/**
 * Created by Charicha on 12/29/2017.
 */

public interface Pixmap {

    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public PixmapFormat getFormat();

    public int getWidth();

    public int getHeight();
}
