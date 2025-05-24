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
import com.tilldawn.Controller.SignUpMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.AppData;
import com.tilldawn.Model.User;

import java.util.ArrayList;

public class SignUpMenuView implements Screen {
    private final SignUpMenuController controller = new SignUpMenuController();
    private final MainMenuView mainMenuView;
    private final Main game;
    private Stage stage;
    private Skin skin;
    public List<String> securityQuestions;

    public SignUpMenuView(Main game, MainMenuView mainMenuView) {
        this.game = game;
        this.mainMenuView = mainMenuView;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));

        Label username = new Label("Username", skin);
        Label password = new Label("Password", skin);

        TextField PasswordField = new TextField("", skin);
        PasswordField.setMessageText("Enter your password");
        TextField UsernameField = new TextField("", skin);
        UsernameField.setMessageText("Enter your username");

        Texture background = new Texture(Gdx.files.internal("background.png"));
        Image backgroundImage = new Image(background);
        backgroundImage.setFillParent(true);
        securityQuestions = new List<>(skin);
        securityQuestions.setItems(
            "What is name of your city",
            "what is your favorite color",
            "name of your best friend"
        );
        ScrollPane securityQuestionsPane = new ScrollPane(securityQuestions, skin);
        securityQuestionsPane.setFadeScrollBars(false);
        securityQuestionsPane.setScrollingDisabled(true, false);

        TextField securityAnswer = new TextField("", skin);
        securityAnswer.setMessageText("Enter answer");




        TextButton Submit = new TextButton("Submit", skin);
        TextButton Back = new TextButton("Back", skin);

        Submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String Question = securityQuestions.getSelected();
                String username = UsernameField.getText();
                String password = PasswordField.getText();
                String answer = securityAnswer.getText();
                String result = controller.validateSignup(username, password,answer);

                if (result.equals("OK")) {
                    System.out.println("Signup successful");
                    Dialog dialog = new Dialog("Signup successful", skin);
                    dialog.button("OK");
                    dialog.show(stage);
                    game.setScreen(mainMenuView);
                    AppData.users.add(new User(username,password,answer,Question));
                    // بعدا اوکی کن


                    //game.setScreen(new LoginView(game)); // برو به لاگین
                } else {
                    Dialog dialog = new Dialog("Error", skin);
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



        Table SignUpTable = new Table();
        SignUpTable.setFillParent(true);
        SignUpTable.center();
        SignUpTable.add(UsernameField).width(350).pad(10);
        SignUpTable.row();
        SignUpTable.add(PasswordField).width(350).pad(10);
        SignUpTable.row();
        SignUpTable.add(securityQuestionsPane).width(800).pad(10);
        SignUpTable.row();
        SignUpTable.add(securityAnswer).width(350).pad(10);
        SignUpTable.row();
        SignUpTable.add(Submit).colspan(2).pad(20);
        SignUpTable.row();
        SignUpTable.add(Back).colspan(2).pad(20);
        stage.addActor(backgroundImage);
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
