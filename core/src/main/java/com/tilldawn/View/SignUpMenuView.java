package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;

public class SignUpMenuView implements Screen {
    private final Main game;
    private Stage stage;
    private Skin skin;

    public SignUpMenuView(Main game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));

        Label username = new Label("Username", skin);
        Label password = new Label("Password", skin);

        TextField PasswordField = new TextField("", skin);
        TextField UsernameField = new TextField("", skin);

        TextButton Submit = new TextButton("Submit", skin);



        Table SignUpTable = new Table();
        SignUpTable.setFillParent(true);
        SignUpTable.center();
        SignUpTable.add(username).pad(10);
        SignUpTable.add(UsernameField).width(200).pad(10);
        SignUpTable.row();
        SignUpTable.add(password).pad(10);
        SignUpTable.add(PasswordField).width(200).pad(10);
        SignUpTable.row();
        SignUpTable.add(Submit).colspan(2).pad(20);

        stage.addActor(SignUpTable);




    }


    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
