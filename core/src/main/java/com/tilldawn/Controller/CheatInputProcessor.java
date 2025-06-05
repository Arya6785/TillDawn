package com.tilldawn.Controller;

import com.badlogic.gdx.InputProcessor;
import com.tilldawn.View.GameView;

public class CheatInputProcessor implements InputProcessor {
    private GameView view;

    public CheatInputProcessor(GameView view) {
        this.view = view;
    }

    @Override
    public boolean keyTyped(char character) {

        String t = view.typedText.trim();
        if (character == '\r' || character == '\n') {
            System.out.println("ENTER PRESSED! typedText: " + view.typedText);

            if (t.equalsIgnoreCase("time")) {
                view.gameTimer -= 60f;
                view.showTemporaryMessage("Time reduced");
            } else if (view.typedText.equalsIgnoreCase("level")) {
                view.player.xp = 0;
                view.player.level++;
                view.player.onLevelUp(view);
            } else if (view.typedText.equalsIgnoreCase("hp")) {
                if (view.player.health < view.player.maxHealth) {
                    view.player.health += 1;
                    view.showTemporaryMessage("Hp Added");
                } else {
                    view.showTemporaryMessage("You are at max health");
                }
            } else if (view.typedText.equalsIgnoreCase("boss")) {
                view.enemySpawner.spawnElder();
            }
            else if (t.equalsIgnoreCase("damage")) {
                view.player.gun.damage *=2;
                view.showTemporaryMessage("Damage increased");
            }

            view.typedText = "";
        } else if (character == '\b') {
            if (!view.typedText.isEmpty()) {
                view.typedText = view.typedText.substring(0, view.typedText.length() - 1);
            }
        } else {
            if (Character.isLetterOrDigit(character) || character == ' ') {
                view.typedText += character;
            }
        }
        return true;
    }

    // سایر متدها لازم نیست کاری انجام بدن
    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}

