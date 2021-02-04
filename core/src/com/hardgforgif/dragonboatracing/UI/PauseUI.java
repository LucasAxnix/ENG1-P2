package com.hardgforgif.dragonboatracing.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.hardgforgif.dragonboatracing.GameData;
import com.hardgforgif.dragonboatracing.core.Player;

public class PauseUI extends UI {

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


    public PauseUI() {
        resumeUnselected = new Texture(Gdx.files.internal("ResumeUnselected.png"));
        resumeSelected = new Texture(Gdx.files.internal("ResumeSelected.png"));
        exitUnselected = new Texture(Gdx.files.internal("ExitUnselected.png"));
        exitSelected = new Texture(Gdx.files.internal("ExitSelected.png"));
    }

    @Override
    public void drawUI(Batch batch, Vector2 mousePos, float screenWidth, float delta) {
        batch.begin();
        if (GameData.paused) {
            // Draw the resume button
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

            // Draw the exit button
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
    public void drawPlayerUI(Batch batch, Player playerBoat) {}

    @Override
    public void getInput(float screenWidth, Vector2 mousePos) {
        if (GameData.paused) {
            // If the resume button is clicked, resume the game
            float x = screenWidth / 2 - RESUME_BUTTON_WIDTH / 2;
            if (
                    mousePos.x < x + RESUME_BUTTON_WIDTH && mousePos.x > x &&
                    // cur pos < top_height
                    mousePos.y < RESUME_BUTTON_Y + RESUME_BUTTON_HEIGHT &&
                    mousePos.y > RESUME_BUTTON_Y
                ) {
                    GameData.paused = false;
                }
            
            // If the exit button is pressed, save and leave the game
            x = screenWidth / 2 - EXIT_BUTTON_WIDTH / 2;
            if (
                mousePos.x < x + EXIT_BUTTON_WIDTH && mousePos.x > x &&
                    // cur pos < top_height
                    mousePos.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT &&
                    mousePos.y > EXIT_BUTTON_Y
                ) {
                    GameData.gamePlayState = false;
                    GameData.paused = false;
                    GameData.resetGameState = true;
                    GameData.gameInstance.saveGame();
                    // make sure there are no rigid bodies left in the lists
                    GameData.gameInstance.clearBodies();
                }
        }
    }
}
