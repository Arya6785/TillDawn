package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.View.GameView;

public class Eyebat extends Enemy {

    private float shootCooldown = 3f;
    private float shootTimer = 0f;
    private TextureRegion projectileTexture;

    public Eyebat(float x, float y, Animation<TextureRegion> animation, TextureRegion projectileTexture) {
        super(x, y, 95f, 50, animation);  // سرعت 50 و hp 50

        this.projectileTexture = projectileTexture;
    }

    public void update(Vector2 target, float delta,GameView view) {
        super.update(target, delta);
        shootTimer += delta;
        if (shootTimer >= shootCooldown) {
            shootTimer = 0;
            shootAt(target,view);
        }
    }

    private void shootAt(Vector2 target, GameView view) {
        Vector2 direction = new Vector2(target).sub(getPosition()).nor();
        EyebatProjectile proj = new EyebatProjectile(getPosition().cpy(), direction, 1, projectileTexture);
        view.enemyProjectiles.add(proj);
    }

    @Override
    public void dropSeed() {
        System.out.println("Eyebat seed dropped at: " + x + ", " + y);
    }

    public static Animation<TextureRegion> loadAnimation() {
        TextureRegion[] frames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            Texture tex = new Texture(Gdx.files.internal("EyeMonster/EyeMonster_" + i + ".png"));
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frames[i] = new TextureRegion(tex);
        }
        return new Animation<>(0.15f, frames);
    }
}
