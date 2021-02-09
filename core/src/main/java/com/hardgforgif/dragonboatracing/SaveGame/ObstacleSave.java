package com.hardgforgif.dragonboatracing.SaveGame;

/**
 * Holds all the data relating to the obstacle object that needs to be saved
 */
public class ObstacleSave {
    public String type;
    public float x;
    public float y;

    public ObstacleSave(String type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public ObstacleSave(String type) {
        this.type = type;
    }

    public ObstacleSave() {}
}
