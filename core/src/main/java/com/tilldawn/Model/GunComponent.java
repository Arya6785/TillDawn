package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GunComponent {
    private Gun gun;
    private Texture texture;
    private Sprite sprite;

    public GunComponent(Gun gun) {
        this.gun = gun;
        this.texture = new Texture(Gdx.files.internal("T_DualShotgun_Gun.png")); // مسیر تصویر تفنگ
        this.sprite = new Sprite(texture);
        sprite.setOriginCenter();
    }

    public void updatePositionAndRotation(float playerX, float playerY, float mouseX, float mouseY) {
        sprite.setPosition(playerX, playerY);
        float angle = (float) Math.toDegrees(Math.atan2(mouseY - playerY, mouseX - playerX));
        sprite.setRotation(angle);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void dispose() {
        texture.dispose();
    }
}

