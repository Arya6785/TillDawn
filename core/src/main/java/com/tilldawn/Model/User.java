package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Main;
import com.tilldawn.View.GameView;
import com.tilldawn.View.MainMenuView;

public class User {
    public String username = "";
    public String password;
    public User(String username, String password,String answer,String Question) {
        this.username = username;
        this.password = password;
        SecurityAnswer = answer;
        this.Question = Question;
    }
    public String SecurityAnswer;
    public String Question;
    public String getUsername() {
        return username;
    }
    public Texture avatarTexture;
    public Texture getRandomAvatarTexture() {
        int index = (int)(Math.random() * 5) + 1; // 1 تا 5
        return new Texture("Avatars/avatar" + index + ".png");
    }

    public Player player;
    public int KillCount = 0;
    public int SecondsSurvived = 0;
    public int getKills() {
        return KillCount;
    }
    public int getSecondsSurvived() {
        return SecondsSurvived;
    }
    public int getScore() {
        return KillCount*SecondsSurvived;
    }
}
