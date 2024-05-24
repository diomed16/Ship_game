package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy implements Collidable, Updatable {
    private Vector2 position;
    private float width, height;
    private boolean destroyed;
    private Game game;
    public Enemy(Game game, Vector2 position, float width, float height) {
        this.position = new Vector2(position.x, position.y);
        this.position.x = position.x;
        this.position.y = position.y;
        this.width = width;
        this.height = height;
        this.destroyed = false;
        this.game = game;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, width, height);
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Bullet) {
            Gdx.app.log("Game", "Enemy destroyed");
            this.destroyed = true;
        }
    }

    @Override
    public void update(float deltaTime) {
        // Обновление состояния врага (например, движение вниз)
        //y -= 100 * deltaTime; // Пример движения вниз, скорость 100 пикселей в секунду
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    public void shoot() {
        Vector2 bulletPosition = new Vector2(position.x + width / 2, position.y);
        Vector2 velocity = new Vector2(0, -1100); // Направление и скорость пули
        Bullet bullet = new Bullet (game,bulletPosition, velocity, 5);
        game.addBullet(bullet);
    }
    public void render(SpriteBatch batch, Texture texture) {
        if(!destroyed)
        batch.draw(texture, position.x, position.y, width, height);
    }
}