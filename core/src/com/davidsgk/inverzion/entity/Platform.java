package com.davidsgk.inverzion.entity;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Platform extends Entity {

    private final int length;
    private final int height = 32;
    private final Color color;

    public Platform(Vector2 pos, Vector2 direction, int length, Color color){
        super(platformTexture(length, 32, color), pos, direction);
        this.color = color;
        this.length = length;
    }

    private static Texture platformTexture(int length, int height, Color color){
        Pixmap pixmap = new Pixmap(length, height, Pixmap.Format.RGB888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, length, height);
        Texture pix2text = new Texture(pixmap);
        pixmap.dispose();
        return pix2text;
    }


    @Override
    public void update() {
        pos.add(direction);
    }

    public int getLength(){
        return this.length;
    }

    public Color getColor() {
        return this.color;
    }
}
