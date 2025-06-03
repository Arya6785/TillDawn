package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.Enums.HeroStats;

import java.util.Arrays;
import java.util.Comparator;

public class Player extends Actor {
    public Animation<TextureRegion> idleAnim, walkAnim, runAnim;
    public float stateTime = 0;
    public String currentState = "Idle";
    public String characterName;
    public int health;
    public int speed;
    public int maxHealth;
    public boolean invincible = false;
    public float invincibilityTime = 0;
    public final float Invincibility_Duration = 5f;
    public Player(String characterName) {
        this.characterName = characterName;
        idleAnim = loadAnimation("Idle");
        walkAnim = loadAnimation("walk");
        runAnim = loadAnimation("run");
        HeroStats stats = HeroStats.valueOf(characterName.toUpperCase());
        this.health = stats.health;
        this.speed = stats.speed;
        this.maxHealth = this.health;
        TextureRegion firstFrame = idleAnim.getKeyFrame(0);
        float originalWidth = firstFrame.getRegionWidth();
        float originalHeight = firstFrame.getRegionHeight();
        setSize(originalWidth * 3.5f, originalHeight * 3.5f);
    }
    private Animation<TextureRegion> loadAnimation(String state) {
        try {
            FileHandle dirHandle = Gdx.files.internal("Heroes/"+ characterName+ "/" + state);
            FileHandle[] files = dirHandle.list();

            if (files == null || files.length == 0) {
                Gdx.app.error("Player", "No files found for " + state);
            }

            Arrays.sort(files, Comparator.comparing(FileHandle::name));
            Array<TextureRegion> frames = new Array<>();
            for (FileHandle file : files) {
                Texture texture = new Texture(file);
                frames.add(new TextureRegion(texture));
            }

            return new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        } catch (Exception e) {
            Gdx.app.error("Player", "Error loading animation for state: " + state, e);
            return null;
        }
    }
    private boolean facingRight = true;

    public void setFacingRight(boolean right) {
        if (this.facingRight != right) {
            this.facingRight = right;
            flipAnimationFrames(idleAnim);
            flipAnimationFrames(walkAnim);
            flipAnimationFrames(runAnim);
        }
    }
    public Vector2 getCenter() {
        return new Vector2(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
    }
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    private void flipAnimationFrames(Animation<TextureRegion> anim) {
        Object[] keyFrames = anim.getKeyFrames();
        for (Object obj : keyFrames) {
            if (obj instanceof TextureRegion) {
                TextureRegion region = (TextureRegion) obj;
                region.flip(true, false);
            }
        }
    }




    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame;

        switch (currentState) {
            case "run": currentFrame = runAnim.getKeyFrame(stateTime); break;
            case "Idle": currentFrame = idleAnim.getKeyFrame(stateTime); break;
            default: currentFrame = idleAnim.getKeyFrame(stateTime); break;
        }
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }
    public void update(float delta) {
        if (invincible) {
            invincibilityTime += delta;
            if (invincibilityTime >= Invincibility_Duration) {
                invincible = false;
                invincibilityTime = 0;
            }
        }
    }

    public void setState(String state) {
        this.currentState = state;
    }
}
