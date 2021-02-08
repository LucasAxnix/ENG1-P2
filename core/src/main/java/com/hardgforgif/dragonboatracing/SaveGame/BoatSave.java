package com.hardgforgif.dragonboatracing.SaveGame;

public class BoatSave {
    public int type;
    public float x;
    public float y;
    public boolean isPlayer;
    public float stamina;
    public float robustness;
    public float maneuverability;
    public float speed;
    public float acceleration;
    public int lane;
    public float currentSpeed;
    public float currentAngle;

    public BoatSave(int type, float x, float y, boolean isPlayer, float stamina, float robustness, float maneuverability, float speed, float accleration, int lane, float currentSpeed, float currentAngle) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.isPlayer = isPlayer;
        this.stamina = stamina; 
        this.robustness = robustness;
        this.maneuverability = maneuverability;
        this.speed = speed;
        this.acceleration = accleration;
        this.lane = lane;
        this.currentSpeed = currentSpeed;
        this.currentAngle = currentAngle;
    }

    public BoatSave() {}
}
