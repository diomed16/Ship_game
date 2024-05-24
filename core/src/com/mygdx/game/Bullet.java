package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
public class Bullet implements Collidable, Updatable{
private boolean isProjectile;
private boolean isFlaming;
private boolean isImpulse;
private Vector2 position;
private Vector2 velocity;
private float damage;
private boolean alive;
private Texture texture;
private Rectangle bounds;
private boolean destroyed;

    public Bullet(Vector2 position, Vector2 velocity, float damage) {
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
        this.texture = texture;
        this.alive = true;
        this.destroyed = false;
        texture = new Texture(Gdx.files.internal("bullet.png"));
    }

    public void update(float deltaTime) {
        if (alive) {
            position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        }

    }

    public void draw(SpriteBatch batch) {
        if (alive) {
            batch.draw(texture, position.x, position.y);
        }
    }


    public boolean isDestroyed() {
        return destroyed;
    }
    public void dispose() {
        texture.dispose();
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void onCollision(Collidable other) {
        this.alive = false; // действие при столкновении
    }


}
