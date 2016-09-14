package com.davidsgk.inverzion.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.davidsgk.inverzion.camera.OrthoCamera;
import com.davidsgk.inverzion.entity.EntityManager;

public class GameScreen extends Screen{

    private OrthoCamera camera;
    private EntityManager entityManager;
    private ShapeRenderer debugRenderer;

    @Override
    public void create() {
        camera = new OrthoCamera();
        entityManager = new EntityManager(0, camera);
        debugRenderer = new ShapeRenderer();
    }

    @Override
    public void update() {
        camera.update();
        entityManager.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        entityManager.render(sb);
        sb.end();
        /*debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
        debugRenderer.setColor(new Color(1, 0, 0, 1));
        Rectangle bounds = entityManager.getPlayer().getBounds();
        debugRenderer.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        for(Enemy e : entityManager.getEnemies()){
            bounds = e.getBounds();
            debugRenderer.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        }
        debugRenderer.setColor(new Color(0, 0, 1, 1));
        for(Bullet b : entityManager.getBullets()){
            bounds = b.getBounds();
            debugRenderer.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        }*/
        //debugRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
