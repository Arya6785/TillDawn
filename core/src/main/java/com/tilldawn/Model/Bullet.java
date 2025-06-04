package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    public Vector2 position;
    public Vector2 velocity;
    public float speed = 500f;
    public int damage;

    private Texture texture;
    private boolean active = true;

    public Bullet(Vector2 startPosition, Vector2 direction, int damage) {
        this.position = new Vector2(startPosition);
        this.velocity = direction.nor().scl(speed);
        this.damage = damage;
        this.texture = new Texture("bullet.png"); // یک عکس کوچک برای گلوله بساز
    }

    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);
    }

    public void draw(SpriteBatch batch) {
        if (active) {
            batch.draw(texture, position.x - 4, position.y - 4, 50, 50);
        }
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void dispose() {
        texture.dispose();
    }
}
