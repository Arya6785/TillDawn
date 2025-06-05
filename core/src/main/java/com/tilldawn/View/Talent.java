package com.tilldawn.View;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.Model.AppData;
import com.tilldawn.Model.User;

import java.util.HashMap;
import java.util.List;

public class Talent implements Screen {
    private Stage stage;
    private Skin skin;
    private Main game;
    private User user;
    private HashMap<String, User> registeredUsers;

    public Talent(Main game, List<User> Users) {
        this.game = game;
        user = AppData.CurrentUser;
        for (User user : Users) {
            this.registeredUsers.put(user.getUsername(), user);
        }        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        table.top().pad(20);
        stage.addActor(table);

        Label title = new Label("Talent / Hint Menu", skin, "title");
        table.add(title).colspan(2).padBottom(20);
        table.row();


        Label heroHints = new Label(
            "🛡️ Hero Guide:\n" +
                "• Shana: Speed 4, HP 4\n" +
                "• Diamond: Speed 1, HP 7\n" +
                "• Scarlet: Speed 5, HP 3\n" +
                "• Lilith: Speed 3, HP 5\n" +
                "• Dasher: Speed 10, HP 2", skin);
        table.add(heroHints).left().pad(10).colspan(2);
        table.row();


        Label controls = new Label("🎮 Current Controls:\n• Move: W/A/S/D\n• Shoot: SPACE\n• Ability: E", skin);
        table.add(controls).left().pad(10).colspan(2);
        table.row();

        Label cheats = new Label(
            "💡 Cheat Codes:\n" +
                "• minute  → Skip 1 minute of game time\n" +
                "• level → Instantly level up and open ability selection", skin);
        table.add(cheats).left().pad(10).colspan(2);
        table.row();

        Label abilities = new Label(
            "✨ Abilities:\n" +
                "• Vitality: Increase max HP by 1 unit\n" +
                "• Damager: Increase weapon damage by 25% for 10 seconds\n" +
                "• ProIncrease: Increase projectile count by 1\n" +
                "• AmoIncrease: Increase max ammo by 5\n" +
                "• Speedy: Double movement speed for 10 seconds", skin);
        table.add(abilities).left().pad(10).colspan(2);
        table.row();

        TextButton backButton = new TextButton("⬅ Back", skin);
        table.add(backButton).colspan(2).padTop(20);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuView(game));
            }
        });
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
