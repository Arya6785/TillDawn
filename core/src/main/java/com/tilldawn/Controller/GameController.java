package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tilldawn.Model.*;
import com.tilldawn.Model.Enums.Ability;
import com.tilldawn.View.GameView;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    public void handleInput(GameView view) {
        boolean moving = false;
        //checkCheat(view);
        Preferences prefs = Gdx.app.getPreferences("TillDawnSettings");
        int up = Input.Keys.valueOf(prefs.getString("moveUp", "W"));
        int down = Input.Keys.valueOf(prefs.getString("moveDown", "S"));
        int left = Input.Keys.valueOf(prefs.getString("moveLeft", "A"));
        int right = Input.Keys.valueOf(prefs.getString("moveRight", "D"));
        int reload = Input.Keys.valueOf(prefs.getString("reload", "R"));

        if (Gdx.input.isKeyPressed(left)) {
            view.player.moveBy(-view.player.speed, 0);
            view.player.setState("run");
            view.player.setFacingRight(false);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(right)) {
            view.player.moveBy(view.player.speed, 0);
            view.player.setState("run");
            view.player.setFacingRight(true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(up)) {
            view.player.moveBy(0, view.player.speed);
            view.player.setState("run");
            moving = true;
        }
        if (Gdx.input.isKeyPressed(down)) {
            view.player.moveBy(0, -view.player.speed);
            view.player.setState("run");
            moving = true;
        }
        if (Gdx.input.isKeyJustPressed(reload)) {
            view.player.gun.startReloading();
        }


        if (!moving) {
            view.player.setState("Idle");
        }
    }

    public void spawnTreesAroundPlayer(GameView view) {
        float px = view.player.getX();
        float py = view.player.getY();
        int maxTrees =12;
        int attempts = 0;
        int maxAttempts = 100;

        while (view.trees.size() < maxTrees && attempts < maxAttempts) {
            attempts++;

            float angle = (float) (Math.random() * Math.PI * 2);
            float distance = 1500 + (float) Math.random() * 400;
            float x = px + (float) Math.cos(angle) * distance;
            float y = py + (float) Math.sin(angle) * distance;

            boolean tooClose = false;
            for (Tree t : view.trees) {
                float dx = t.getX() - x;
                float dy = t.getY() - y;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                if (dist < 1000) { // حداقل فاصله بین درخت‌ها مثلاً 100 پیکسل
                    tooClose = true;
                    break;
                }
            }

            if (!tooClose) {
                view.trees.add(new Tree(x, y, view.treeAnimation));
            }
        }
    }
    public void removeFarTrees(GameView view) {
        float px = view.player.getX();
        float py = view.player.getY();

        view.trees.removeIf(tree -> {
            float dx = tree.getX() - px;
            float dy = tree.getY() - py;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            return dist > 2500; // اگر از بازیکن خیلی دور شد، حذفش کن
        });
    }
    public void checkCollision(GameView view) {
        for (Enemy enemy : view.enemies) {
            if (enemy.getBounds().overlaps(view.player.getBounds())) {
                damage(view,1); // فقط EnemyR باعث دمیج میشه
            }
        }
        for (Tree tree : view.trees) {
            if (tree.getBounds().overlaps(view.player.getBounds())) {
                damage(view,1);
            }
        }

    }
    public boolean checkCollision2(Bullet bullet , Enemy enemy) {
        if (enemy.getBounds().overlaps(bullet.getBounds())) {
            return true;
        }
        return false;
    }
    public boolean checkCollision3(HeartPickup pickup , Player player) {
        if (player.getBounds().overlaps(pickup.getBounds())) {
            return true;
        }
        return false;
    }
    public void damage(GameView view,int amount) {
        if (!view.player.invincible) {
            view.player.health -= amount;
            view.player.invincible = true;
            view.player.invincibilityTime = 0;
            if (view.player.health <= 0) {
                die(view);
            }
        }


    }
    public void die(GameView view) {
        // هندل مرگ بازیکن

        view.player.SecondsSurvived = view.gameTimeMinutes *60 - view.gameTimer;
        if (AppData.CurrentUser != null) {
            AppData.CurrentUser.Score = AppData.CurrentGameView.player.getScore();
            AppData.CurrentUser.KillCount = AppData.CurrentGameView.player.KillCount;
            AppData.CurrentUser.SecondsSurvived = AppData.CurrentGameView.player.SecondsSurvived;
        }
        view.game.setScreen(view.mainMenuView);
        AppData.showGameOverMessage(view.mainMenuView.skin,view.mainMenuView.stage);

        AppData.CurrentGameView = null;
        view.hide();
            return;
    }
    public void Shoot (GameView view) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)&& view.player.gun.currentAmmo > 0) {
            Vector2 center = view.player.gunSprite.getMuzzlePosition();
            Vector3 mouseWorld = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            view.camera.unproject(mouseWorld);
            Vector2 targetDir = new Vector2(mouseWorld.x, mouseWorld.y).sub(center).nor();

            int numBullets = view.player.gun.projectile;

            float spread = 10f; // زاویه پخش تیرها

            for (int i = 0; i < numBullets; i++) {
                float angleOffset = (i - (numBullets - 1) / 2f) * spread;
                Vector2 direction = new Vector2(targetDir).rotateDeg(angleOffset);
                Bullet bullet = new Bullet(center, direction, view.player.gun.damage);
                view.player.bullets.add(bullet);

            }
            view.player.gun.currentAmmo-=1;
        }

    }
    public void removeBullet(GameView view,float delta) {
        // آپدیت گلوله‌ها
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet bullet : view.player.bullets) {
            if (!bullet.isActive()) continue;
            bullet.update(delta);

            for (Enemy enemy : view.enemies) {
                if (checkCollision2(bullet,enemy)) {
                    enemy.hp -= bullet.damage;
                    bullet.deactivate();

                    if (enemy.hp <= 0) {
                        view.enemies.remove(enemy);
                        view.pickups.add(new HeartPickup(enemy.getPosition().cpy()));
                        view.player.KillCount += 1;
                        view.explosions.add(new Explosion(enemy.getPosition().cpy()));
                        break;
                    }

                }
            }

            // مثلاً اگر از محدوده دور شد حذفش کنیم
            if (bullet.position.dst(view.player.getCenter()) > 1500) {
                bullet.deactivate();
            }

            if (!bullet.isActive()) {
                toRemove.add(bullet);
            }
        }

