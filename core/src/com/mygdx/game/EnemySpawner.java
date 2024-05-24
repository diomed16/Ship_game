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
        Vector2 position = new Vector2(x,650);

        float width = 309*0.5f; // Ширина врага
        float height = 208*0.5f; // Высота врага
        Enemy enemy = new Enemy(game,position, width, height);
        game.addEnemy(enemy);
    }

    public void render(List<Enemy> enemies) {
        batch.begin();
        for (Enemy enemy : enemies) {
            enemy.render(batch, texture);
        }
        batch.end();
    }
}

