package com.davidsgk.inverzion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {

    public static Texture PLAYER = new Texture(Gdx.files.internal("player_01.png"));
    public static Texture ENEMY = new Texture(Gdx.files.internal("enemy.png"));
    public static Texture BULLET = new Texture(Gdx.files.internal("bullet.png"));

}
