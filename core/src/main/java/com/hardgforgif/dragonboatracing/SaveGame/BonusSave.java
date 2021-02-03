package com.hardgforgif.dragonboatracing.SaveGame;

import javax.lang.model.element.TypeElement;

public class BonusSave {
    public String type;
    public float x;
    public float y;

    public BonusSave(String type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public BonusSave(String type) {
        this.type = type;
    }

    public BonusSave() {}
}
