package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class GameAssets {
    public static GameAssets gameAssets = new GameAssets();
    private final String character1_idle0 = "Heroes/Shana/Idle/0.png";
    private final String character1_idle1 = "Heroes/Shana/Idle/1.png";
    private final String character1_idle2 = "Heroes/Shana/Idle/2.png";
    private final String character1_idle3 = "Heroes/Shana/Idle/3.png";
    private final String character1_idle4 = "Heroes/Shana/Idle/4.png";
    private final String character1_idle5 = "Heroes/Shana/Idle/5.png";
    private final Texture character1_idle0_tex = new Texture(character1_idle0);
    private final Texture character1_idle1_tex = new Texture(character1_idle1);
    private final Texture character1_idle2_tex = new Texture(character1_idle2);
    private final Texture character1_idle3_tex = new Texture(character1_idle3);
    private final Texture character1_idle4_tex = new Texture(character1_idle4);
    private final Texture character1_idle5_tex = new Texture(character1_idle5);
    public final Animation<Texture> Shana = new Animation<>(0.1f, character1_idle0_tex, character1_idle1_tex, character1_idle2_tex, character1_idle3_tex, character1_idle4_tex, character1_idle5_tex);
}
