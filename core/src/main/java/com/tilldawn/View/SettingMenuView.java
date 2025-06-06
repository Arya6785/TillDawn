package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Main;

public class SettingMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private Main game;
    private TextField moveUpField, moveDownField, moveLeftField, moveRightField, reloadField;
    private Preferences prefs;
    public SettingMenuView(Main game) {
        this.game = game;
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));
        Texture background = new Texture(Gdx.files.internal("background.png"));
        Image backgroundImage = new Image(background);
        backgroundImage.setFillParent(true);

        prefs = Gdx.app.getPreferences("TillDawnSettings");
        Slider volumeSlider = new Slider(0, 1, 0.01f, false, skin);
        volumeSlider.setValue(prefs.getFloat("musicVolume", 0.5f)); // مقدار پیش‌فرض

        Label volumeLabel = new Label("Music Volume:", skin);
        CheckBox autoReloadCheck = new CheckBox("  Auto Reload", skin);
        autoReloadCheck.setChecked(prefs.getBoolean("autoReload", true));  // پیش‌فرض: فعال

        SelectBox<String> musicSelect = new SelectBox<>(skin);
        musicSelect.setItems("music1.mp3", "music2.mp3", "music3.mp3");

// اگر قبلاً انتخاب کرده، لود کن
        String currentMusic = prefs.getString("music", "music1.mp3");
        musicSelect.setSelected(currentMusic);

        TextButton Back = new TextButton("Back", skin);
        CheckBox sfxCheck = new CheckBox("  SFX", skin);
        Back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuView(game));
            }
        });
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(backgroundImage);
        stage.addActor(table);

        // Create text fields with current settings or defaults
        moveUpField = new TextField(prefs.getString("moveUp", "W"), skin);
        moveDownField = new TextField(prefs.getString("moveDown", "S"), skin);
        moveLeftField = new TextField(prefs.getString("moveLeft", "A"), skin);
        moveRightField = new TextField(prefs.getString("moveRight", "D"), skin);
        reloadField = new TextField(prefs.getString("reload", "R"), skin);

        table.add(new Label("Move Up:", skin)).pad(5);
        table.add(moveUpField).width(100).pad(5);
        table.row();

        table.add(new Label("Move Down:", skin)).pad(5);
        table.add(moveDownField).width(100).pad(5);
        table.row();

        table.add(new Label("Move Left:", skin)).pad(5);
        table.add(moveLeftField).width(100).pad(5);
        table.row();

        table.add(new Label("Move Right:", skin)).pad(5);
        table.add(moveRightField).width(100).pad(5);
        table.row();

        table.add(new Label("Reload:", skin)).pad(5);
        table.add(reloadField).width(100).pad(5);
        table.row();
        table.add(musicSelect).width(100).pad(5);
        table.row();
        table.add(sfxCheck).width(100).pad(5);
        table.row();
        table.add(volumeLabel).pad(5);
        table.add(volumeSlider).width(150).pad(5);
        table.row();

        table.add(autoReloadCheck).colspan(2).pad(5);
        table.row();


        TextButton saveButton = new TextButton("Save Settings", skin);
        table.add(saveButton).colspan(2).padTop(20);
        table.row();
        table.add(Back);

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.putString("moveUp", moveUpField.getText().toUpperCase());
                prefs.putString("moveDown", moveDownField.getText().toUpperCase());
                prefs.putString("moveLeft", moveLeftField.getText().toUpperCase());
                prefs.putString("moveRight", moveRightField.getText().toUpperCase());
                prefs.putString("reload", reloadField.getText().toUpperCase());
                prefs.putString("music", musicSelect.getSelected());  // ذخیره موسیقی
                prefs.putFloat("musicVolume", volumeSlider.getValue());
                prefs.putBoolean("autoReload", autoReloadCheck.isChecked());


                prefs.flush();

                Dialog dialog = new Dialog("Saved", skin);
                dialog.text("Settings saved successfully!");
                dialog.button("OK");
                dialog.show(stage);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
