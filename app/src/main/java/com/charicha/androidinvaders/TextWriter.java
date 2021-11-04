package com.charicha.androidinvaders;

import com.charicha.game.SpriteBatcher;
import com.charicha.game.Texture;
import com.charicha.game.TextureRegion;

/**
 * Created by Charicha on 1/24/2018.
 */

public class TextWriter {

    public float glyphWidth, glyphHeight;
    public Texture texture;
    public TextureRegion[] glyphs = new TextureRegion[96];

    public TextWriter(Texture texture, float x, float y, float glyphWidth, float glyphHeight, int numGlyphPerRow, int numGlyphPerColumn){
        this.glyphWidth = glyphWidth;
        this.glyphHeight = glyphHeight;
        this.texture = texture;
        for(int i = 0; i < numGlyphPerRow; i++){
            for(int j = 0; j < numGlyphPerColumn; j++){
                int index = j * numGlyphPerRow + i;
                if(index < 96){
                    glyphs[index] = new TextureRegion(texture, x + i * glyphWidth, y + j * glyphHeight, glyphWidth, glyphHeight);
                } else
                    break;
            }
        }
    }

    public void writeText(SpriteBatcher spriteBatcher, String text, float x, float y){
        int len = text.length();
        int stringWidth = len * (int) glyphWidth/2;
        x = x - stringWidth/2;

        for(int i = 0; i < len; i++){
            char c = text.charAt(i);
            int index = c - ' ';
            if(index >= 0 && index < 96){
                spriteBatcher.drawSprite(x, y, glyphWidth/2, glyphHeight/2, glyphs[index]);
                x+= glyphWidth/2;
            }
        }
    }

}
