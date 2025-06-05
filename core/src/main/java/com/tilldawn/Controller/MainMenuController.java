package com.tilldawn.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tilldawn.Main;
import com.tilldawn.Model.AppData;
import com.tilldawn.Model.Enums.Menu;
import com.tilldawn.View.*;

public class MainMenuController {
    private final MainMenuView mainMenuView;
    public Main game;
    public MainMenuController(com.tilldawn.Main game, MainMenuView mainMenuView) {
        this.game = game;
        this.mainMenuView = mainMenuView;
    }
    public ClickListener Login(){
        return new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new LoginMenuView(game,mainMenuView));
            }
        };
    }

    public ClickListener SignUp(){
        return new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new SignUpMenuView(game,mainMenuView));
            }
        };
    }

    public ClickListener Profile(){
        return new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new ProfileMenuView(game));
            }
        };
    }
    public ClickListener PreGame(){
        return new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new PreGameView(game,mainMenuView));
            }
        };
    }
    public ClickListener ScoreBoard(){
        return new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new ScoreBoard(game,AppData.CurrentUser,AppData.users));
            }
        };
    }
    public ClickListener Settings() {
        return new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new SettingMenuView(game));
            }
        };
    }
    public ClickListener Talent(){
        return new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new Talent(game, AppData.users));
            }
        };
    }
}

