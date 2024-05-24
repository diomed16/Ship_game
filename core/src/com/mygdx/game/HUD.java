package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class HUD {
    private int score;
    private int lives;
    private int level;
    private BitmapFont font;
    private SpriteBatch batch;

    public HUD(SpriteBatch batch) {
        this.score = 0;
        this.lives = 3;
        this.level = 1;
        this.batch = batch;

    }

    public void render() {
        batch.begin();

        batch.end();
    }

    public void updateScore(int increment) {
        score += increment;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }
}
