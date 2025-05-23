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

public class SignUpMenuView implements Screen {
    private final SignUpMenuController controller = new SignUpMenuController();
    private final MainMenuView mainMenuView;
    private final Main game;
    private Stage stage;
    private Skin skin;

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
        TextField UsernameField = new TextField("", skin);
        Texture background = new Texture(Gdx.files.internal("background.png"));
        Image backgroundImage = new Image(background);
        backgroundImage.setFillParent(true);

        TextButton Submit = new TextButton("Submit", skin);

        Submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = UsernameField.getText();
                String password = PasswordField.getText();

                String result = controller.validateSignup(username, password);

                if (result.equals("OK")) {
                    System.out.println("Signup successful");
                    Dialog dialog = new Dialog("Signup successful", skin);
                    dialog.button("OK");
                    dialog.show(stage);
                    game.setScreen(mainMenuView);
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
