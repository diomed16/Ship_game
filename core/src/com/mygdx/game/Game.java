package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private SpriteBatch batch;
    private Texture enemyTexture;
    private HUD hud;
    private EnemySpawner enemySpawner;
    private List<Bullet> bullets;
    private List<Enemy> enemies;


    public Game() {
        batch = new SpriteBatch();
        enemyTexture = new Texture(Gdx.files.internal("Enemy_Blaser.png"));
        hud = new HUD(batch);
        enemySpawner = new EnemySpawner(this,batch, enemyTexture,2000); // Интервал спавна врагов в миллисекундах
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    public void render() {
        // Другие операции отрисовки
        enemySpawner.render(enemies);
        batch.begin();
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
        batch.end();
        hud.render();
    }
    @SuppressWarnings("NewApi")
    public void update(float deltaTime) {
        // Обновление состояния игры
        enemySpawner.update(enemies, deltaTime);

        for (Bullet bullet : bullets) {
            bullet.update(deltaTime);
        }

        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {

                if (bullet.getBounds().overlaps(enemy.getBounds())) {
                    Gdx.app.log("Game", "Collision");
                    bullet.onCollision(enemy);
                    enemy.onCollision(bullet);
                }
            }
        }

        bullets.removeIf(Bullet::isDestroyed);
        enemies.removeIf(Enemy::isDestroyed);

        hud.updateScore(10);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void addEnemy(Enemy enemy) {

        enemies.add(enemy);
    }




}
