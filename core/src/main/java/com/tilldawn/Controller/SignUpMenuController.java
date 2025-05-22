package com.tilldawn.Controller;

import com.tilldawn.Model.AppData;
import com.tilldawn.Model.User;

import com.tilldawn.Model.User;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Pattern;

public class SignUpMenuController {






        public String validateSignup(String username, String password) {
            if (username == null || username.equals("")) {
                return "Username is required";
            }

            if (isDuplicate(username))
                return "Username is already taken.";
            if (password == null || password.equals("")) {
                return "Password is required";
            }

            if (!isStrongPassword(password))
                return "Password is too weak.";

            AppData.users.add(new User(username, password));
            return "OK"; // موفقیت
        }

        private boolean isDuplicate(String username) {
            for (User user : AppData.users) {
                if (user.getUsername().equalsIgnoreCase(username))
                    return true;
            }
            return false;
        }

        private boolean isStrongPassword(String password) {
            if (password.length() < 8)
                return false;

            boolean hasUpper = Pattern.compile("[A-Z]").matcher(password).find();
            boolean hasDigit = Pattern.compile("[0-9]").matcher(password).find();
            boolean hasSpecial = Pattern.compile("[@%$#&*()_]").matcher(password).find();

            return hasUpper && hasDigit && hasSpecial;
        }


}
