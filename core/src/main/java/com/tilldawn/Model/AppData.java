package com.tilldawn.Model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Main;
import com.tilldawn.Model.Enums.Menu;
import com.tilldawn.View.GameView;
import com.tilldawn.View.MainMenuView;

import java.util.ArrayList;
import java.util.List;

public class AppData {
    public static List<User> users= new ArrayList<>();
    public static User CurrentUser = null;


    public static boolean isUsernameTaken(String newUsername) {
        for (User user : users) {
            if (user.getUsername().equals(newUsername)) {
                return true;
            }
        }
        return false;
    }
    public static void showMessage(String msg, Skin skin, Stage stage) {
        Dialog dialog = new Dialog(" ", skin);
        Label label = new Label(msg, skin);
        label.setFontScale(3f); // اگه بخوای بزرگ‌تر بشه
        dialog.text(label);
        dialog.button("OK");
        dialog.show(stage);
    }

}
