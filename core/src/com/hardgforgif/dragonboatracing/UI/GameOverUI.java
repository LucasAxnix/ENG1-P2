package com.hardgforgif.dragonboatracing.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.hardgforgif.dragonboatracing.GameData;
import com.hardgforgif.dragonboatracing.core.Player;

public class GameOverUI extends UI {
    private Texture gameOverTexture;
    private Sprite gameOverSprite;
    private Texture victoryTexture;
    private Sprite victorySprite;

    private Texture background;
    private Sprite backgroundSprite;
    private Texture entryTexture;
    private Sprite[] entrySprites;
    private BitmapFont[] resultFonts;
    private BitmapFont titleFont;
    private BitmapFont timerFont;

    private ScrollingBackground scrollingBackground = new ScrollingBackground();

    public GameOverUI() {
        scrollingBackground.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        scrollingBackground.setSpeedFixed(true);
        scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        gameOverTexture = new Texture(Gdx.files.internal("gameOver.png"));
        victoryTexture = new Texture(Gdx.files.internal("victory.png"));

        gameOverSprite = new Sprite(gameOverTexture);
        victorySprite = new Sprite(victoryTexture);

        gameOverSprite.setPosition(400, 350);
        gameOverSprite.setSize(500, 500);

        victorySprite.setPosition(400, 350);
        victorySprite.setSize(500, 500);

        background = new Texture(Gdx.files.internal("Background.png"));
        backgroundSprite = new Sprite(background);
        backgroundSprite.setPosition(200, 50);
        backgroundSprite.setSize(Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 300);

        entryTexture = new Texture(Gdx.files.internal("Background.png"));

        entrySprites = new Sprite[4];
        resultFonts = new BitmapFont[4];

        titleFont = new BitmapFont();
        titleFont.getData().setScale(1.8f);
        titleFont.setColor(Color.BLACK);
        timerFont = new BitmapFont();
        timerFont.getData().setScale(1.4f);
        timerFont.setColor(Color.BLACK);

        for (int i = 0; i < 4; i++) {
            entrySprites[i] = new Sprite(entryTexture);
            entrySprites[i].setSize(backgroundSprite.getWidth() - 100, 50);
            entrySprites[i].setPosition(backgroundSprite.getX() + 50,
                    backgroundSprite.getY() + backgroundSprite.getHeight() - 200 - (i * 60));

            resultFonts[i] = new BitmapFont();
            resultFonts[i].getData().setScale(1.2f);
            resultFonts[i].setColor(Color.BLACK);
        }
    }

    @Override
    public void drawUI(Batch batch, Vector2 mousePos, float screenWidth, float delta) {
        batch.begin();
        scrollingBackground.updateAndRender(delta, batch);

        backgroundSprite.draw(batch);
        titleFont.draw(batch, "Final Standings", backgroundSprite.getX() + backgroundSprite.getWidth() / 2 - 80,
                backgroundSprite.getY() + backgroundSprite.getHeight() - 50);

        // If this was the last leg and the player won, show the victory screen
        if (GameData.currentLeg == 2 && GameData.standings[0] == 1)
            victorySprite.draw(batch);
        // Otherwise, the game is over with a loss
        else
            gameOverSprite.draw(batch);

        for (int i = 0; i < GameData.results.size(); i++) {
            int boatNr = GameData.results.get(i)[0].intValue();

            // Draw the result background element
            entrySprites[i].draw(batch);

            // Prepare the text based on the boat who's result this is
            String text = (i + 1) + ". ";
            if (boatNr == 0) {
                text += "Player: ";
            }

            else {
                text += "Opponent" + boatNr;
            }

            // Display the text
            resultFonts[i].draw(batch, text, entrySprites[i].getX() + 50, entrySprites[i].getY() + 30);

        }
        batch.end();
        playMusic();
    }

    @Override
    public void drawPlayerUI(Batch batch, Player playerBoat) {}

    @Override
    public void getInput(float screenWidth, Vector2 mousePos) {
        // When the user clicks on the screen
        if (mousePos.x != 0f && mousePos.y != 0f) {
            // Reset the game, after which the game will return to the main menu state
            GameData.gameOverState = false;
            GameData.resetGameState = true;

            // Switch the music to the main menu music
            GameData.music.stop();
            GameData.music = Gdx.audio.newMusic(Gdx.files.internal("Vibing.ogg"));
        }
    }
}
