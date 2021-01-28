package com.hardgforgif.dragonboatracing.SaveGame;

import java.util.List;

public class LaneSave {
    public int laneNumber;
    public List<ObstacleSave> obstacles;

    public LaneSave(int laneNumber, List<ObstacleSave> obstacles) {
        this.obstacles = obstacles;
        this.laneNumber = laneNumber;
    }

    public LaneSave() {}
}
