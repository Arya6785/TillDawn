package com.tilldawn.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.CheatInputProcessor;
import com.tilldawn.Controller.EnemySpawner;
import com.tilldawn.Controller.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameView implements Screen {

    public final GameController controller = new GameController();
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
    public float gameTimer;
    public List<EyebatProjectile> enemyProjectiles = new ArrayList<>();
    private String temporaryMessage = null;
    private float messageDisplayTime = 0f;
    private static final float MESSAGE_DURATION = 6f;
    private static final float SpeedBoost = 10f;
    private float SpeedBoostTime = 0f;
    private boolean speedBoost = false;
    private boolean speedBoostdone = false;
    public int gameTimeMinutes;
    public boolean paused = false;
    private Window pauseMenuWindow;
    private Music backgroundMusic;
    public String typedText = "";
    private Texture glowTexture;
    private float glowWidth = 105f;   // اندازه مناسب نسبت به پلیر، به دلخواه تغییر بده
    private float glowHeight = 105f;
    public ArrayList<Explosion> explosions = new ArrayList<>();




    public GameView(Main game, String selectedHero,Gun selectedGun,MainMenuView mainMenuView,int gameTimeMinutes,User user) {
        this.game = game;
        this.mainMenuView = mainMenuView;
        this.gameTimer = gameTimeMinutes * 60f;
        this.gameTimeMinutes = gameTimeMinutes;
        font = new BitmapFont();
        font.getData().setScale(2);
        enemySpawner = new EnemySpawner(trees,enemies , this);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        stage = new Stage(new ScreenViewport(camera));



        glowTexture = new Texture("glow.png");
        glowTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        treeAnimation = Tree.loadAnimation();

        for (int i = 0; i < 10; i++) {
            float x = (float) Math.random() * 2000 - 1000;
            float y = (float) Math.random() * 2000 - 1000;
            trees.add(new Tree(x, y, treeAnimation));
        }

        player = new Player(selectedHero,this,selectedGun);
        user.player = player;
        player.setPosition(100, 100);

        stage.addActor(player);

        background = new Texture("background.png");
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        batch = new SpriteBatch();
    }




    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new CheatInputProcessor(this)); // اول چیت‌کد
        multiplexer.addProcessor(stage); // بعد استیج برای دکمه‌ها و UI
        Gdx.input.setInputProcessor(multiplexer);
        Pixmap pixmap = new Pixmap(Gdx.files.internal("T_Cursor.png")); // بارگذاری تصویر
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 0, 0));   // تنظیم تصویر به عنوان موس
        pixmap.dispose(); // آزادسازی منابع
        Preferences prefs = Gdx.app.getPreferences("TillDawnSettings");
        String selectedMusic = prefs.getString("music", "music1.mp3");
        float musicVolume = prefs.getFloat("musicVolume", 0.5f);

        if (backgroundMusic != null) backgroundMusic.dispose();  // اطمینان از خالی بودن قبلی

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal( selectedMusic));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(musicVolume);
        backgroundMusic.play();
    }
    public void showTemporaryMessage(String message) {
        temporaryMessage = message;
        messageDisplayTime = 0f;
    }
    public void SpeedBoost (){
        speedBoost = true;
        SpeedBoostTime = 0f;
    }
    @Override
    public void render(float delta) {


        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            game.setScreen(new PauseMenuView(game,this,mainMenuView));
            return;
        }


        controller.handleInput(this);
        updateCamera();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        enemySpawner.update(player.getCenter(), delta,this);
        if (temporaryMessage != null) {
            messageDisplayTime += delta;
            if (messageDisplayTime >= MESSAGE_DURATION) {
                temporaryMessage = null;
            }
        }
        if (speedBoost) {
            if (!speedBoostdone){
                player.speed*=2;
                speedBoostdone = true;
            }
            SpeedBoostTime += delta;
            if (SpeedBoostTime >= SpeedBoost) {
                speedBoost = false;
                speedBoostdone = false;
                player.speed/=2;
            }
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        gameTimer -= delta;
        if (gameTimer <= 0) {
            // زمان تمام شده، برگشت به منوی اصلی
            player.SecondsSurvived = gameTimeMinutes * 60f;
            if (AppData.CurrentUser != null) {
                if (AppData.CurrentUser.Score > AppData.CurrentGameView.player.getScore()){
                    AppData.CurrentUser.Score = AppData.CurrentGameView.player.getScore();
                    AppData.CurrentUser.KillCount = AppData.CurrentGameView.player.KillCount;
                    AppData.CurrentUser.SecondsSurvived = AppData.CurrentGameView.player.SecondsSurvived;
                }

            }
            game.setScreen(mainMenuView);
            AppData.showVictoryMessage(mainMenuView.skin, mainMenuView.stage);
            if (AppData.CurrentUser != null) {
                AppData.CurrentUser.KillCount =AppData.CurrentGameView.player.KillCount;
                AppData.CurrentUser.SecondsSurvived = AppData.CurrentGameView.player.SecondsSurvived;
            }
            AppData.CurrentGameView = null;
            hide();
            return;
        }

        //Auto Reload
        Preferences prefs = Gdx.app.getPreferences("TillDawnSettings");
        boolean autoReload = prefs.getBoolean("autoReload", true);

        if (player.gun.currentAmmo ==0 && autoReload){
            player.gun.startReloading();
        }



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
        controller.updateEnemyProjectiles(this, delta);

        controller.PickUp(this);
        player.gun.updateReload(delta);
        // آپدیت و رسم گلوله‌های دشمن
        for (int i = 0; i < enemyProjectiles.size(); i++) {
            EyebatProjectile proj = enemyProjectiles.get(i);
            proj.update(delta);
            proj.render(batch);

            // اگر گلوله غیرفعال شده بود حذفش کن
            if (!proj.active) {
                enemyProjectiles.remove(i);
                i--;
            }
        }


        // رسم دشمن‌ها
        enemySpawner.render(batch);

        // رسم درخت‌ها
        for (Tree tree : trees) {
            tree.render(batch); // ✅ حالا داخل batch.begin هست
        }
        batch.draw(
            glowTexture,
            player.getX() + player.getWidth() / 2f - glowWidth / 2f,
            player.getY() + player.getHeight() / 2f - glowHeight / 2f,
            glowWidth,
            glowHeight
        );

        font.draw(batch, "HP: " + player.health, camera.position.x - 1200, camera.position.y + 300);
        int minutes = (int)(gameTimer / 60);
        int seconds = (int)(gameTimer % 60);
        font.draw(batch, String.format("Time Left: %02d:%02d", minutes, seconds),
            camera.position.x - 1200, camera.position.y + 360);
        font.draw(batch , String.format("Kill Count: %d",player.KillCount),camera.position.x - 1200,camera.position.y+420);
        font.draw(batch , String.format("Level: %d",player.level),camera.position.x - 1200,camera.position.y+480);
        font.draw(batch , String.format("Xp: %d",player.xp),camera.position.x - 1200,camera.position.y+540);
        font.draw(batch , String.format("Ammo: %d",player.gun.currentAmmo),camera.position.x - 1200,camera.position.y+600);

        if (temporaryMessage != null) {
            font.draw(batch, temporaryMessage, camera.position.x +700, camera.position.y + 200);
        }
        for (int i = 0; i < explosions.size(); i++) {
            Explosion ex = explosions.get(i);
            ex.update(delta);
            ex.render(batch);
            if (ex.isFinished()) {
                explosions.remove(i);
                i--;
            }
        }



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
    @Override public void hide() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        batch.dispose();
        if (backgroundMusic != null) backgroundMusic.dispose();

    }
}
