package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sun.source.util.Trees;
import com.tilldawn.Model.Enemy;
import com.tilldawn.Model.EnemyR;
import com.tilldawn.Model.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemySpawner {
    private final List<Enemy> enemies = new ArrayList<>();
    private final float worldWidth = 3000;
    private final float worldHeight = 3000;
    private final Animation<TextureRegion> enemyRAnimation;
    private float spawnTimer = 0f;
    private float spawnRate = 1f;
    private float timeSinceStart = 0f;
    private List<Tree> trees;


    public EnemySpawner(List<Tree> trees) {
        this.enemyRAnimation = EnemyR.loadAnimation();
        this.trees = trees;
    }

    public void update(Vector2 playerPos, float delta) {
        timeSinceStart += delta;
        spawnTimer += delta;

        if (spawnTimer >= spawnRate) {
            spawnTimer = 0f;
            spawnEnemy();
        }

        // کاهش نرخ اسپاون با گذر زمان
        if (timeSinceStart > 5 && spawnRate > 1f) {
            spawnRate -= delta * 0.01f;
        }

        for (Enemy enemy : enemies) {
            enemy.update(playerPos, delta);
        }

        // پاک کردن دشمن‌های مرده
        enemies.removeIf(Enemy::isDead);
    }

    public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
    }

    private void spawnEnemy() {
        Vector2 pos = getRandomEdgePosition();
        enemies.add(new EnemyR(pos.x, pos.y, enemyRAnimation));
    }

    private Vector2 getRandomEdgePosition() {
        int side = MathUtils.random(3);
        float x, y;

        switch (side) {
            case 0: x = 0; y = MathUtils.random(worldHeight); break;        // چپ
            case 1: x = worldWidth; y = MathUtils.random(worldHeight); break; // راست
            case 2: x = MathUtils.random(worldWidth); y = 0; break;         // پایین
            default: x = MathUtils.random(worldWidth); y = worldHeight; break; // بالا
        }

        return new Vector2(x, y);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
