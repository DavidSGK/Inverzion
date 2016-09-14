package com.davidsgk.inverzion.entity;

import com.badlogic.gdx.math.Vector2;
import com.davidsgk.inverzion.TextureManager;

public class Enemy extends Entity{

    public Enemy(Vector2 pos, Vector2 direction){
        super(TextureManager.ENEMY, pos, direction);
    }

    public void track(Player player){
        float magnitude = new Vector2(player.getPosition().x - pos.x, player.getPosition().y - pos.y).len();
        Vector2 trackV = new Vector2(player.getPosition().x - pos.x, player.getPosition().y - pos.y).scl(150 / magnitude);
        setDirection(trackV.x, trackV.y);
    }

    @Override
    public void update() {
        pos.add(direction);
    }
}
