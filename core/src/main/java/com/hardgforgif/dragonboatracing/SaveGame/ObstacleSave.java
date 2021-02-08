package com.hardgforgif.dragonboatracing.SaveGame;

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
