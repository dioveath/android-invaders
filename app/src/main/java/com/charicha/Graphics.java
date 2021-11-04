package com.charicha;

/**
 * Created by Charicha on 12/28/2017.
 */

public interface Graphics {

    public Pixmap newPixmap(String fileName, Pixmap.PixmapFormat format);

    public void clear(int color);

    public void drawCircle(int x, int y, int radius, int color);

    public void drawRect(int x, int y, int width, int height, int color);

    public void drawPixmap(Pixmap pixmap, int x, int y);

    public int getWidth();

    public int getHeight();
}
