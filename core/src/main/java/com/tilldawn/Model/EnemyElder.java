package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.View.GameView;

public class EnemyElder extends Enemy {

    private float dashCooldown = 5f;
    private float timeSinceLastDash = 0f;
    private boolean isDashing = false;
    private float dashDuration = 0.3f;
    private float dashTime = 0f;
    private float dashSpeed = 800f;
    private Vector2 dashDirection;

    public EnemyElder(float x, float y,Animation<TextureRegion> animation) {
        super(x, y, 100f, 400, animation);
        this.width =85f;
        this.height = 100f;// hp 100, speed 400
    }


    public void update(Vector2 target, float delta, GameView view) {
        timeSinceLastDash += delta;

        if (isDashing) {
            dashTime += delta;
            if (dashTime < dashDuration) {
                // Dash movement
                x += dashDirection.x * dashSpeed * delta;
                y += dashDirection.y * dashSpeed * delta;
            } else {
                isDashing = false;
            }
        } else {
            if (timeSinceLastDash >= dashCooldown) {
                startDash(target);
                timeSinceLastDash = 0f;
            } else {
                // Normal movement toward player
                Vector2 direction = new Vector2(target.x - x, target.y - y).nor();
                x += direction.x * speed * delta;
                y += direction.y * speed * delta;
            }
        }
    }

    private void startDash(Vector2 target) {
        dashDirection = new Vector2(target.x - x, target.y - y).nor();
        isDashing = true;
        dashTime = 0f;
    }

    @Override
    public void dropSeed() {
        System.out.println("Elder seed dropped at: " + x + ", " + y);
    }

    public static Animation<TextureRegion> loadAnimation() {
        TextureRegion[] frames = new TextureRegion[1];
        Texture tex = new Texture(Gdx.files.internal("Elder/ElderBrain.png"));
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        frames[0] = new TextureRegion(tex);
        return new Animation<>(0.15f, frames);
    }
}
