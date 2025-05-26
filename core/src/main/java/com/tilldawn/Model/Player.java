package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.Comparator;

public class Player extends Actor {
    public Animation<TextureRegion> idleAnim, walkAnim, runAnim;
    public float stateTime = 0;
    public String currentState = "Idle";
    public String characterName;

    public Player(String characterName) {
        this.characterName = characterName;
        idleAnim = loadAnimation("Idle");
        walkAnim = loadAnimation("walk");
        runAnim = loadAnimation("run");
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


    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame;

        switch (currentState) {
            case "walk": currentFrame = walkAnim.getKeyFrame(stateTime); break;
            case "run": currentFrame = runAnim.getKeyFrame(stateTime); break;
            case "Idle": currentFrame = idleAnim.getKeyFrame(stateTime); break;
            default: currentFrame = idleAnim.getKeyFrame(stateTime); break;
        }
        batch.draw(currentFrame, getX(), getY());
    }

    public void setState(String state) {
        this.currentState = state;
    }
}
