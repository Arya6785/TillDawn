package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;

import javax.swing.event.ChangeListener;

public class PreGameView implements Screen {
    private final Main game;
    private final MainMenuView mainMenuView;
    private  Skin skin;
    private Stage stage;
    public List<String> Heroes;


    public PreGameView(Main game, MainMenuView mainMenuView) {
        this.game = game;
        this.mainMenuView = mainMenuView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));

        TextButton Play = new TextButton("Play", skin);

        Play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event ,float x, float y){
                game.setScreen(new GameView(game,Heroes.getSelected()));
            }
        });
        Heroes = new List<>(skin);
        Heroes.setItems(
            "Shana",
            "Dasher",
            "Diamond",
             "Lilith",
            "Scarlet"
        );

        ScrollPane HeroChoose = new ScrollPane(Heroes, skin);
        HeroChoose.setFadeScrollBars(false);
        HeroChoose.setScrollingDisabled(true, false);
        Table PreGameTable = new Table();
        PreGameTable.add(HeroChoose);
        PreGameTable.row();
        PreGameTable.add(Play);
        PreGameTable.setFillParent(true);
        stage.addActor(PreGameTable);

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
