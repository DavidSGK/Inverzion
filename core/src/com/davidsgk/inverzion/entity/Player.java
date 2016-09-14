package com.davidsgk.inverzion.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.davidsgk.inverzion .Inverzion;
import com.davidsgk.inverzion.TextureManager;
import com.davidsgk.inverzion.camera.OrthoCamera;

public class Player extends Entity {

    private final EntityManager entityManager;
    private final OrthoCamera camera;
    private long reload;
    private Color color;
    private float speed = 50;

    public Player(Vector2 pos, Vector2 direction, EntityManager entityManager, OrthoCamera camera, Color color){
        super(playerTexture(64, 64, color), pos, direction);
        this.entityManager = entityManager;
        this.camera = camera;
        this.color = color;
    }

    private static Texture playerTexture(int length, int height, Color color){
        Pixmap pixmap = new Pixmap(length, height, Pixmap.Format.RGB888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, length, height);
        Texture pix2text = new Texture(pixmap);
        pixmap.dispose();
        return pix2text;
    }

    @Override
    public void update() {

        pos.add(direction); //change position based on velocity

        /*
        if(Math.sqrt(Math.pow(getDirection().x, 2) + Math.pow(getDirection().y, 2)) < 300 * Gdx.graphics.getDeltaTime()){
            if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)){
                addDirection(-speed / (float) Math.sqrt(2), speed / (float) Math.sqrt(2));
            } else if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)){
                addDirection(speed / (float) Math.sqrt(2), speed / (float) Math.sqrt(2));
            } else if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)){
                addDirection(-speed / (float) Math.sqrt(2), -speed / (float) Math.sqrt(2));
            } else if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)){
                addDirection(speed / (float) Math.sqrt(2), -speed / (float) Math.sqrt(2));
            } else if(Gdx.input.isKeyPressed(Input.Keys.W)){
                addDirection(0, speed);
            } else if(Gdx.input.isKeyPressed(Input.Keys.A)){
                addDirection(-speed, 0);
            } else if(Gdx.input.isKeyPressed(Input.Keys.S)){
                addDirection(0, -speed);
            } else if(Gdx.input.isKeyPressed(Input.Keys.D)){
                addDirection(speed, 0);
            }
        }*/

        //Left and right controls
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            this.direction.x = -300 * Gdx.graphics.getDeltaTime();
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            this.direction.x = 300 * Gdx.graphics.getDeltaTime();
        } else {
            this.direction.x = 0;
        }

        //Color switching
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if(this.color == Color.BLACK){
                this.color = Color.WHITE;
            } else {
                this.color = Color.BLACK;
            }
        }

        this.texture = playerTexture(64, 64, this.color);

        //Jump length depends on how long button is held
        /*if(Gdx.input.isKeyPressed(Input.Keys.W) && this.direction.y < 2000 * Gdx.graphics.getDeltaTime()){
            System.out.println("true");
            addDirection(0, 100 * Gdx.graphics.getDeltaTime());
        }*/


        //movement "slide"
        //addDirection(-getDirection().x * Gdx.graphics.getDeltaTime() * 200, -getDirection().y * Gdx.graphics.getDeltaTime() * 200);

        //proper touch/click coordinates
        Vector2 touch = new Vector2(0, 0);
        if(Gdx.input.isTouched()){
            touch = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
            this.setPosition(touch);
            this.setDirection(0, 0);
        }

        //shooting bullets
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            if(System.currentTimeMillis() - reload >= 250){
                Vector2 shootDirection = new Vector2(touch.x - pos.x, touch.y - pos.y);
                entityManager.addBullet(new Bullet(pos.cpy().add(texture.getWidth()/2, texture.getHeight()/2).sub(TextureManager.BULLET.getWidth()/2, TextureManager.BULLET.getHeight()/2), shootDirection.scl(1 / shootDirection.len() * 15)));
                reload = System.currentTimeMillis();
            }
        }

        //Interaction with platforms
        for(Platform p : entityManager.getPlatforms()){
            if(this.getBounds().getX() + this.getBounds().getWidth() >= p.getBounds().x &&
                    this.getBounds().getX() <= p.getBounds().getX() + p.getBounds().getWidth() &&
                    this.getBounds().getY() <= p.getBounds().getY() + p.getBounds().getHeight() &&
                    this.getBounds().getY() >= p.getBounds().getY() &&
                    this.getDirection().y <= 0 &&
                    this.color.equals(p.getColor())){
                this.pos.y += Math.abs(this.getBounds().getY() - (p.getBounds().getY() + p.getBounds().getHeight()));
                this.direction.y = 0;
                this.direction.x += p.getDirection().x;

                //Jumping only allowed while on a platform
                if(Gdx.input.isKeyPressed(Input.Keys.W)){
                    this.direction.y += 500 * Gdx.graphics.getDeltaTime();
                }

            }
        }

        //Prevent moving left or right off screen
        if(this.getBounds().getX() < 0 || this.getBounds().getX() > Inverzion.WIDTH){
            this.pos.x += Math.abs(this.getBounds().getX());
        } else if(this.getBounds().getX() + this.getBounds().getWidth() > Inverzion.WIDTH){
            this.pos.x -= Math.abs(this.getBounds().getX() + this.getBounds().getWidth() - Inverzion.WIDTH);
        }

        //Gravity
        addDirection(0, -980 * Gdx.graphics.getDeltaTime());
    }
}
