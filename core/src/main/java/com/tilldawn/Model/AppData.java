package com.tilldawn.Model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Model.Enums.Menu;

import java.util.ArrayList;

public class AppData {
    public static Menu currentMenu = Menu.MAINMENU;
    public static ArrayList<User>  users= new ArrayList<>();
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
        Dialog dialog = new Dialog("Message", skin);
        Label label = new Label(msg, skin);
        label.setFontScale(1.5f); // اگه بخوای بزرگ‌تر بشه
        dialog.text(label);
        dialog.button("OK");
        dialog.show(stage);
    }

}
