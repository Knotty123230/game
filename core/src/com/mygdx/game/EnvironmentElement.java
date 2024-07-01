package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.*;

public class EnvironmentElement {
    private final Map<TextureRegion, List<Float>> elements;
    private final float regionWidth;
    private float prevPosition;
    private final List<TextureRegion> elementRegions;

    public EnvironmentElement(List<TextureRegion> elementRegions) {
        this.elements = new HashMap<>();
        this.prevPosition = 0;
        this.elementRegions = elementRegions;
        this.regionWidth = elementRegions.get(0).getRegionWidth(); // Assuming all elements have the same width
        generatePositions();
    }

    private void generatePositions() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) { // Generate 10 random positions as an example
            addElement(random);
        }
    }

    private void addElement(Random random) {
        int size = elementRegions.size();
        if (size == 0) return; // Prevents error if the list is empty

        TextureRegion textureRegion = elementRegions.get(random.nextInt(size));
        float x;
        do {
            x = prevPosition + regionWidth + random.nextFloat() * regionWidth;
        } while (x - prevPosition <= regionWidth / 2);
        prevPosition = x;

        elements.computeIfAbsent(textureRegion, k -> new ArrayList<>()).add(x);
    }

    public void draw(SpriteBatch batch) {
        System.out.println(elements);
        for (Map.Entry<TextureRegion, List<Float>> entry : elements.entrySet()) {
            TextureRegion textureRegion = entry.getKey();
            for (Float position : entry.getValue()) {
                batch.draw(textureRegion, position, 0);
            }
        }
    }

    public void update(float cameraX) {
        Random random = new Random();
        // Generate new positions when the camera moves forward
        while (prevPosition < cameraX + Gdx.graphics.getWidth()) {
            addElement(random);
        }
    }
}
