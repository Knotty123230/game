package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ground {
    TextureRegion texture;

    public Ground(TextureRegion texture) {
        this.texture = texture;
    }

    public void draw(SpriteBatch batch, float x, float y, float cameraPosition) {
        float screenWidth = Gdx.graphics.getWidth();
        System.out.println(screenWidth);
        float textureWidth = texture.getRegionWidth();
        System.out.println("cameraPosition = " + cameraPosition);
        System.out.println("textureWidth = " + textureWidth);

        // Calculate the starting position for the textures
        float startX = cameraPosition - (cameraPosition % textureWidth) - textureWidth;
        System.out.println("startX = " + startX);
        // Calculate how many textures are needed to cover the screen width
        int count = (int) Math.ceil(screenWidth / textureWidth) + 2;
        System.out.println("count = " + count);
        // Draw the textures horizontally
        for (int i = 0; i < count; i++) {
            batch.draw(texture, startX + (i * textureWidth), y);
        }

    }
}
