package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Model.*;
import com.tilldawn.View.GameView;

import java.util.List;

public class EnemySpawner {

    public final List<Enemy> enemies;
    private final float worldWidth = 3000;
    private final float worldHeight = 3000;
    private List<EyebatProjectile> enemyProjectiles;

    private final Animation<TextureRegion> enemyRAnimation;
    private final Animation<TextureRegion> eyebatAnimation;
    private final Animation<TextureRegion> ElderAnimation;

    private final TextureRegion eyebatProjectileTexture;

    private float timeSinceStart = 0f;
    private float enemyRSpawnTimer = 3f;
    private float eyebatSpawnTimer = 0f;
    private final float eyebatSpawnDelay ;
    private final float ElderSpawnDelay ;
    private boolean elderSpawned = false;

    // 1/4 کل زمان بازی فرض شده

    public EnemySpawner(List<Tree> trees, List<Enemy> enemies,GameView view) {
        this.enemies = enemies;
        this.enemyRAnimation = EnemyR.loadAnimation();
        this.eyebatAnimation = Eyebat.loadAnimation();
        this.ElderAnimation = EnemyElder.loadAnimation();
        this.eyebatSpawnDelay = view.gameTimeMinutes * 15f;
        this.ElderSpawnDelay = view.gameTimeMinutes * 30f;


        // بارگذاری تکسچر تیر eyebat
        eyebatProjectileTexture = new TextureRegion(new Texture("EyeMonsterProjecitle.png"));
    }

    public void update(Vector2 playerPos, float delta, GameView view) {
        timeSinceStart += delta;
        enemyRSpawnTimer+=delta;

        // اسپاون دشمن معمولی




        // آپدیت دشمنان
        for (Enemy enemy : enemies) {
            if (enemy instanceof Eyebat) {
                ((Eyebat) enemy).update(playerPos, delta, view);

            }
            else if (enemy instanceof EnemyElder){
                ((EnemyElder)enemy).update(playerPos, delta, view);
            }
            else {
                enemy.update(playerPos, delta);

            }
        }

        enemies.removeIf(Enemy::isDead);
        if (enemyRSpawnTimer >=3f){
            enemyRSpawnTimer = 0f;
            float n = timeSinceStart/10;
            int n2 = MathUtils.floor(n);
            for (int i = 0; i < n2; i++) {
                spawnEnemyR();
            }

        }
        // اسپاون Eyebat بعد از یک چهارم اول بازی
        if (timeSinceStart > eyebatSpawnDelay) {
            eyebatSpawnTimer += delta;
            if (eyebatSpawnTimer >= 10f) {
                eyebatSpawnTimer = 0f;
                float n = (4*timeSinceStart - view.gameTimeMinutes*60f + 30)/40;
                int n2 = MathUtils.floor(n);
                for (int i = 0; i < n2; i++) {
                    spawnEyebat();
                }
            }
        }
        if (timeSinceStart > ElderSpawnDelay) {
            if (!elderSpawned) {
                spawnElder();
                elderSpawned = true;
            }

        }
    }

    public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
    }

    private void spawnEnemyR() {
        Vector2 pos = getRandomEdgePosition();
        enemies.add(new EnemyR(pos.x, pos.y, enemyRAnimation));
    }

    private void spawnEyebat() {
        Vector2 pos = getRandomEdgePosition();
        enemies.add(new Eyebat(pos.x, pos.y, eyebatAnimation, eyebatProjectileTexture));
    }
    public void spawnElder() {
        Vector2 pos = getRandomEdgePosition();
        enemies.add(new EnemyElder(pos.x, pos.y, ElderAnimation));

    }

    private Vector2 getRandomEdgePosition() {
        int side = MathUtils.random(3);
        float x, y;

        switch (side) {
            case 0:
                x = 0;
                y = MathUtils.random(worldHeight);
                break;
            case 1:
                x = worldWidth;
                y = MathUtils.random(worldHeight);
                break;
            case 2:
                x = MathUtils.random(worldWidth);
                y = 0;
                break;
            default:
                x = MathUtils.random(worldWidth);
                y = worldHeight;
                break;
        }

        return new Vector2(x, y);
    }
}
