package com.davidsgk.inverzion.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.davidsgk.inverzion.Inverzion;
import com.davidsgk.inverzion.TextureManager;
import com.davidsgk.inverzion.camera.OrthoCamera;

public class EntityManager {

    private final Array<Enemy> enemies = new Array<Enemy>();
    private final Array<Bullet> bullets = new Array<Bullet>();
    private final Array<Platform> platforms = new Array<Platform>();
    private final Player player;
    private long pDelay = System.currentTimeMillis();
    private Color pColor = new Color();
    private Color playerColor = new Color(Color.BLACK);

    public EntityManager(int amount, OrthoCamera camera){
        player = new Player(new Vector2(Inverzion.WIDTH/2, Inverzion.HEIGHT/2), new Vector2(0, 0), this, camera, playerColor);
        for(int i = 0; i < amount; i++){
            float x, y;
            if(MathUtils.random(0, 1) == 0){
                x = MathUtils.random(0, Inverzion.WIDTH - TextureManager.ENEMY.getWidth());
                if(MathUtils.random(0, 1) == 0){
                    y = MathUtils.random(Inverzion.HEIGHT, Inverzion.HEIGHT * 2);
                } else {
                    y = -MathUtils.random(TextureManager.ENEMY.getHeight(), Inverzion.HEIGHT);
                }
            } else {
                y = MathUtils.random(0, Inverzion.HEIGHT - TextureManager.ENEMY.getHeight());
                if(MathUtils.random(0, 1) == 0){
                    x = MathUtils.random(Inverzion.WIDTH, Inverzion.WIDTH * 2);
                } else {
                    x = -MathUtils.random(TextureManager.ENEMY.getWidth(), Inverzion.WIDTH);
                }
            }
            addEnemy(new Enemy(new Vector2(x, y), new Vector2(0, 0)));
        }
    }

    public void update(){
        for(Enemy e : enemies){
            e.track(player);
            e.update();
        }
        for(Bullet b : bullets){
            if(b.checkHorizontalEnd()){
                //b.setPos(new Vector2(Inverzion.WIDTH - b.getPosition().x, b.getPosition().y));
                bullets.removeValue(b, false);
            }
            if(b.checkVerticalEnd()){
                //b.setPos(new Vector2(b.getPosition().x, Inverzion.HEIGHT - b.getPosition().y));
                bullets.removeValue(b, false);
            }
            b.update();
        }
        for(Platform p : platforms){
            if(p.getPosition().x + p.getLength() < 0){
                platforms.removeValue(p, false);
            }
            p.update();
        }
        if(System.currentTimeMillis() - pDelay > 1300){
            if(MathUtils.random(0, 1) == 0){
                pColor = Color.BLACK;
            } else {
                pColor = Color.WHITE;
            }
            addPlatform(new Platform(new Vector2(Inverzion.WIDTH, MathUtils.random(0, Inverzion.HEIGHT / 4)),
                    new Vector2(MathUtils.random(2, 6) * -1, 0), MathUtils.random(75, 200), pColor));
            pDelay = System.currentTimeMillis();
        }
        player.update();
        checkCollisions();
    }

    public void render(SpriteBatch sb){
        for(Enemy e : enemies){
            e.render(sb);
        }
        for(Bullet b : bullets){
            b.render(sb);
        }
        for(Platform p : platforms){
            p.render(sb);
        }
        player.render(sb);
    }

    private void checkCollisions(){
        for(Enemy e: enemies){
            for(Bullet b: bullets){
                /*if(e.getBounds().contains(b.getBounds())){
                    enemies.removeValue(e, false);
                    bullets.removeValue(b, false);
                }*/
                if(Intersector.overlaps(e.getBounds(), b.getBounds())){
                    enemies.removeValue(e, false);
                    bullets.removeValue(b, false);
                }
            }
        }
    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }

    public void addPlatform(Platform platform){
        platforms.add(platform);
    }

    public Player getPlayer(){
        return player;
    }

    public Array<Enemy> getEnemies(){
        return enemies;
    }

    public Array<Bullet> getBullets(){
        return bullets;
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public boolean gameOver(){
        return getEnemies().size <= 0;
    }
}
