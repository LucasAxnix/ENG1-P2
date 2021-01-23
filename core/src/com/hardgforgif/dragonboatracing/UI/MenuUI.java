package com.hardgforgif.dragonboatracing.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.hardgforgif.dragonboatracing.GameData;
import com.hardgforgif.dragonboatracing.core.Player;

public class MenuUI extends UI {

    // Sets the dimensions for all the UI components
    private static final int LOGO_WIDTH = 400;
    private static final int LOGO_HEIGHT = 200;
    private static final int LOGO_Y = 450;

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;
    private static final int PLAY_BUTTON_Y = 230;

    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;
    private static final int EXIT_BUTTON_Y = 100;

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
    Texture logo;

    ScrollingBackground scrollingBackground = new ScrollingBackground();

    public MenuUI() {
        scrollingBackground.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        scrollingBackground.setSpeedFixed(true);
        scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        playEasyButtonActive = new Texture("PlayEasySelected.png");// "PlayEasySelected.png"
        playEasyButtonInactive = new Texture("PlayEasyUnSelected.png");
        playMediumButtonActive = new Texture("PlayMediumSelected.png");// "PlayMediumSelected.png"
        playMediumButtonInactive = new Texture("PlayMediumUnselected.png");
        playHardButtonActive = new Texture("PlayHardSelected.png");// "PlayHardSelected.png"
        playHardButtonInactive = new Texture("PlayHardUnselected.png");
        logo = new Texture("Title.png");
        exitButtonActive = new Texture("ExitSelected.png");
        exitButtonInactive = new Texture("ExitUnselected.png");
    }

    @Override
    public void drawUI(Batch batch, Vector2 mousePos, float screenWidth, float delta) {
        batch.begin();
        scrollingBackground.updateAndRender(delta, batch);
        batch.draw(logo, screenWidth / 2 - LOGO_WIDTH / 2, LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);

        // Draw easy button
        float x = (float) (screenWidth / 2 - (PLAY_BUTTON_WIDTH * 1.5));
        if (mousePos.x < x + PLAY_BUTTON_WIDTH && mousePos.x > x &&
        // cur pos < top_height
                mousePos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && mousePos.y > PLAY_BUTTON_Y) {
            batch.draw(playEasyButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            batch.draw(playEasyButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        // Draw medium play button
        x = (float) (screenWidth / 2 - (PLAY_BUTTON_WIDTH * 0.5));
        if (mousePos.x < x + PLAY_BUTTON_WIDTH && mousePos.x > x &&
        // cur pos < top_height
                mousePos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && mousePos.y > PLAY_BUTTON_Y) {
            batch.draw(playMediumButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            batch.draw(playMediumButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        // Draw Hard play butt(on
        x = (float) (screenWidth / 2 + (PLAY_BUTTON_WIDTH * 0.5));
        if (mousePos.x < x + PLAY_BUTTON_WIDTH && mousePos.x > x &&
        // cur pos < top_height
                mousePos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && mousePos.y > PLAY_BUTTON_Y) {
            batch.draw(playHardButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            batch.draw(playHardButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        // Otherwise draw the selected buttons
        x = screenWidth / 2 - EXIT_BUTTON_WIDTH / 2;
        if (mousePos.x < x + EXIT_BUTTON_WIDTH && mousePos.x > x && mousePos.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
                && mousePos.y > EXIT_BUTTON_Y) {
            batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        } else {
            batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
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
        }

        // If the exit button is clicked, close the game
        x = screenWidth / 2 - EXIT_BUTTON_WIDTH / 2;
        if (clickPos.x < x + EXIT_BUTTON_WIDTH && clickPos.x > x && clickPos.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
                && clickPos.y > EXIT_BUTTON_Y) {
            Gdx.app.exit();
        }

    }
}