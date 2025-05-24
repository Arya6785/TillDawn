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
import com.tilldawn.Controller.LoginMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.AppData;
import com.tilldawn.Model.User;

public class LoginMenuView implements Screen {
    private final LoginMenuController controller = new LoginMenuController();
    private final Main game;
    private final MainMenuView mainMenuView;
    private Stage stage;
    private Skin skin;

    public LoginMenuView(Main game, MainMenuView mainMenuView) {
        this.game = game;
        this.mainMenuView = mainMenuView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));

        TextField usernameField = new TextField("", skin);
        TextField passwordField = new TextField("", skin);

        usernameField.setMessageText("Enter Username");
        passwordField.setMessageText("Enter Password");


        TextButton Login = new TextButton("Login", skin);
        TextButton Back = new TextButton("Back", skin);
        TextButton Forgot = new TextButton("Forgot Password", skin);

        Login.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String result = controller.checkUser(username, password);

                if (result == "OK") {
                    AppData.CurrentUser = controller.getUser(username);
                    // game start
                }
                else {
                    Dialog dialog = new Dialog("Error", skin);
                    dialog.setWidth(110);
                    dialog.setHeight(40);
                    dialog.text(result);
                    dialog.button("OK");
                    dialog.show(stage);
                }

            }
        });

        Back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(mainMenuView);
            }
        });

        Forgot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                User user = controller.getUser(usernameField.getText());

                if (user != null) {
                    askSecurityQuestion(user, controller);
                } else {
                    showMessage("User not found.");
                }
            }
        });




        Texture background = new Texture(Gdx.files.internal("background.png"));
        Image backgroundImage = new Image(background);
        backgroundImage.setFillParent(true);

        Table LoginTable = new Table();

        LoginTable.setFillParent(true);
        LoginTable.center();
        LoginTable.add(usernameField).width(350).pad(10);
        LoginTable.row();
        LoginTable.add(passwordField).width(350).pad(10);
        LoginTable.row();
        LoginTable.add(Login).width(350).pad(10);
        LoginTable.row();
        LoginTable.add(Back).width(350).pad(10);
        LoginTable.row();
        LoginTable.add(Forgot).width(450).pad(10);

        stage.addActor(backgroundImage);

        stage.addActor(LoginTable);
    }
    private void askSecurityQuestion(User user, LoginMenuController controller) {
        final TextField answerField = new TextField("", skin);
        Dialog dialog = new Dialog("Security Question", skin) {
            @Override
            protected void result(Object object) {
                String answer = answerField.getText();
                if (controller.checkSecurityAnswer(user, answer)) {
                    showMessage("Correct! Your password is: " + user.password);
                } else {
                    showMessage("Incorrect answer.");
                }
            }
        };
        dialog.text(user.Question);
        dialog.getContentTable().row();
        dialog.getContentTable().add(answerField).width(200);
        dialog.button("Submit");
        dialog.show(stage);
    }


    private void showMessage(String msg) {
        Dialog dialog = new Dialog("Message", skin);
        Label label = new Label(msg, skin);
        label.setFontScale(1.5f); // اگه بخوای بزرگ‌تر بشه
        dialog.text(label);
        dialog.button("OK");
        dialog.show(stage);
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

    }
}
