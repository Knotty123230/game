package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Character character;
    private KeyboardHandler keyboardHandler;
    private Ground ground;
    private OrthographicCamera camera;
    private EnvironmentElement environmentElements;

    @Override
    public void create() {
        TextureRegion grassTexture = new TextureRegion(new Texture("nature_1/6.png"));
        TextureRegion treeTexture = new TextureRegion(new Texture("nature_1/5.png"));
        this.environmentElements = new EnvironmentElement(Arrays.asList(grassTexture, treeTexture));

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        character = new Character(new Vector2(0, 0));
        keyboardHandler = new KeyboardHandler(character);
        Gdx.input.setInputProcessor(keyboardHandler);
        this.ground = new Ground(new TextureRegion(new Texture("nature_1/8.png")));
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        keyboardHandler.update(delta);
        character.update(delta);

        // Update camera position to follow the character
        camera.position.set(character.getVector2().x, camera.viewportHeight / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ground.draw(batch, 0, 0, camera.position.x - camera.viewportWidth / 2);

        // Update and draw environment elements (grass and trees)
        environmentElements.update(camera.position.x);
        environmentElements.draw(batch);

        character.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        character.dispose();
    }
}
