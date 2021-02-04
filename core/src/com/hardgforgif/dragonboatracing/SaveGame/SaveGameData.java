package com.hardgforgif.dragonboatracing.SaveGame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.hardgforgif.dragonboatracing.GameData;
import com.hardgforgif.dragonboatracing.UI.ResultsUI;
import com.hardgforgif.dragonboatracing.core.AI;
import com.hardgforgif.dragonboatracing.core.Bonus;
import com.hardgforgif.dragonboatracing.core.Lane;
import com.hardgforgif.dragonboatracing.core.Map;
import com.hardgforgif.dragonboatracing.core.Obstacle;
import com.hardgforgif.dragonboatracing.core.Player;

public class SaveGameData {

    /**
     * Saves the current game state into a json file (save.json)
     * 
     * @param player    the player object
     * @param opponents a list of all the opponents
     * @param maps      a list of the maps for each leg
     */
    public static void SaveGame(Player player, AI[] opponents, Map[] maps) {
        // Create a new json string
        Json json = new Json();
        json.setOutputType(OutputType.json);

        // Create a new Save object to hold all the data to be saved
        Save save = new Save(GameData.showResultsState);

        // Write all the single value data
        save.setLeg(GameData.currentLeg);
        save.setTime(GameData.currentTimer);
        save.setStandings(GameData.results);
        save.setDifficulty(GameData.difficulty);
        // Write the player's boat
        save.addNewBoat(new BoatSave(player.boatType, player.boatBody.getPosition().x, player.boatBody.getPosition().y,
                true, player.stamina, player.robustness, player.maneuverability, player.speed, player.acceleration, 0,
                player.currentSpeed, player.boatBody.getAngle()));

        // Write all the opponent's boats
        for (int i = 0; i < opponents.length; i++) {
            AI aiBoat = opponents[i];
            save.addNewBoat(new BoatSave(aiBoat.boatType, aiBoat.boatBody.getPosition().x,
                    aiBoat.boatBody.getPosition().y, false, aiBoat.stamina, aiBoat.robustness, aiBoat.maneuverability,
                    aiBoat.speed, aiBoat.acceleration, i + 1, aiBoat.currentSpeed, aiBoat.boatBody.getAngle()));
        }

        // Loop through all the lanes on the current map
        for (int i = 0; i < maps[GameData.currentLeg].lanes.length; i++) {
            // Write all the obstacles
            Lane lane = maps[GameData.currentLeg].lanes[i];
            List<ObstacleSave> obstacles = new ArrayList<>();
            for (Obstacle obstacle : lane.obstacles) {
                if (obstacle.obstacleBody != null) {
                    obstacles.add(new ObstacleSave(obstacle.obstacleType.replace(".json", ""),
                            obstacle.obstacleBody.getPosition().x, obstacle.obstacleBody.getPosition().y));
                } else {
                    obstacles.add(new ObstacleSave(obstacle.obstacleType.replace(".json", "")));
                }
            }

            // Write all the bonuses
            List<BonusSave> bonuses = new ArrayList<BonusSave>();
            for (Bonus bonus : lane.bonuses) {
                if (bonus.bonusBody != null) {
                    bonuses.add(new BonusSave(bonus.bonusType.replace(".json", ""), bonus.bonusBody.getPosition().x,
                            bonus.bonusBody.getPosition().y));
                } else {
                    bonuses.add(new BonusSave(bonus.bonusType.replace(".json", "")));
                }
            }

            // Write the save for that lane
            save.setLane(i, new LaneSave(i, obstacles, bonuses));
        }
        // Write the save data to the json file
        FileHandle gameSave = Gdx.files.local("save.json");
        gameSave.writeString(json.prettyPrint(save), false);
    }

