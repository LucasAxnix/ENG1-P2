package com.hardgforgif.dragonboatracing.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.hardgforgif.dragonboatracing.GameData;
import com.hardgforgif.dragonboatracing.SaveGame.SaveGameData;
import com.hardgforgif.dragonboatracing.core.Player;

public class GamePlayUI extends UI {
    private BitmapFont positionLabel;
    private BitmapFont robustnessLabel;
    private BitmapFont staminaLabel;
    private BitmapFont timerLabel;
    private BitmapFont legLabel;
    private Texture stamina;
    private Texture robustness;
    private Sprite rBar;
    private Sprite sBar;

    private Texture resumeUnselected;
    private Texture resumeSelected;
    private Texture exitUnselected;
    private Texture exitSelected;

    private static final int RESUME_BUTTON_WIDTH = 250;
    private static final int RESUME_BUTTON_HEIGHT = 120;
    private static final int RESUME_BUTTON_Y = 500;

    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;
    private static final int EXIT_BUTTON_Y = 300;

    public GamePlayUI() {
        positionLabel = new BitmapFont();
        positionLabel.getData().setScale(1.4f);
        positionLabel.setColor(Color.BLACK);

        robustnessLabel = new BitmapFont();
        staminaLabel = new BitmapFont();

        timerLabel = new BitmapFont();
        timerLabel.getData().setScale(1.4f);
        timerLabel.setColor(Color.BLACK);

        legLabel = new BitmapFont();
        legLabel.getData().setScale(1.4f);
        legLabel.setColor(Color.BLACK);

        stamina = new Texture(Gdx.files.internal("Stamina_bar.png"));
        robustness  = new Texture(Gdx.files.internal("Robustness_bar.png"));
        rBar = new Sprite(robustness);
        sBar = new Sprite(stamina);
        sBar.setPosition(10 ,120);
        rBar.setPosition(10,60);
        
        resumeUnselected = new Texture(Gdx.files.internal("ResumeUnselected.png"));
        resumeSelected = new Texture(Gdx.files.internal("ResumeSelected.png"));
        exitUnselected = new Texture(Gdx.files.internal("ExitUnselected.png"));
        exitSelected = new Texture(Gdx.files.internal("ExitSelected.png"));
    }

    @Override
    public void drawUI(Batch batch, Vector2 mousePos, float screenWidth, float delta) {
        batch.begin();
        if (GameData.paused) {

            float x = screenWidth / 2 - RESUME_BUTTON_WIDTH / 2;
            if (
                mousePos.x < x + RESUME_BUTTON_WIDTH && mousePos.x > x &&
                        // cur pos < top_height
                        mousePos.y < RESUME_BUTTON_Y + RESUME_BUTTON_HEIGHT &&
                        mousePos.y > RESUME_BUTTON_Y
            ) {
                batch.draw(resumeSelected, x, RESUME_BUTTON_Y, RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
            } else {
                batch.draw(resumeUnselected, x, RESUME_BUTTON_Y, RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
            }

            x = screenWidth / 2 - EXIT_BUTTON_WIDTH / 2;
            if (
                mousePos.x < x + EXIT_BUTTON_WIDTH && mousePos.x > x &&
                        // cur pos < top_height
                        mousePos.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT &&
                        mousePos.y > EXIT_BUTTON_Y
            ) {
                batch.draw(exitSelected, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            } else {
                batch.draw(exitUnselected, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            }
        }

        batch.end();
    }

    @Override
    public void drawPlayerUI(Batch batch, Player playerBoat) {
        // Set the robustness and stamina bars size based on the player boat
        sBar.setSize(playerBoat.stamina, 30);
        rBar.setSize(playerBoat.robustness,30);

        batch.begin();
        // Draw the robustness and stamina bars
        sBar.draw(batch);
        rBar.draw(batch);
        robustnessLabel.draw(batch, "Robustness", 10, 110);
        staminaLabel.draw(batch, "Stamina", 10,170);

        // Draw the position label, the timer and the leg label
        positionLabel.draw(batch, GameData.standings[0] + "/4", 1225, 700);
        timerLabel.draw(batch, String.valueOf(Math.round(GameData.currentTimer * 10.0) / 10.0), 10, 700);
        legLabel.draw(batch, "Leg: " + (GameData.currentLeg + 1), 10, 650);
        batch.end();

        playMusic();
    }

    @Override
    public void getInput(float screenWidth, Vector2 mousePos) {
        if (GameData.paused) {
        
            float x = screenWidth / 2 - RESUME_BUTTON_WIDTH / 2;

            if (
                    mousePos.x < x + RESUME_BUTTON_WIDTH && mousePos.x > x &&
                    // cur pos < top_height
                    mousePos.y < RESUME_BUTTON_Y + RESUME_BUTTON_HEIGHT &&
                    mousePos.y > RESUME_BUTTON_Y
                ) {
                    GameData.paused = false;
                }

            x = screenWidth / 2 - EXIT_BUTTON_WIDTH / 2;
            if (
                mousePos.x < x + EXIT_BUTTON_WIDTH && mousePos.x > x &&
                    // cur pos < top_height
                    mousePos.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT &&
                    mousePos.y > EXIT_BUTTON_Y
                ) {
                    // TODO: save game

                    GameData.gamePlayState = false;
                    GameData.mainMenuState = true;
                    GameData.paused = false;
                    GameData.resetGameState = true;
                    GameData.gameInstance.saveGame();
                }
        }
    }
}
