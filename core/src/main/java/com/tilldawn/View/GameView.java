package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.EnemySpawner;
import com.tilldawn.Controller.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.*;


import java.util.ArrayList;
import java.util.List;

public class GameView implements Screen {
    private final GameController controller = new GameController();
    public final Main game;
    public Stage stage;
    public OrthographicCamera camera;
    public Player player;
    public Texture background;
    public SpriteBatch batch;
    public EnemySpawner enemySpawner;
    public MainMenuView mainMenuView;
    public List<Tree> trees = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    public Animation<TextureRegion> treeAnimation;
    public BitmapFont font;
    public List<HeartPickup> pickups = new ArrayList<>();
    public GameView(Main game, String selectedHero,Gun selectedGun,MainMenuView mainMenuView) {
        this.game = game;
        this.mainMenuView = mainMenuView;
        font = new BitmapFont();
        font.getData().setScale(2);
        enemySpawner = new EnemySpawner(trees,enemies);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        stage = new Stage(new ScreenViewport(camera));
        treeAnimation = Tree.loadAnimation();

        for (int i = 0; i < 10; i++) {
            float x = (float) Math.random() * 2000 - 1000;
            float y = (float) Math.random() * 2000 - 1000;
            trees.add(new Tree(x, y, treeAnimation));
        }

        player = new Player(selectedHero,this,selectedGun);
        player.setPosition(100, 100);

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
        controller.handleInput(this);
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
        controller.spawnTreesAroundPlayer(this);
        controller.removeFarTrees(this);
        controller.checkCollision(this);
        player.update(delta);
        Vector2 playerCenter = player.getCenter();
        Vector3 mouseWorld = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouseWorld);// فرض بر اینه که همچین متدی داری
        player.gunSprite.updatePositionAndRotation(playerCenter, new Vector2(mouseWorld.x, mouseWorld.y));
        player.gunSprite.draw(batch);
        controller.Shoot(this);
        controller.removeBullet(this,delta);
        controller.PickUp(this);
        // رسم دشمن‌ها
        enemySpawner.render(batch);

        // رسم درخت‌ها
        for (Tree tree : trees) {
            tree.render(batch); // ✅ حالا داخل batch.begin هست
        }
        font.draw(batch, "HP: " + player.health, camera.position.x - 1000, camera.position.y + 500);




        batch.end();

        stage.act(delta);
        stage.draw();
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
