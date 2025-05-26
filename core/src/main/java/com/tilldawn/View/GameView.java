package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.Model.Player;

public class GameView implements Screen {
    private final Main game;
    private Stage stage;
    private OrthographicCamera camera;
    private Player player;

    public GameView(Main game, String selectedHero) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600); // سایز صفحه
        stage = new Stage(new ScreenViewport(camera));

        player = new Player(selectedHero); // مثلا "Diamond"
        player.setPosition(100, 100); // جای اولیه
        stage.addActor(player);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        updateCamera();
        handleInput();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    private void updateCamera() {
        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.update();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveBy(-2, 0);
            player.setState("run");
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveBy(2, 0);
            player.setState("run");
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveBy(0, 2);
            player.setState("run");
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveBy(0, -2);
            player.setState("run");
        } else {
            player.setState("Idle");
        }
    }

    // باقی توابع Screen

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }

}
