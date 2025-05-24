package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.Model.AppData;

public class ProfileMenuView implements Screen {
    private final Main game;
    private final Skin skin;
    private Stage stage;

    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField confirmPasswordField;
    public ProfileMenuView(Main game) {
        this.game = game;
        this.skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        confirmPasswordField = new TextField("", skin);
        buildUI();
    }
    private void buildUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        usernameField.setMessageText("New Username");
        passwordField.setMessageText("New Password");
        confirmPasswordField.setMessageText("Confirm Password");
        Texture background = new Texture(Gdx.files.internal("background.png"));
        Image backgroundImage = new Image(background);

        TextButton changeUsernameBtn = new TextButton("Change Username", skin);
        TextButton changePasswordBtn = new TextButton("Change Password", skin);
        TextButton backBtn = new TextButton("Back", skin);
        TextButton TerminateUser = new TextButton("Terminate User", skin);
        changeUsernameBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String newUsername = usernameField.getText();
                if (AppData.CurrentUser == null) {
                    AppData.showMessage("No user is currently logged in.", skin, stage);
                    return;
                }
                if (newUsername == null || newUsername.length() == 0) {
                    AppData.showMessage("Enter UserName", skin,stage);
                }
                if (AppData.isUsernameTaken(newUsername)) {
                    AppData.showMessage("Username already exists.", skin, stage);
                } else {
                    AppData.CurrentUser.username = newUsername;
                    AppData.showMessage("Username changed successfully.", skin, stage);
                }
            }
        });
        TerminateUser.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(AppData.CurrentUser == null) {
                    AppData.showMessage("No user is currently logged in.", skin, stage);
                    return;
                }
                AppData.users.remove(AppData.CurrentUser);
                AppData.CurrentUser = null;
                AppData.showMessage("Username removed successfully.", skin, stage);
            }
        });
        changePasswordBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String newPass = passwordField.getText();
                String confirmPass = confirmPasswordField.getText();

                if (!newPass.equals(confirmPass)) {
                    AppData.showMessage("Passwords do not match.", skin, stage);
                } else if (!isPasswordStrong(newPass)) {
                    AppData.showMessage("Password is too weak.", skin, stage);
                } else {
                    AppData.CurrentUser.password = newPass;
                    AppData.showMessage("Password changed successfully.", skin, stage);
                }
            }
        });

        backBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                // برگرد به صفحه قبل
                game.setScreen(new MainMenuView(game));
            }
        });

        table.add(usernameField).width(300).pad(10); table.row();
        table.add(changeUsernameBtn).width(500).pad(10); table.row();
        table.add(passwordField).width(300).pad(10); table.row();
        table.add(confirmPasswordField).width(300).pad(10); table.row();
        table.add(changePasswordBtn).width(500).pad(10); table.row();
        table.add(TerminateUser).width(500).pad(10); table.row();
        table.add(backBtn).width(300).pad(10);
        stage.addActor(backgroundImage);
        stage.addActor(table);
    }
    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*[0-9].*") &&
            password.matches(".*[@%$#&*()_].*");
    }

    @Override
    public void show (){

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
        stage.dispose(); skin.dispose();
    }
}