// حذف گلوله‌های غیر فعال
        view.player.bullets.removeAll(toRemove);

// رسم گلوله‌ها
        for (Bullet bullet : view.player.bullets) {
            bullet.draw(view.batch);
        }

    }
    public void PickUp(GameView view) {
        for (HeartPickup pickup : view.pickups) {
            pickup.render(view.batch);
        }
        Vector2 playerCenter = view.player.getCenter();
        for (int i = 0; i < view.pickups.size(); i++) {
            HeartPickup pickup = view.pickups.get(i);
            if (checkCollision3(pickup, view.player)) {
                view.pickups.remove(i);
                i--; // چون از لیست حذف شد
                view.player.addXP(3,view);
            }
        }


    }
    public void checkCheat(GameView view) {
        for (int i = 0; i < 256; i++) {
            if (Gdx.input.isKeyJustPressed(i)) {
                char c = (char) i;
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') ) {
                    view.typedText += c;
                }


                // اگر Enter زده شد
                if (i == Input.Keys.ENTER) {
                    System.out.println("ENTER PRESSED! typedText: " + view.typedText);

                    String command = view.typedText.toLowerCase().trim();

                    if (command.equalsIgnoreCase("time")) {
                        view.gameTimer -= 60f; // کم کردن یک دقیقه
                        view.showTemporaryMessage("Time reduced");

                        System.out.println("1 minute removed! Remaining: " + view.gameTimer);
                    }
                    if (view.typedText.equalsIgnoreCase("level")) {
                        if (view.player.health < view.player.maxHealth) {
                            view.player.health +=1;
                            view.showTemporaryMessage("Hp Added");
                        }
                        else {
                            view.showTemporaryMessage("You are at max health");
                        }
                    }
                    if (view.typedText.equalsIgnoreCase("level")) {
                        view.player.xp =0;
                        view.player.level++;
                        view.player.onLevelUp(view);
                    }
                    if (view.typedText.equalsIgnoreCase("boss")) {
                        view.enemySpawner.spawnElder();
                    }

                    view.typedText = ""; // پاک کردن ورودی
                }

                // اگر Backspace زده شد
                if (i == Input.Keys.BACKSPACE && view.typedText.length() > 0) {
                    view.typedText = view.typedText.substring(0, view.typedText.length() - 1);
                }
            }
        }

    }
    public void updateEnemyProjectiles(GameView view, float delta) {
        List<EyebatProjectile> toRemove = new ArrayList<>();

        for (EyebatProjectile proj : view.enemyProjectiles) {
            proj.update(delta);
            if (proj.getBounds().overlaps(view.player.getBounds())) {
                damage(view, proj.damage);
                proj.deactivate();
            }
            if (!proj.active) toRemove.add(proj);
        }
        view.enemyProjectiles.removeAll(toRemove);
        for (EyebatProjectile proj : view.enemyProjectiles) {
            proj.render(view.batch);
        }
    }
    public void grantAbility(GameView view,Ability ability) {
        switch(ability) {
            case VITALITY:
                view.player.maxHealth += 1;
                view.player.health += 1;
                view.showTemporaryMessage("Your Max Hp was Increased");
                break;
            case DAMAGER:
                view.player.gun.damage*=1.25f;
                view.showTemporaryMessage("Your guns damage was Increased");
                break;
            case PROCLEASE:
                view.player.gun.projectile += 1;
                view.showTemporaryMessage("Your guns Projectiles was Increased");
                break;
            case AMOCREASE:
                view.player.gun.maxAmmo+=5;
                view.showTemporaryMessage("Your Max Ammo was Increased");
                break;
            case SPEEDY:
                view.SpeedBoost();
                view.showTemporaryMessage("Your Speed was Increased");
                break;
        }
    }



}
