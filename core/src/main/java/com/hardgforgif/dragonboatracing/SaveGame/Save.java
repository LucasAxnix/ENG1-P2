package com.hardgforgif.dragonboatracing.SaveGame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Save {
    public int leg;
    public float time;
    public List<Float[]> standings;
    public ArrayList<BoatSave> boats;
    public LaneSave[] lanes;
    public String difficulty;
    public boolean hasPlayerFinished;
    
    public Save(boolean hasPlayerFinished) {
        boats = new ArrayList<>();
        lanes = new LaneSave[4];
        this.hasPlayerFinished = hasPlayerFinished;
    }

    public Save() {}

    public void setLeg(int leg) {
        this.leg = leg;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setStandings(List<Float[]> standings) {
        this.standings = standings;
    }

    public void addNewBoat(BoatSave boat) {
        boats.add(boat);
    }

    public void setLane(int laneNumber, LaneSave lane) {
        lanes[laneNumber] = lane;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
