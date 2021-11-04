package com.charicha.androidinvaders;

import com.charicha.Music;
import com.charicha.Sound;
import com.charicha.game.Animation;
import com.charicha.game.ObjLoader;
import com.charicha.game.Texture;
import com.charicha.game.TextureRegion;
import com.charicha.game.Vertices3;
import com.charicha.impl.GLGame;

/**
 * Created by Charicha on 1/23/2018.
 */

public class Assets {

    static Texture background;
    static TextureRegion backgroundRegion;
    static Texture items;
    static TextureRegion logoRegion;
    static TextureRegion menuRegion;
    static TextureRegion gameoverRegion;
    static TextureRegion pauseRegion;
    static TextureRegion settingsRegion;
    static TextureRegion touchRegion;
    static TextureRegion accelRegion;
    static TextureRegion touchEnabledRegion;
    static TextureRegion accelEnabledRegion;
    static TextureRegion soundRegion;
    static TextureRegion soundEnabledRegion;
    static TextureRegion leftRegion;
    static TextureRegion rightRegion;
    static TextureRegion fireRegion;
    static TextureRegion pauseButtonRegion;
    static TextWriter textWriter;

    static Texture explosionTexture;
    static Animation explosionAnim;
    static Vertices3 shipModel;
    static Texture shipTexture;
    static Vertices3 invaderModel;
    static Texture invaderTexture;
    static Vertices3 shotModel;
    static Vertices3 shieldModel;

    static Music music;
    static Sound clickSound;
    static Sound explosionSound;
    static Sound shotSound;


    public static void load(GLGame game){
        background = new Texture(game, "background.jpg", true);
        backgroundRegion = new TextureRegion(background, 0, 0, background.width, background.height);

        items = new Texture(game, "items.png", true);
        logoRegion = new TextureRegion(items, 0, 256, 384, 128);
        menuRegion = new TextureRegion(items, 0, 128, 224, 64);
        gameoverRegion = new TextureRegion(items, 224, 128, 128, 64);
        pauseRegion = new TextureRegion(items, 0, 192, 160, 64);
        settingsRegion = new TextureRegion(items, 0, 160, 224, 32);
        touchRegion = new TextureRegion(items, 0, 384, 64, 64);
        accelRegion = new TextureRegion(items, 64, 384, 64, 64);
        touchEnabledRegion = new TextureRegion(items, 0, 448, 64, 64);
        accelEnabledRegion = new TextureRegion(items, 64, 448, 64, 64);
        soundRegion = new TextureRegion(items, 128, 384, 64, 64);
        soundEnabledRegion = new TextureRegion(items, 190, 384, 64, 64);
        leftRegion = new TextureRegion(items, 0, 0, 64, 64);
        rightRegion = new TextureRegion(items, 64, 0, 64, 64);
        fireRegion = new TextureRegion(items, 128, 0, 64, 64);
        pauseButtonRegion = new TextureRegion(items, 0, 64, 64, 64);
        textWriter = new TextWriter(items, 224, 0, 16, 20, 16, 6);

        explosionTexture = new Texture(game, "explode.png", true);
        TextureRegion[] keyFrames = new TextureRegion[16];
        int frame = 0;
        for(int i = 0; i < 256; i += 64){
            for(int j = 0; j < 256; j+= 64){
                keyFrames[frame++] = new TextureRegion(explosionTexture, j, i, 64, 64);
            }
        }
        explosionAnim = new Animation(0.1f, keyFrames);

        shipTexture = new Texture(game, "ship.png", true);
        shipModel = ObjLoader.loadObj(game, "ship.obj");

        invaderTexture = new Texture(game, "invader.png", true);
        invaderModel = ObjLoader.loadObj(game, "invader.obj");

        shieldModel = ObjLoader.loadObj(game, "shield.obj");
        shotModel = ObjLoader.loadObj(game, "shot.obj");

        music = game.getAudio().newMusic("music.mp3", true);
        music.setVolume(0.5f);
        if(Settings.soundEnabled)
            music.play();

        clickSound = game.getAudio().newSound("click.ogg");
        explosionSound = game.getAudio().newSound("explosion.ogg");
        shotSound = game.getAudio().newSound("shot.ogg");
    }

    public static void reload(){
        background.reload();
        items.reload();
        explosionTexture.reload();
        shipTexture.reload();
        invaderTexture.reload();
        if(Settings.soundEnabled)
            music.play();
    }

    public static void playSound(Sound sound){
        if(Settings.soundEnabled)
            sound.play(1);
    }
}
