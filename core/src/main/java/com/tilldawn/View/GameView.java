package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.EnemySpawner;
import com.tilldawn.Main;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.Tree;


import java.util.ArrayList;
import java.util.List;

public class GameView implements Screen {
    private final Main game;
    private Stage stage;
    private OrthographicCamera camera;
    private Player player;
    private Texture background;
    private SpriteBatch batch;
    private EnemySpawner enemySpawner;

    private List<Tree> trees = new ArrayList<>();
    private Animation<TextureRegion> treeAnimation;

    public GameView(Main game, String selectedHero) {
        this.game = game;
        enemySpawner = new EnemySpawner(trees);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        stage = new Stage(new ScreenViewport(camera));
        treeAnimation = Tree.loadAnimation();

        for (int i = 0; i < 10; i++) {
            float x = (float) Math.random() * 2000 - 1000;
            float y = (float) Math.random() * 2000 - 1000;
            trees.add(new Tree(x, y, treeAnimation));
        }

        player = new Player(selectedHero);
        player.setPosition(100, 100);

        stage.addActor(player);

        background = new Texture("background.png");
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        batch = new SpriteBatch();
    }
    private void spawnTreesAroundPlayer() {
        float px = player.getX();
        float py = player.getY();
        int maxTrees = 12;
        int attempts = 0;
        int maxAttempts = 100;

        while (trees.size() < maxTrees && attempts < maxAttempts) {
            attempts++;

            float angle = (float) (Math.random() * Math.PI * 2);
            float distance = 1500 + (float) Math.random() * 400;
            float x = px + (float) Math.cos(angle) * distance;
            float y = py + (float) Math.sin(angle) * distance;

            boolean tooClose = false;
            for (Tree t : trees) {
                float dx = t.getX() - x;
                float dy = t.getY() - y;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                if (dist < 500) { // حداقل فاصله بین درخت‌ها مثلاً 100 پیکسل
                    tooClose = true;
                    break;
                }
            }

            if (!tooClose) {
                trees.add(new Tree(x, y, treeAnimation));
            }
        }
    }

    private void removeFarTrees() {
        float px = player.getX();
        float py = player.getY();

        trees.removeIf(tree -> {
            float dx = tree.getX() - px;
            float dy = tree.getY() - py;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            return dist > 2500; // اگر از بازیکن خیلی دور شد، حذفش کن
        });
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

        enemySpawner.update(player.getCenter(), delta);

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
        spawnTreesAroundPlayer();
        removeFarTrees();
        // رسم دشمن‌ها
        enemySpawner.render(batch);

        // رسم درخت‌ها
        for (Tree tree : trees) {
            tree.render(batch); // ✅ حالا داخل batch.begin هست
        }

        batch.end();

        stage.act(delta);
        stage.draw();
    }


    private void handleInput() {
        boolean moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveBy(-4, 0);
            player.setState("run");
            player.setFacingRight(false);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveBy(4, 0);
            player.setState("run");
            player.setFacingRight(true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveBy(0, 4);
            player.setState("run");
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveBy(0, -4);
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
