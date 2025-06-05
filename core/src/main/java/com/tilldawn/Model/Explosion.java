package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;

public class Explosion {
    private static Animation<TextureRegion> explosionAnimation;
    private float stateTime;
    private Vector2 position;
    private boolean finished;

    public Explosion(Vector2 position) {
        this.position = position;
        this.stateTime = 0;
        this.finished = false;
    }

    // یک‌بار از بیرون صدا زده میشه تا انیمیشن ساخته بشه
    public static void loadAssets() {
        TextureRegion[] frames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            Texture texture = new Texture("Explosion/ExplosionFX_" + i  + ".png");
            frames[i] = new TextureRegion(texture);
        }
        explosionAnimation = new Animation<>(0.1f, frames); // 0.1 ثانیه بین فریم‌ها
        explosionAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public void update(float delta) {
        stateTime += delta;
        if (explosionAnimation.isAnimationFinished(stateTime)) {
            finished = true;
        }
    }

    public void render(SpriteBatch batch) {
        if (!finished) {
            TextureRegion currentFrame = explosionAnimation.getKeyFrame(stateTime);
            batch.draw(currentFrame, position.x, position.y);
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
