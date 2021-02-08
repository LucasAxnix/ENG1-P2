package com.hardgforgif.dragonboatracing.SaveGame;

import java.util.List;

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
