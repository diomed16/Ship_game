package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Background {
    private Texture tileTexture;
    private int screenWidth;
    private int screenHeight;

    public Background(String tileTexturePath) {
        tileTexture = new Texture(Gdx.files.internal(tileTexturePath));
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    public void render(SpriteBatch batch) {
        int tileWidth = tileTexture.getWidth();
        int tileHeight = tileTexture.getHeight();

        for (int x = 0; x < screenWidth; x += tileWidth) {
            for (int y = 0; y < screenHeight; y += tileHeight) {
                batch.draw(tileTexture, x, y);
            }
        }
    }

    public void dispose() {
        tileTexture.dispose();
    }
}