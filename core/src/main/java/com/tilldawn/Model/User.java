package com.tilldawn.Model;

import com.tilldawn.Main;
import com.tilldawn.View.GameView;
import com.tilldawn.View.MainMenuView;

public class User {
    public String username;
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
