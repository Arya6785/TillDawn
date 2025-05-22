package com.tilldawn.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tilldawn.Main;
import com.tilldawn.Model.AppData;
import com.tilldawn.Model.Enums.Menu;
import com.tilldawn.View.SignUpMenuView;

public class MainMenuController {
    public Main game;
    public MainMenuController(com.tilldawn.Main game) {
        this.game = game;
    }
    public ClickListener Login(){
        return new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                AppData.currentMenu = Menu.LOGINMENU;
            }
        };
    }

    public ClickListener SignUp(){
        return new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new SignUpMenuView(game));
            }
        };
    }
}
