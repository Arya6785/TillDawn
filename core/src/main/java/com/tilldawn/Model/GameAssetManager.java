package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    public GameAssetManager gameAssetManager = new GameAssetManager();
    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    private Skin skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));



}
