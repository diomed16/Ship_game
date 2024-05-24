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
private Texture texture;
private Rectangle bounds;
private boolean destroyed;
private Game game;

    public Bullet(Game game, Vector2 position, Vector2 velocity, float damage) {
        this.game = game;
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
        this.destroyed = false;
        this.texture = new Texture(Gdx.files.internal("bullet.png"));
        this.bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void update(float deltaTime) {
        if (!destroyed) {
            position.add(velocity.x * deltaTime, velocity.y * deltaTime);
            bounds.setPosition(position.x, position.y);
        }

    }

    public void draw(SpriteBatch batch) {
        if (!destroyed) {
            batch.draw(texture, position.x, position.y);
        }
    }


    public boolean isDestroyed() {
        return destroyed;
    }
    public void dispose() {
        texture.dispose();
    }





    @Override
    public Rectangle getBounds() {

        return this.bounds;
    }

    @Override
    public void onCollision(Collidable other) {

        this.destroyed = true; // действие при столкновении
    }


}
