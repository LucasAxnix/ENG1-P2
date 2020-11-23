package com.hardgforgif.dragonboatracing.UI;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.hardgforgif.dragonboatracing.GameData;
import com.hardgforgif.dragonboatracing.core.Player;
import javafx.util.Pair;

import java.util.Comparator;

public class ResultsUI extends UI{
    private Texture background;
    private Sprite backgroundSprite;
    private Texture entryTexture;
    private Sprite[] entrySprites;
    private BitmapFont[] resultFonts;
    private BitmapFont titleFont;
    private BitmapFont timer_label;

    public ResultsUI(){
        entrySprites = new Sprite[4];
        resultFonts = new BitmapFont[4];
        titleFont = new BitmapFont();
        titleFont.getData().setScale(1.8f);
        titleFont.setColor(Color.BLACK);
        timer_label = new BitmapFont();
        timer_label.getData().setScale(1.4f);
        timer_label.setColor(Color.BLACK);


        background = new Texture(Gdx.files.internal("Background.png"));
        backgroundSprite = new Sprite(background);
        backgroundSprite.setPosition(200,150);
        backgroundSprite.setSize(Gdx.graphics.getWidth() - 400,Gdx.graphics.getHeight() - 300);

        entryTexture = new Texture(Gdx.files.internal("Background.png"));

        // Give the position of all the entries in the result table
        for (int i = 0; i < 4; i++){
            entrySprites[i] = new Sprite(entryTexture);
            entrySprites[i].setSize(backgroundSprite.getWidth() - 100,50);
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
        backgroundSprite.draw(batch);
        titleFont.draw(batch, "Results", backgroundSprite.getX() + backgroundSprite.getWidth() / 2 - 30,
                backgroundSprite.getY() + backgroundSprite.getHeight() - 50);

        // Sort the currently available results in ascending order
        GameData.results.sort(new Comparator<Pair<Integer, Float>>() {
            @Override
            public int compare(Pair<Integer, Float> o1, Pair<Integer, Float> o2) {
                if (o1.getValue() + GameData.penalties[o1.getKey()] > o2.getValue()+ GameData.penalties[o2.getKey()]) {
                    return 1;
                } else if (o1.getValue() + GameData.penalties[o1.getKey()] ==
                           o2.getValue()+ GameData.penalties[o2.getKey()]) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        // Go through the results and display them on the screen
        for (int i = 0; i < GameData.results.size(); i++){
            int boatNr = GameData.results.get(i).getKey();
            float result = GameData.results.get(i).getValue();
            float penalties = 0f;

            // Draw the result background element
            entrySprites[i].draw(batch);

            // Prepare the text based on the boat who's result this is
            String text = (i + 1) + ". ";
            if (boatNr == 0){
                text += "Player: ";
            }

            else{
                text += "Opponent" + boatNr + ": ";
            }

            // Add the penalties
            penalties += GameData.penalties[boatNr];
            result += penalties;
            if (result != Float.MAX_VALUE)
                text += result;
            else
                text += "DNF";

            // Display the text
            resultFonts[i].draw(batch, text, entrySprites[i].getX() + 50,  entrySprites[i].getY() + 30);
            resultFonts[i].draw(batch, "Penalties: " + GameData.penalties[boatNr],
                             entrySprites[i].getX() + 300, entrySprites[i].getY() + 30);

        }
        timer_label.draw(batch, String.valueOf(Math.round(GameData.currentTimer * 10.0) / 10.0), 10, 700);
        batch.end();

        playMusic();
    }

    @Override
    public void drawPlayerUI(Batch batch, Player playerBoat) {

    }

    @Override
    public void getInput(float screenWidth, Vector2 mousePos) {
        // When the user clicks anywhere on the screen, switch the game state as necessary
        if(mousePos.x != 0f && mousePos.y != 0f && GameData.results.size() == 4) {
            GameData.gamePlay = false;
            float playerResult = GameData.results.get(GameData.standings[0] - 1).getValue();

            // If the game is over due to player's dnf or victory, switch to the endgame screen
            if (GameData.currentLeg == 2 || playerResult == Float.MAX_VALUE || GameData.standings[0] == 4){
                GameData.showResults = false;
                GameData.endGame = true;
                GameData.currentUI = new EndGameUI();
            }
            // Otherwise prepare for the next leg
            else{
                GameData.resetGame = true;
            }

        }
    }
}