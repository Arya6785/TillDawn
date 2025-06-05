package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.Texture;

public class EnemyR extends Enemy {
    public EnemyR(float x, float y, Animation<TextureRegion> animation) {
        super(x, y, 85f, 25, animation);
        this.width = 65f;
        this.height = 85f;
    }

    @Override
    public void dropSeed() {
        System.out.println("Seed dropped at: " + x + ", " + y);
    }

    // متدی که انیمیشن رو بسازه از فایل‌ها
    public static Animation<TextureRegion> loadAnimation() {
        TextureRegion[] frames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            Texture tex = new Texture(Gdx.files.internal("TenticleSpawn/TentacleSpawn" + i + ".png"));
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frames[i] = new TextureRegion(tex);
        }

        return new Animation<>(0.2f, frames); // هر فریم 0.2 ثانیه
    }
}
