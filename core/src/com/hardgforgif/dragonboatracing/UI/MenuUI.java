package com.hardgforgif.dragonboatracing.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.hardgforgif.dragonboatracing.GameData;
import com.hardgforgif.dragonboatracing.SaveGame.SaveGameData;
import com.hardgforgif.dragonboatracing.core.Player;

public class MenuUI extends UI {

    // Sets the dimensions for all the UI components
    private static final int LOGO_WIDTH = 400;
    private static final int LOGO_HEIGHT = 200;
    private static final int LOGO_Y = 420;

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;
    private static final int PLAY_BUTTON_Y = 150;

    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;
    private static final int EXIT_BUTTON_Y = 20;

    private static final int VOLUME_BUTTON_WIDTH = 75;
    private static final int VOLUME_BUTTON_HEIGHT = 75;
    private static final int VOLUME_BUTTON_Y = 645;

    private static final int VOLUME_LABEL_WIDTH = 250;
    private static final int VOLUME_LABEL_HEIGHT = 75;
    private static final int VOLUME_LABEL_Y = 645;

    private static final int LOAD_BUTTON_WIDTH = 250;
    private static final int LOAD_BUTTON_HEIGHT = 120;
    private static final int LOAD_BUTTON_Y = 280;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture playEasyButtonActive;
    Texture playEasyButtonInactive;
    Texture playMediumButtonActive;
    Texture playMediumButtonInactive;
    Texture playHardButtonActive;
    Texture playHardButtonInactive;
    Texture volumeUpActive;
    Texture volumeUpInactive;
    Texture volumeDownActive;
    Texture volumeDownInactive;
    Texture logo;
    Texture volumeLabel;

    Texture loadGameInactive;
    Texture loadGameActive;

    ScrollingBackground scrollingBackground = new ScrollingBackground();

    public MenuUI() {
        scrollingBackground.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        scrollingBackground.setSpeedFixed(true);
        scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);
        // Add images for the play buttons
        playEasyButtonActive = new Texture("PlayEasySelected.png");// "PlayEasySelected.png"
        playEasyButtonInactive = new Texture("PlayEasyUnSelected.png");
        playMediumButtonActive = new Texture("PlayMediumSelected.png");// "PlayMediumSelected.png"
        playMediumButtonInactive = new Texture("PlayMediumUnselected.png");
        playHardButtonActive = new Texture("PlayHardSelected.png");// "PlayHardSelected.png"
        playHardButtonInactive = new Texture("PlayHardUnselected.png");
        // Add logo image for the title
        logo = new Texture("Title.png");
        // Add images for the Exit button
        exitButtonActive = new Texture("ExitSelected.png");
        exitButtonInactive = new Texture("ExitUnselected.png");
        // Add images for volume changer
        volumeUpActive = new Texture("VolumePlusSelected.png");
        volumeUpInactive = new Texture("VolumePlus.png");
        volumeDownActive = new Texture("VolumeMinusSelected.png");
        volumeDownInactive = new Texture("VolumeMinus.png");
        volumeLabel = new Texture("Volume.png");

        loadGameInactive = new Texture("LoadUnselected.png");
        loadGameActive = new Texture("LoadSelected.png");

