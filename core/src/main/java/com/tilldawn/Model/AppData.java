package com.tilldawn.Model;

import com.tilldawn.Model.Enums.Menu;

import java.util.ArrayList;

public class AppData {
    public static Menu currentMenu = Menu.MAINMENU;
    public static ArrayList<User>  users= new ArrayList<>();
    public static User CurrentUser = null;

}
