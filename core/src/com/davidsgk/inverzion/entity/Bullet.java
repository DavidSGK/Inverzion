package com.davidsgk.inverzion.entity;

import com.badlogic.gdx.math.Vector2;
import com.davidsgk.inverzion  .Inverzion;
import com.davidsgk.inverzion.TextureManager;

public class Bullet extends Entity{

    public Bullet(Vector2 pos, Vector2 direction){
        super(TextureManager.BULLET, pos, direction);
    }

    private final float speed = 25;

    @Override
    public void update() {
        pos.add(direction);
    }

    public float getSpeed(){
        return speed;
    }

    public Vector2 getDirection(){
        return direction;
    }

    public void setPos(Vector2 newPos){
        pos = newPos;
    }

    public boolean checkHorizontalEnd(){
        return pos.x + texture.getWidth() < 0 || pos.x > Inverzion.WIDTH;
    }

    public boolean checkVerticalEnd(){
        return pos.y + texture.getHeight() < 0 || pos.y > Inverzion.HEIGHT;
    }
}
