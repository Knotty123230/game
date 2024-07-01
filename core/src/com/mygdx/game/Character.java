package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Character extends Actor {
    private final Animation<TextureRegion> walkingAnimation;
    private float stateTime;
    private boolean isRunning;
    private boolean shouldFinishCurrentFrame;
    private boolean isFacingLeft;
    private final Vector2 position;

    public Character(Vector2  position) {
        this.position = position;
        TextureRegion[] walkFrames = new TextureRegion[12];
        for (int i = 0; i < 12; i++) {
            String fileName = String.format("Minotaur_1/PNG/PNG Sequences/Running/0_Minotaur_Running_%03d.png", i);
            Pixmap pixmap200 = new Pixmap(Gdx.files.internal(fileName));
            Pixmap pixmap100 = new Pixmap(250, 250, pixmap200.getFormat());
            pixmap100.drawPixmap(pixmap200,
                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
            );
            Texture texture = new Texture(pixmap100);
            walkFrames[i] = new TextureRegion(texture);
        }
        walkingAnimation = new Animation<>(0.05f, walkFrames);
        stateTime = 0f;
        isRunning = false;
        shouldFinishCurrentFrame = false;
        isFacingLeft = false;
    }

    public void setRunning(boolean running) {
        isRunning = running;
        if (!running) {
            shouldFinishCurrentFrame = true;
        }
    }

    public Vector2 getVector2() {
        return position;
    }

    public void setDirection(boolean facingLeft) {
        isFacingLeft = facingLeft;
    }

    public void update(float deltaTime) {
        if (isRunning || shouldFinishCurrentFrame) {
            int currentFrameIndex = walkingAnimation.getKeyFrameIndex(stateTime);
            stateTime += deltaTime;

            int newFrameIndex = walkingAnimation.getKeyFrameIndex(stateTime);
            if (currentFrameIndex != newFrameIndex && shouldFinishCurrentFrame) {
                shouldFinishCurrentFrame = false;
            }
            if (!isRunning) {
                stateTime = 0;
            }
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = walkingAnimation.getKeyFrame(stateTime, true);
        if ((isFacingLeft && !currentFrame.isFlipX()) || (!isFacingLeft && currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }
        batch.draw(currentFrame, position.x, position.y);
    }

    public void dispose() {
        for (TextureRegion frame : walkingAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
    }
}