    /**
     * Loads the current saved game (save.json)
     */
    public static void LoadSave() {
        // Load the json file and read its contents
        FileHandle saveFile = Gdx.files.local("save.json");
        String saveString = saveFile.readString();

        // Create a new json string from the save file
        Json json = new Json();
        // Write all of the save data to a new Save object
        Save save = json.fromJson(Save.class, saveString);

        GameData.isFromSave = true;

        // Set the results ui if the save was at the end of a leg
        if (save.hasPlayerFinished) {
            GameData.currentUI = new ResultsUI();
            GameData.showResultsState = true;
        }

        // Set all the single value data in GameData
        GameData.currentLeg = save.leg;
        GameData.currentTimer = save.time;
        GameData.results = save.standings;
        GameData.difficulty = save.difficulty;

        // Set the AI difficulty level
        switch (save.difficulty) {
            case "EASY":
                GameData.level = new float[] { 0.87f, 0.89f, 0.92f };
                break;
            case "MEDIUM":
                GameData.level = new float[] { 0.92f, 0.97f, 1f };
                break;
            case "HARD":
                GameData.level = new float[] { 1f, 1.05f, 1.07f };
                break;
        }

        // Set the lanes up
        GameData.spawnedObstacles = true;
        GameData.gameInstance.createLanes();

        List<BoatSave> boats = save.boats;
        // Set all of the boat data in the game class
        for (int i = 0; i < boats.size(); i++) {
            BoatSave boatSave = boats.get(i);
            if (boatSave.isPlayer) {
                // Set the player boat
                Player player = new Player(boatSave.robustness, boatSave.speed, boatSave.acceleration,
                        boatSave.maneuverability, boatSave.type,
                        GameData.gameInstance.getMap()[GameData.currentLeg].lanes[boatSave.lane]);
                player.createBoatBody(GameData.gameInstance.getWorld()[GameData.currentLeg],
                        GameData.startingPoints[i][0], GameData.startingPoints[i][1], "Boat1.json");

                player.boatBody.setTransform(new Vector2(boatSave.x, boatSave.y), boatSave.currentAngle);
                player.boatSprite.setRotation(boatSave.currentAngle);
                player.currentSpeed = boatSave.currentSpeed;

                player.boatSprite.setRotation((float) Math.toDegrees(player.boatBody.getAngle()));
                player.boatSprite.setPosition(
                        (player.boatBody.getPosition().x * GameData.METERS_TO_PIXELS)
                                - player.boatSprite.getWidth() / 2,
                        (player.boatBody.getPosition().y * GameData.METERS_TO_PIXELS)
                                - player.boatSprite.getHeight() / 2);

                GameData.gameInstance.setPlayer(player);
            } else {
                // Set the opponent boats
                AI opponentBoat = new AI(boatSave.robustness, boatSave.speed, boatSave.acceleration,
                        boatSave.maneuverability, boatSave.type,
                        GameData.gameInstance.getMap()[GameData.currentLeg].lanes[boatSave.lane], true);
                opponentBoat.createBoatBody(GameData.gameInstance.getWorld()[GameData.currentLeg],
                        GameData.startingPoints[i][0], GameData.startingPoints[i][1], "Boat1.json");

                opponentBoat.boatBody.setTransform(new Vector2(boatSave.x, boatSave.y), boatSave.currentAngle);
                opponentBoat.boatSprite.setRotation(boatSave.currentAngle);
                opponentBoat.currentSpeed = boatSave.currentSpeed;

                opponentBoat.boatSprite.setRotation((float) Math.toDegrees(opponentBoat.boatBody.getAngle()));
                opponentBoat.boatSprite.setPosition(
                        (opponentBoat.boatBody.getPosition().x * GameData.METERS_TO_PIXELS)
                                - opponentBoat.boatSprite.getWidth() / 2,
                        (opponentBoat.boatBody.getPosition().y * GameData.METERS_TO_PIXELS)
                                - opponentBoat.boatSprite.getHeight() / 2);

                GameData.gameInstance.setOpponent(opponentBoat, i - 1);
            }
        }

        // Loop through each lane
        for (int i = 0; i < 4; i++) {
            Lane lane = GameData.gameInstance.getMap()[GameData.currentLeg].lanes[i];
            LaneSave laneSave = save.lanes[i];
            // Spawn each of the obstacles back in
            for (int j = 0; j < lane.obstacles.length; j++) {
                ObstacleSave obstacleSave = laneSave.obstacles.get(j);
                String type = obstacleSave.type;
                Obstacle obstacle = new Obstacle(type + ".png");
                // Set the scale correctly for obstacles 1 and 6
                float scale = type.contains("1") || type.contains("6") ? -0.8f : 0f;
                obstacle.createObstacleBody(GameData.gameInstance.getWorld()[GameData.currentLeg], obstacleSave.x,
                        obstacleSave.y, type + ".json", scale);
                lane.obstacles[j] = obstacle;
            }

            // Spawn each of the bonuses back in
            for (int j = 0; j < lane.bonuses.length; j++) {
                BonusSave bonusSave = laneSave.bonuses.get(j);
                String type = bonusSave.type;
                Bonus bonus = new Bonus(type + ".png");
                bonus.createBonusBody(GameData.gameInstance.getWorld()[GameData.currentLeg], bonusSave.x, bonusSave.y,
                        type + ".json", 0);
                lane.bonuses[j] = bonus;
            }
        }
    }
}
