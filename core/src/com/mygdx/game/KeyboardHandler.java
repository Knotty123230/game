package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class KeyboardHandler extends InputAdapter {
    private final Character character;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private static final float SPEED = 400f;

    public KeyboardHandler(Character character) {
        this.character = character;
        this.isLeftKeyPressed = false;
        this.isRightKeyPressed = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) {
            isLeftKeyPressed = true;
            isRightKeyPressed = false;
            character.setDirection(true);
            character.setRunning(true);
            return true;
        } else if (keycode == Input.Keys.D) {
            isRightKeyPressed = true;
            isLeftKeyPressed = false;
            character.setDirection(false);
            character.setRunning(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A && !isRightKeyPressed) {
            isLeftKeyPressed = false;
            character.setRunning(false);
            character.setDirection(true);
            return true;
        } else if (keycode == Input.Keys.D && !isLeftKeyPressed) {
            isRightKeyPressed = false;
            character.setRunning(false);
            character.setDirection(false);
            return true;
        }
        return false;
    }

    public void update(float delta) {
        if (isLeftKeyPressed) {
            character.getVector2().x -= SPEED * delta;
        } else if (isRightKeyPressed) {
            character.getVector2().x += SPEED * delta;
        }
    }
}
