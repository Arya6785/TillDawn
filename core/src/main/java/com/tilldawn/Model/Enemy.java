package com.tilldawn.Model;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {
    public Animation<TextureRegion> animation;
    public float x, y;
    public float speed;
    public int hp;
    public boolean isDead;
    public float stateTime;

    protected float width, height;

    public Enemy(float x, float y, float speed, int hp, Animation<TextureRegion> animation) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.hp = hp;
        this.animation = animation;
        this.stateTime = 0f;
        this.isDead = false;

        TextureRegion firstFrame = animation.getKeyFrame(0);
        this.width = firstFrame.getRegionWidth();
        this.height = firstFrame.getRegionHeight();
    }

    public void update(Vector2 target, float delta) {
        if (isDead) return;

        stateTime += delta;

        Vector2 direction = new Vector2(target.x - x, target.y - y).nor();
        x += direction.x * speed * delta;
        y += direction.y * speed * delta;
    }

    public void render(SpriteBatch batch) {
        if (!isDead) {
            TextureRegion frame = animation.getKeyFrame(stateTime, true);
            batch.draw(frame, x, y);
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public abstract void dropSeed();

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp <= 0) {
            isDead = true;
            dropSeed();
        }
    }
    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

}
