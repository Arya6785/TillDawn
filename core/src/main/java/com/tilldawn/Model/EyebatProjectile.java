package com.tilldawn.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EyebatProjectile {
    public Vector2 position, velocity;
    public int damage;
    public boolean active = true;
    private TextureRegion texture;
    private float speed = 200;

    public EyebatProjectile(Vector2 pos, Vector2 dir, int damage, TextureRegion texture) {
        this.position = pos;
        this.velocity = dir.scl(speed);
        this.damage = damage;
        this.texture = texture;
    }

    public void update(float delta) {
        if (!active) return;
        position.mulAdd(velocity, delta);
    }

    public void render(SpriteBatch batch) {
        if (active)
            batch.draw(texture, position.x, position.y);
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, texture.getRegionWidth(), texture.getRegionHeight());
    }

    public void deactivate() {
        active = false;
    }
}
