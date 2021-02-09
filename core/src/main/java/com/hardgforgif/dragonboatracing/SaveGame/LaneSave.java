package com.hardgforgif.dragonboatracing.SaveGame;

import java.util.List;

/**
 * Holds all the data relating to the Lane that needs to be saved
 */
public class LaneSave {
    public int laneNumber;
    public List<ObstacleSave> obstacles;
    public List<BonusSave> bonuses;

    public LaneSave(int laneNumber, List<ObstacleSave> obstacles, List<BonusSave> bonuses) {
        this.obstacles = obstacles;
        this.laneNumber = laneNumber;
        this.bonuses = bonuses;
    }

    public LaneSave() {}
}
