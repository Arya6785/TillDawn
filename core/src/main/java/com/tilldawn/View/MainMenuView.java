package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.MainMenuController;
import com.tilldawn.Main;

public class MainMenuView implements Screen {
    public final Main game;
    private Stage stage;
    private Skin skin;
    private MainMenuController controller;

    public MainMenuView(Main game) {
        this.game = game;
        this.controller = new MainMenuController(game,this);
    }

    @Override
    public void show() {
        stage  = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));

        Texture background = new Texture(Gdx.files.internal("background.png"));
        Image backgroundImage = new Image(background);
        backgroundImage.setFillParent(true);

        TextButton loginButton = new TextButton("Login", skin);
        TextButton registerButton = new TextButton("SignUp", skin);
        TextButton profileMenu = new TextButton("Profile", skin);
        TextButton ScoreButton = new TextButton("ScoreBoard", skin);
        TextButton PreGameMenu = new TextButton("Pregame", skin);
        TextButton Talent = new TextButton("Talent", skin);

        Table MainMenuTable = new Table();
        MainMenuTable.setFillParent(true);

        MainMenuTable.center();
        MainMenuTable.add(loginButton).width(300).height(80).pad(15);
        MainMenuTable.row();
        MainMenuTable.add(registerButton).width(300).height(80).pad(15);
        MainMenuTable.row();
        MainMenuTable.add(ScoreButton).width(300).height(80).pad(15);
        MainMenuTable.row();
        MainMenuTable.add(PreGameMenu).width(300).height(80).pad(15);
        MainMenuTable.row();
        MainMenuTable.add(profileMenu).width(300).height(80).pad(15);
        MainMenuTable.row();
        MainMenuTable.add(Talent).width(300).height(80).pad(15);
        MainMenuTable.row();
        stage.addActor(backgroundImage);
        stage.addActor(MainMenuTable);


        loginButton.addListener(controller.Login());
        registerButton.addListener(controller.SignUp());
        profileMenu.addListener(controller.Profile());
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
