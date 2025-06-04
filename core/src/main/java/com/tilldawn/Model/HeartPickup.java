package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class HeartPickup {
    private Texture texture;
    private Vector2 position;
    private float size = 25f; // اندازه آیتم (قابل تغییر)

    public HeartPickup(Vector2 position) {
        this.texture = new Texture("T_HeartPickup.png");
        this.position = position;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, size, size);
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, size, size);
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }
}
