package com.mygdx.game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
public class BulletFactory {
    public static Bullet createBullet(Vector2 position, Vector2 velocity, float damage) {
        return new Bullet(position, velocity, damage);
    }
}
