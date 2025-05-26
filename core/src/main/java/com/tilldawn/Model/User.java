package com.tilldawn.Model;

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
}
