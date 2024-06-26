package com.mygdx.game;
import com.badlogic.gdx.math.Rectangle;

public interface Collidable {
    Rectangle getBounds();
    void onCollision(Collidable other);
}
