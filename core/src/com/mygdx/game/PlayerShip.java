package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class PlayerShip implements Collidable, Updatable {
    private Vector2 position;
    private Texture texture;
    private Rectangle bounds;
    private float speed = 200; // Скорость корабля в пикселях в секунду
    private Array<Bullet> bullets; // Список пуль
    private Texture bulletTexture; // Текстура для пуль

    public PlayerShip(Texture texture, Texture bulletTexture, float x, float y) {
        this.texture = texture;
        this.bulletTexture = bulletTexture;
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.bullets = new Array<>();
    }
    @Override
    public Rectangle getBounds() {
        return bounds;
    }
    @Override
    public void onCollision(Collidable other) {
       // действие при столкновении
    }
    public void update(float deltaTime) {
        for (int i = 0; i < bullets.size; i++) {
            bullets.get(i).update(deltaTime);
            if (!bullets.get(i).isAlive()) { // Проверка, жива ли пуля
                bullets.removeIndex(i);
                i--;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
    }

    public void shoot() {
        float damage =  10;
        Vector2 bulletPosition = new Vector2(position.x + texture.getWidth() / 2 - bulletTexture.getWidth() / 2, position.y + texture.getHeight());
        Vector2 velocity = new Vector2(0, 300); // Направление и скорость пули
        Bullet bullet = new Bullet(bulletPosition, velocity,damage,  bulletTexture);
        bullets.add(bullet);
    }
    public void setPosition(float position)
    {
        this.position.x = position;
    }
    public void dispose() {
        texture.dispose();
        bulletTexture.dispose();
        for (Bullet bullet : bullets) {
            bullet.dispose();
        }
    }
}
