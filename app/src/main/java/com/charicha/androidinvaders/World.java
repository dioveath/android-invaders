package com.charicha.androidinvaders;

import com.charicha.math.Collision;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Charicha on 1/24/2018.
 */

public class World {
    public interface WorldListener {
        void explosion();
        void shot();
    }

    final static float WORLD_MIN_X = -14;
    final static float WORLD_MAX_X = 14;
    final static float WORLD_MIN_Z = -15;

    WorldListener worldListener;
    int waves = 1;
    int score = 0;
    float speedMultiplier = 1;

    final List<Shot> shots = new ArrayList<>();
    final List<Invader> invaders = new ArrayList<>();
    final List<Shield> shields = new ArrayList<>();
    final Ship ship;
    long lastShotTime;
    Random random;

    public World(){
        ship = new Ship(0,0, 0);
        ship.currentState = Ship.State.Exploding;
        generateInvaders();
        generateShields();
        lastShotTime = System.nanoTime();
        random = new Random();
    }

    private void generateInvaders(){
        for(int row = 0; row < 4; row++){
            for(int column = 0; column < 8; column++){
                Invader invader = new Invader(-WORLD_MAX_X/2 + column * 2f, 0, WORLD_MIN_Z + row * 2f);
                invaders.add(invader);
            }
        }
    }

    private void generateShields(){
        for(int i = 0; i < 3; i++){
            shields.add(new Shield(-10 + i * 10 - 1, 0, -3));
            shields.add(new Shield(-10 + i * 10 + 0, 0, -3));
            shields.add(new Shield(-10 + i * 10 + 1, 0, -3));
            shields.add(new Shield(-10 + i * 10 - 1, 0, -2));
            shields.add(new Shield(-10 + i * 10 + 1, 0, -2));
        }
    }

    public void setWorldListener(WorldListener worldListener){
        this.worldListener = worldListener;
    }

    public void update(float deltaTime, float accelx){
        ship.update(deltaTime, accelx);
        updateInvaders(deltaTime);
        updateShots(deltaTime);

        checkShotCollisions();
        checkInvaderCollisions();

        if(invaders.size() == 0){
            generateInvaders();
            waves++;
            speedMultiplier += 0.5f;
        }
    }

    private void updateInvaders(float deltaTime){
        int len = invaders.size();
        for(int i = 0; i < len; i++){
            Invader invader = invaders.get(i);
            invader.update(deltaTime, speedMultiplier);

            if(invader.currentState == Invader.State.Alive){
                if(random.nextFloat() < 0.001f){
                    Shot shot = new Shot(invader.position.x, invader.position.y, invader.position.z, Shot.SHOT_VELOCITY);
                    shots.add(shot);
                    worldListener.shot();
                }
            } else if(invader.currentState == Invader.State.Dead && invader.internalStateTime >= Invader.INVADER_EXPLOSION_TIME){
                invaders.remove(i);
                i--;
                len--;
            }

        }
    }

    private void updateShots(float deltaTime){
        int len = shots.size();
        for(int i = 0; i < len; i++){
            Shot shot = shots.get(i);
            shot.update(deltaTime);
            if(shot.position.z < WORLD_MIN_Z || shot.position.z > 5){
                shots.remove(i);
                i--;
                len--;
            }
        }
    }

    private void checkShotCollisions(){
        int len = shots.size();

        for(int i = 0; i < len; i++){
            Shot shot = shots.get(i);
            boolean shotRemoved = false;

            int len2 = shields.size();
            for(int j = 0; j < len2; j++){
                Shield shield = shields.get(j);
                if(Collision.overlapSpheres(shield.boundSphere, shot.boundSphere)){
                    shields.remove(j);
                    shots.remove(i);
                    i--;
                    len--;
                    shotRemoved = true;
                    break;
                }
            }

            if(shotRemoved)
                continue;

            if(shot.velocity.z < 0){
                len2 = invaders.size();
                for(int j = 0; j < len2; j++){
                    Invader invader = invaders.get(j);
                    if(Collision.overlapSpheres(invader.boundSphere, shot.boundSphere) && invader.currentState != Invader.State.Dead){
                        invader.kill();
                        worldListener.explosion();
                        score += 10;
                        shots.remove(i);
                        i--;
                        len--;
                        break;
                    }
                }
            } else {
                if(Collision.overlapSpheres(ship.boundSphere, shot.boundSphere) && ship.currentState == Ship.State.Alive){
                    ship.kill();
                    worldListener.explosion();
                    shots.remove(i);
                    i--;
                    len--;
                }
            }
        }



    }

    private void checkInvaderCollisions(){
        if(ship.currentState == Ship.State.Exploding)
            return;

        int len = invaders.size();
        for(int i = 0; i < len; i++){
            if(Collision.overlapSpheres(ship.boundSphere, invaders.get(i).boundSphere)){
                ship.lives = 1;
                ship.kill();
                return;
            }
        }

    }

    public boolean isGameOver(){
        return ship.lives == 0;
    }

    public void shoot(){
        if(ship.currentState == Ship.State.Exploding)
            return;

        int friendlyShoots = 0;
        int len = shots.size();
        for(int i = 0; i < len; i++){
            if(shots.get(i).velocity.z < 0){
                friendlyShoots++;
            }
        }

        if(System.nanoTime() - lastShotTime > 1000000000 || friendlyShoots == 0){
            shots.add(new Shot(ship.position.x, 0, ship.position.z, -Shot.SHOT_VELOCITY));
            lastShotTime = System.nanoTime();
            worldListener.shot();
        }

    }

}