        GameData.spawnedObstacles = false;
    }

    @Override
    public void drawUI(Batch batch, Vector2 mousePos, float screenWidth, float delta) {
        batch.begin();
        scrollingBackground.updateAndRender(delta, batch);
        batch.draw(logo, screenWidth / 2 - LOGO_WIDTH / 2, LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);

        // Draw easy difficulty button
        float x = (float) (screenWidth / 2 - (PLAY_BUTTON_WIDTH * 1.5));
        if (mousePos.x < x + PLAY_BUTTON_WIDTH && mousePos.x > x &&
        // cur pos < top_height
                mousePos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && mousePos.y > PLAY_BUTTON_Y) {
            batch.draw(playEasyButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            batch.draw(playEasyButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        // Draw medium difficulty play button
        x = (float) (screenWidth / 2 - (PLAY_BUTTON_WIDTH * 0.5));
        if (mousePos.x < x + PLAY_BUTTON_WIDTH && mousePos.x > x &&
        // cur pos < top_height
                mousePos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && mousePos.y > PLAY_BUTTON_Y) {
            batch.draw(playMediumButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            batch.draw(playMediumButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        // Draw Hard difficulty play button
        x = (float) (screenWidth / 2 + (PLAY_BUTTON_WIDTH * 0.5));
        if (mousePos.x < x + PLAY_BUTTON_WIDTH && mousePos.x > x &&
        // cur pos < top_height
                mousePos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && mousePos.y > PLAY_BUTTON_Y) {
            batch.draw(playHardButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            batch.draw(playHardButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        // Draw Exit game Buttons
        x = screenWidth / 2 - EXIT_BUTTON_WIDTH / 2;
        if (mousePos.x < x + EXIT_BUTTON_WIDTH && mousePos.x > x && mousePos.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
                && mousePos.y > EXIT_BUTTON_Y) {
            batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        } else {
            batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
        // Draw Volume up Buttons
        x = screenWidth - VOLUME_BUTTON_WIDTH * 2;
        if (mousePos.x < x + VOLUME_BUTTON_WIDTH && mousePos.x > x
                && mousePos.y < VOLUME_BUTTON_Y + VOLUME_BUTTON_HEIGHT && mousePos.y > EXIT_BUTTON_Y) {
            batch.draw(volumeUpActive, x, VOLUME_BUTTON_Y, VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
        } else {
            batch.draw(volumeUpInactive, x, VOLUME_BUTTON_Y, VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
        }

        // Draw Volume down Buttons
        x = screenWidth - VOLUME_BUTTON_WIDTH;
        if (mousePos.x < x + VOLUME_BUTTON_WIDTH && mousePos.x > x
                && mousePos.y < VOLUME_BUTTON_Y + VOLUME_BUTTON_HEIGHT && mousePos.y > VOLUME_BUTTON_Y) {
            batch.draw(volumeDownActive, x, VOLUME_BUTTON_Y, VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
        } else {
            batch.draw(volumeDownInactive, x, VOLUME_BUTTON_Y, VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
        }

        // Draw Volume label
        x = screenWidth - VOLUME_BUTTON_WIDTH * 2 - VOLUME_LABEL_WIDTH;
        batch.draw(volumeLabel, x, VOLUME_LABEL_Y, VOLUME_LABEL_WIDTH, VOLUME_LABEL_HEIGHT);

        // Draw load game button
        x = screenWidth / 2 - LOAD_BUTTON_WIDTH / 2;
        if (mousePos.x < x + LOAD_BUTTON_WIDTH && mousePos.x > x &&
        // cur pos < top_height
                mousePos.y < LOAD_BUTTON_Y + LOAD_BUTTON_HEIGHT && mousePos.y > LOAD_BUTTON_Y) {
            batch.draw(loadGameActive, x, LOAD_BUTTON_Y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT);
        } else {
            batch.draw(loadGameInactive, x, LOAD_BUTTON_Y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT);
        }

        batch.end();

        playMusic();
    }

    @Override
    public void drawPlayerUI(Batch batch, Player playerBoat) {

    }

    @Override
    public void getInput(float screenWidth, Vector2 clickPos) {
        // If the play easy button is clicked
        float x = (float) (screenWidth / 2 - (PLAY_BUTTON_WIDTH * 1.5));
        if (clickPos.x < x + PLAY_BUTTON_WIDTH && clickPos.x > x &&
        // cur pos < top_height
                clickPos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && clickPos.y > PLAY_BUTTON_Y) {
            // Switch to the choosing state
            GameData.mainMenuState = false;
            GameData.choosingBoatState = true;
            GameData.currentUI = new ChoosingUI();
            // Set difficulty for level ad AI
            GameData.difficulty = "EASY";
            GameData.level = new float[] { 0.87f, 0.89f, 0.92f };
            GameData.isFromSave = false;
        }

        // If the play medium button is clicked
        x = (float) (screenWidth / 2 - (PLAY_BUTTON_WIDTH * 0.5));
        if (clickPos.x < x + PLAY_BUTTON_WIDTH && clickPos.x > x &&
        // cur pos < top_height
                clickPos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && clickPos.y > PLAY_BUTTON_Y) {
            // Switch to the choosing state
            GameData.mainMenuState = false;
            GameData.choosingBoatState = true;
            GameData.currentUI = new ChoosingUI();
            // Set difficulty for level and AI
            GameData.difficulty = "MEDIUM";
            GameData.level = new float[] { 0.92f, 0.97f, 1f };
            GameData.isFromSave = false;
        }

        // If the play hard button is clicked
        x = (float) (screenWidth / 2 + (PLAY_BUTTON_WIDTH * 0.5));
        if (clickPos.x < x + PLAY_BUTTON_WIDTH && clickPos.x > x &&
        // cur pos < top_height
                clickPos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && clickPos.y > PLAY_BUTTON_Y) {
            // Switch to the choosing state
            GameData.mainMenuState = false;
            GameData.choosingBoatState = true;
            GameData.currentUI = new ChoosingUI();
            // Set difficulty for level and AI
            GameData.difficulty = "HARD";
            GameData.level = new float[] { 1f, 1.05f, 1.07f };
            GameData.isFromSave = false;
        }

        // If the exit button is clicked, close the game
        x = screenWidth / 2 - EXIT_BUTTON_WIDTH / 2;
        if (clickPos.x < x + EXIT_BUTTON_WIDTH && clickPos.x > x && clickPos.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
                && clickPos.y > EXIT_BUTTON_Y) {
            Gdx.app.exit();
        }

        // If the volume up is clicked, turn the volume up
        x = screenWidth - VOLUME_BUTTON_WIDTH * 2;
        if (clickPos.x < x + VOLUME_BUTTON_WIDTH && clickPos.x > x
                && clickPos.y < VOLUME_BUTTON_Y + VOLUME_BUTTON_HEIGHT && clickPos.y > VOLUME_BUTTON_Y) {
            volumeUp();
            playMusic();
        }

        // If the volume down is clicked, turn the volume down
        x = screenWidth - VOLUME_BUTTON_WIDTH;
        if (clickPos.x < x + VOLUME_BUTTON_WIDTH && clickPos.x > x
                && clickPos.y < VOLUME_BUTTON_Y + VOLUME_BUTTON_HEIGHT && clickPos.y > VOLUME_BUTTON_Y) {

            volumeDown();
            playMusic();
        }

        x = screenWidth / 2 - LOAD_BUTTON_WIDTH / 2;
        if (clickPos.x < x + LOAD_BUTTON_WIDTH && clickPos.x > x
                && clickPos.y < LOAD_BUTTON_Y + LOAD_BUTTON_HEIGHT && clickPos.y > LOAD_BUTTON_Y) {

            SaveGameData.LoadSave();
            GameData.mainMenuState = false;
            GameData.gamePlayState = true;
            GameData.currentUI = new GamePlayUI();

            // Change the music
            float currentVolume = GameData.music.getVolume();
            GameData.music.stop();
            GameData.music = Gdx.audio.newMusic(Gdx.files.internal("Love_Drama.ogg"));
            GameData.music.setVolume(currentVolume);
        }
    }
}