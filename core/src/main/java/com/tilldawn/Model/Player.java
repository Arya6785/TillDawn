package com.tilldawn.Model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.Enums.Ability;
import com.tilldawn.Model.Enums.HeroStats;
import com.tilldawn.View.GameView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Player extends Actor {
    public Animation<TextureRegion> idleAnim, walkAnim, runAnim;
    public float stateTime = 0;
    public String currentState = "Idle";
    public String characterName;
    public int health;
    public float speed;
    public int maxHealth;
    public boolean invincible = false;
    public float invincibilityTime = 0;
    public final float Invincibility_Duration = 2f;
    public GunComponent gunComponent;
    public GunSprite gunSprite;
    public Gun gun;
    public List<Bullet> bullets = new ArrayList<>();
    public int KillCount = 0;
    public int SecondsSurvived = 0;
    public int xp = 0;
    public int level = 0;
    public void addXP(int amount,GameView view) {
        xp += amount;
        checkLevelUp(view);
    }

    private void checkLevelUp(GameView view) {
        int requiredXP = calculateRequiredXP(level);
        while (xp >= requiredXP) {
            xp -= requiredXP;
            level++;
            onLevelUp(view);
            requiredXP = calculateRequiredXP(level);
        }
    }

    private int calculateRequiredXP(int currentLevel) {
        // exp لازم برای رفتن از لول i به i+1 برابر i*20 است
        return currentLevel * 20;
    }

    private void onLevelUp(GameView view) {
        Ability newAbility = Ability.getRandomAbility();
        view.controller.grantAbility(view,Ability.SPEEDY);

        // اینجا باید ابیلیت‌ها رو به کاربر بده
    }
    public int getScore() {
        return KillCount*SecondsSurvived;
    }


    public Player(String characterName, GameView view,Gun gun) {
        this.characterName = characterName;
        idleAnim = loadAnimation("Idle");
        walkAnim = loadAnimation("walk");
        runAnim = loadAnimation("run");
        this.gun = gun;
        HeroStats stats = HeroStats.valueOf(characterName.toUpperCase());
        this.health = stats.health;
        this.speed = stats.speed;
        this.maxHealth = this.health;
        TextureRegion firstFrame = idleAnim.getKeyFrame(0);
        float originalWidth = firstFrame.getRegionWidth();
        float originalHeight = firstFrame.getRegionHeight();
        setSize(originalWidth * 3.5f, originalHeight * 3.5f);
        this.gunSprite = new GunSprite(gun.name);
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
    public void updateGun(GameView view) {
        Vector3 mouseWorld = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        view.camera.unproject(mouseWorld); // اگه دوربین داری

        gunComponent.updatePositionAndRotation(getX(), getY(), mouseWorld.x, mouseWorld.y);
    }

    public void renderGun(SpriteBatch batch) {
        gunComponent.render(batch);
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
