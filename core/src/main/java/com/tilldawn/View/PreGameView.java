package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.Model.AppData;
import com.tilldawn.Model.Gun;
import com.tilldawn.Model.User;

import javax.swing.event.ChangeListener;

public class PreGameView implements Screen {
    private final Main game;
    private final MainMenuView mainMenuView;
    private  Skin skin;
    private Stage stage;
    public List<String> Heroes;
    public List<String> Guns;
    public List<String> GameTimes;



    public PreGameView(Main game, MainMenuView mainMenuView) {
        this.game = game;
        this.mainMenuView = mainMenuView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));
        Texture background = new Texture(Gdx.files.internal("background.png"));
        Image backgroundImage = new Image(background);

        TextButton Play = new TextButton("Play", skin);
        TextButton Skip = new TextButton("Skip", skin);
        Skip.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event ,float x, float y) {
                String selectedGunName = Guns.getSelected();
                Gun selectedGun;

                switch (selectedGunName) {
                    case "Revolver":
                        selectedGun = new Gun("Revolver", 20, 1, 1, 6);
                        break;
                    case "Shotgun":
                        selectedGun = new Gun("Shotgun", 10, 4, 1, 2);
                        break;
                    case "SMG Dual":
                        selectedGun = new Gun("SMG Dual", 8, 1, 2, 24);
                        break;
                    default:
                        selectedGun = new Gun("Revolver", 20, 1, 1, 6); // حالت پیش‌فرض
                }
                int selectedTime = Integer.parseInt(GameTimes.getSelected());



                game.setScreen(new GameView(game, Heroes.getSelected(), selectedGun, mainMenuView,selectedTime,new User("","","","")));
            }
        });
        Play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event ,float x, float y) {
                if (AppData.CurrentUser == null) {
                    AppData.showMessage("You need to login first!",skin,stage);
                    return;
                }
                String selectedGunName = Guns.getSelected();
                Gun selectedGun;

                switch (selectedGunName) {
                    case "Revolver":
                        selectedGun = new Gun("Revolver", 20, 1, 1, 6);
                        break;
                    case "Shotgun":
                        selectedGun = new Gun("Shotgun", 10, 4, 1, 2);
                        break;
                    case "SMG Dual":
                        selectedGun = new Gun("SMG Dual", 8, 1, 2, 24);
                        break;
                    default:
                        selectedGun = new Gun("Revolver", 20, 1, 1, 6); // حالت پیش‌فرض
                }
                int selectedTime = Integer.parseInt(GameTimes.getSelected());



                game.setScreen(new GameView(game, Heroes.getSelected(), selectedGun, mainMenuView,selectedTime,AppData.CurrentUser));
            }
        });
        Heroes = new List<>(skin);
        Heroes.setItems(
            "Shana",
            "Dasher",
            "Diamond",
             "Lilith",
            "Scarlet"
        );
        Guns = new List<>(skin);
        Guns.setItems(
            "Revolver",
            "Shotgun",
            "SMG Dual"
        );
        GameTimes = new List<>(skin);
        GameTimes.setItems("2", "5", "10", "20");

        ScrollPane TimeChoose = new ScrollPane(GameTimes, skin);
        TimeChoose.setFadeScrollBars(false);
        TimeChoose.setScrollingDisabled(true, false);


        ScrollPane HeroChoose = new ScrollPane(Heroes, skin);
        ScrollPane GunChoose = new ScrollPane(Guns, skin);
        HeroChoose.setFadeScrollBars(false);
        HeroChoose.setScrollingDisabled(true, false);
        Table PreGameTable = new Table();
        PreGameTable.add(HeroChoose);
        PreGameTable.row();
        PreGameTable.add(GunChoose);
        PreGameTable.row();
        PreGameTable.add(TimeChoose);
        PreGameTable.add(Play);
        PreGameTable.row();
        PreGameTable.add(Skip);
        PreGameTable.setFillParent(true);
        stage.addActor(backgroundImage);
        stage.addActor(PreGameTable);

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
