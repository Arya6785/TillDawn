package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GunSprite {
    private Texture texture;
    private Vector2 position = new Vector2();
    private float rotation;

    public GunSprite(String gunName) {
        texture = new Texture(Gdx.files.internal("T_DualShotgun_Gun.png"));
    }

    public void updatePositionAndRotation(Vector2 playerCenter, Vector2 mousePosition) {
        Vector2 direction = new Vector2(mousePosition).sub(playerCenter).nor(); // بردار جهت به سمت موس
        float distanceFromPlayer = 60f;
        position.set(playerCenter).add(direction.scl(distanceFromPlayer));

        rotation = direction.angleDeg();
    }

    public void draw(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(
                texture,
                position.x - texture.getWidth() / 2f,
                position.y - texture.getHeight() / 2f,
                texture.getWidth() / 2f,
                texture.getHeight() / 2f,
                texture.getWidth(),
                texture.getHeight(),
                5f, 5f,
                rotation,
                0, 0,
                texture.getWidth(),
                texture.getHeight(),
                false, false
            );
        }
    }
    public Vector2 getMuzzlePosition() {
        float gunLength = 30f;

        float radians = (float) Math.toRadians(rotation);

        float offsetX = (float) Math.cos(radians) * gunLength;
        float offsetY = (float) Math.sin(radians) * gunLength;

        return new Vector2(position.x + offsetX, position.y + offsetY);
    }

}



