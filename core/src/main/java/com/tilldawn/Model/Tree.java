package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Tree extends Enemy{
    private float x;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private float y;
    private Animation<TextureRegion> animation;
    private float stateTime = 0f;
    private static final float FRAME_DURATION = 0.8f;
    private static final float WIDTH = 210;
    private static final float HEIGHT = 270;

    public Tree(float x, float y, Animation<TextureRegion> animation) {

        super(x, y, 85f, 25, animation);
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.stateTime = 0f;

    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y, WIDTH, HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void dropSeed() {

    }

    public static Animation<TextureRegion> loadAnimation() {
        Array<TextureRegion> frames = new Array<>();

        // اضافه کردن فریم‌ها از پوشه Tree (اسم‌ها رو دقیق طبق فایل‌های واقعی تنظیم کن)
        frames.add(new TextureRegion(new Texture("Tree/T_TreeMonster_0.png")));
        frames.add(new TextureRegion(new Texture("Tree/T_TreeMonster_1.png")));
        frames.add(new TextureRegion(new Texture("Tree/T_TreeMonster_2.png")));

        return new Animation<TextureRegion>(FRAME_DURATION, frames, Animation.PlayMode.LOOP);
    }
}
