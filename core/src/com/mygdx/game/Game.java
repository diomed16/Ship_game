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
        enemyTexture = new Texture(Gdx.files.internal("user_ship2.png"));
        hud = new HUD(batch);
        enemySpawner = new EnemySpawner(this,batch, enemyTexture,2000); // Интервал спавна врагов в миллисекундах (2 секунды)
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
                    bullet.onCollision(enemy);
                    enemy.onCollision(bullet);
                }
            }
        }

        bullets.removeIf(Bullet::isDestroyed);
        enemies.removeIf(Enemy::isDestroyed);

        hud.updateScore(10); // Пример обновления счёта
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void addEnemy(Vector2 position, float width, float height) {
        Enemy newEnemy = new Enemy(this,position, width, height);
        enemies.add(newEnemy);
    }

    // Другие методы игры
}
