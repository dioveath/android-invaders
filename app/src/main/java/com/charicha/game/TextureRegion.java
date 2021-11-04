package com.charicha.game;

/**
 * Created by Charicha on 1/11/2018.
 */

public class TextureRegion {

    public final Texture texture;
    public final float u1, u2, v1, v2;

    public TextureRegion(Texture texture, float x, float y, float width, float height){
        this.texture = texture;
        this.u1 = x / texture.width;
        this.u2 = (x + width) / texture.width;
        this.v1 = y / texture.height;
        this.v2 = (y + height) / texture.height;
    }

}
