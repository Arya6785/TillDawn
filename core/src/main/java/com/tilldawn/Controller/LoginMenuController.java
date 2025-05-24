package com.tilldawn.Controller;

import com.tilldawn.Model.AppData;
import com.tilldawn.Model.User;

public class LoginMenuController {

    public String checkUser(String username, String password) {
        if (getUser(username) == null) {
            return "User not found";
        }
        if (!checkPassword(username, password)) {
            return "Wrong Password";
        }
        return "OK";
    }

    public String ForgotPassword(String username) {
        if (username == null || username.equals("")) {
            return "Enter Username";
        }
        return "Forgot Password";

    }
    public boolean checkSecurityAnswer(User user, String answer) {
        return user.SecurityAnswer.equalsIgnoreCase(answer.trim());
    }


    public User getUser(String username) {
        for (User user : AppData.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private boolean checkPassword(String username, String password) {
        for (User user : AppData.users) {
            if (user.getUsername().equals(username)) {
                if (user.password.equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}
