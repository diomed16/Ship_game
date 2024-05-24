package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.graphics.Texture;
public class EnemySpawner {
    private long lastSpawnTime;
    private long spawnInterval;
    private Random random;
    private SpriteBatch batch;
    private Texture texture;
    private Game game;
    public EnemySpawner(Game game,SpriteBatch batch, Texture texture, long spawnInterval) {
        this.lastSpawnTime = 0;
        this.spawnInterval = spawnInterval;
        this.random = new Random();
        this.batch = batch;
        this.texture = texture;
        this.game = game;
    }

    @SuppressWarnings("NewApi")
    public void update(List<Enemy> enemies, float deltaTime) {
        long currentTime = TimeUtils.millis();
        if (currentTime - lastSpawnTime > spawnInterval) {
            spawnEnemy(enemies);
            lastSpawnTime = currentTime;
        }

        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }

        enemies.removeIf(Enemy::isDestroyed);
    }

    private void spawnEnemy(List<Enemy> enemies) {
        int x = random.nextInt(800);
        Vector2 position = new Vector2(x,480); // Пример координаты X (ширина экрана 800 пикселей)

        float width = 20; // Ширина врага
        float height = 20; // Высота врага
        Enemy enemy = new Enemy(game,position, width, height);
        enemies.add(enemy);
    }

    public void render(List<Enemy> enemies) {
        batch.begin();
        for (Enemy enemy : enemies) {
            enemy.render(batch, texture);
        }
        batch.end();
    }
}

