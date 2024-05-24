package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.io.IOException;

public class PlayerShip implements Collidable, Updatable {
    private Game game;
    private Vector2 position;
    private Texture texture;
    private Rectangle bounds;
    private Array<Bullet> bullets; // Список пуль
    private Texture bulletTexture; // Текстура для пуль

    public PlayerShip(Texture texture, Texture bulletTexture, float x, float y) {
        this.game = game;
        this.texture = texture;
        this.bulletTexture = bulletTexture;
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
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
            if (bullets.get(i).isDestroyed()) { // Проверка, жива ли пуля
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
    public void setSize(float scaledWidth, float scaledHeight)
    {
     this.bounds.setSize(scaledWidth,scaledHeight) ;
    }
    public float getX()
    {
      return this.position.x;
    }
    public float getY()
    {
        return this.position.y;
    }
    public float getWidth()
    {
        return this.bounds.width;
    }
    public float getHeight()
    {
        return this.bounds.height;
    }

    public void shoot(Game game) {
        float damage =  10;
        Vector2 bulletPosition = new Vector2(position.x + texture.getWidth() / 2 - bulletTexture.getWidth() / 2, position.y + texture.getHeight()/2+bulletTexture.getHeight());
        Vector2 velocity = new Vector2(0, 1100); // Направление и скорость пули
        Bullet bullet = new Bullet(game,bulletPosition, velocity,damage);
        try {
            game.addBullet(bullet);
        }
        catch (NullPointerException e)
         {
            System.out.println(e.getMessage());
        }
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
