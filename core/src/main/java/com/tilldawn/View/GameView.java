package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.Model.Player;

public class GameView implements Screen {
    private final Main game;
    private Stage stage;
    private OrthographicCamera camera;
    private Player player;
    private Texture background;
    private SpriteBatch batch;

    public GameView(Main game, String selectedHero) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        stage = new Stage(new ScreenViewport(camera));

        player = new Player(selectedHero);
        player.setPosition(100, 100);
        player.setSize(64, 64); // سایز دلخواه

        stage.addActor(player);

        background = new Texture("background.png");
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        handleInput();
        updateCamera();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // رسم پس‌زمینه از وسط دوربین
        batch.draw(
            background,
            camera.position.x - camera.viewportWidth / 2,
            camera.position.y - camera.viewportHeight / 2,
            camera.viewportWidth,
            camera.viewportHeight
        );

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    private void handleInput() {
        boolean moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveBy(-2, 0);
            player.setState("run");
            player.setFacingRight(false);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveBy(2, 0);
            player.setState("run");
            player.setFacingRight(true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveBy(0, 2);
            player.setState("run");
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveBy(0, -2);
            player.setState("run");
            moving = true;
        }

        if (!moving) {
            player.setState("Idle");
        }
    }

    private void updateCamera() {
        camera.position.set(
            player.getX() + player.getWidth() / 2,
            player.getY() + player.getHeight() / 2,
            0
        );
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        batch.dispose();
    }
}
