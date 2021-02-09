package com.hardgforgif.dragonboatracing.core.AITest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AITest {
    /*
     * Again due to libgdx issues this will have to be done testing cases when an
     * obstacle is both within and without of range to do this we will take the
     * algorithm used in the program and hard-coding values for both cases. Below is
     * the algorithm used in the program to check whether an obstacle is within
     * range to start dodging:
     * 
     * 
     * for (Obstacle obstacle : this.lane.obstacles) { // Get the obstacles
     * attributes float width = obstacle.obstacleSprite.getWidth() *
     * obstacle.obstacleSprite.getScaleX(); float height =
     * obstacle.obstacleSprite.getHeight() * obstacle.obstacleSprite.getScaleY();
     * float posX = obstacle.obstacleSprite.getX() +
     * obstacle.obstacleSprite.getWidth() / 2 - width / 2; float posY =
     * obstacle.obstacleSprite.getY() + obstacle.obstacleSprite.getHeight() / 2 -
     * height / 2;
     * 
     * // Get the boat attributes float boatLeftX = objectChecker.x -
     * boatSprite.getWidth() / 2 * boatSprite.getScaleX(); float boatRightX =
     * objectChecker.x + boatSprite.getWidth() / 2 * boatSprite.getScaleX();
     * 
     * // Check for obstacles if (boatRightX >= posX && boatLeftX <= posX + width &&
     * objectChecker.y >= posY && boatSprite.getY() + boatSprite.getHeight() / 2 <=
     * posY) { detectedObstacleYPos = posY; return true; } } return false;
     * 
     * first it gets width and value of an obstacle and then getting both the a
     * co-ordinates for the left and right side of the boat and then checks the if
     * the obstacle is within dodging range
     * If the algorithm above is changd in the main code then the algorithm in the test method must also be 
     * changed as if not the automated Circle CI test will not be up to date and will no longer check if the 
     * code is stable.
     * 
     */
    @Test
    public void withinDistanceTestTrue() throws Exception {

        assertEquals(true, withindistance(50, 2000, 30, 40, 55, 1950, 50));
    }

    @Test
    public void withinDistanceTestFalse() throws Exception {
        // make the obstacle
        assertEquals(false, withindistance(50, 2000, 30, 40, 55, 1000, 50));
    }

    public boolean withindistance(int obstacleX, int obstacleY, int obstacleWidth, int boatLeftX, int boatRightX,
            int boatY, int boatheight) {

        // this simplifies the check to check whether the boat is contained within the
        // obstacles x range and if the boat is below the obstacle
        // within a certain distance 
        
        if (boatRightX <= obstacleX + obstacleWidth / 2 && boatLeftX >= obstacleX - obstacleWidth / 2
                && boatY <= obstacleY && boatY + boatheight * 1.5 > obstacleY) {

            return true;
        }
        return false;

    }

    // Every leg of the race the stats of the race increases to show this
    // we will demonstrate the difficulty increase for the Medium difficulty if the
    // level per leg is wrong
    // This shows then when the values are multiplied by the AI's level difficulty
    // the AI's stats go up each level
    // to prove the this is correct we will demonstrate with correct and incorrect
    // level difficulty multipliers
    // If the levels are changed then every time it is changed in the mainn code it 
    // should be changed here as well to keep the Circle CI checks correct 
    @Test
    public void levelDifficultyTrue() throws Exception {

        assertEquals(true, difficultyIncreaseTrue(120, 110, 100, 80));
    }

    @Test
    public void levelDifficultyFalse() throws Exception {

        assertEquals(false, difficultyIncreaseFalse(120, 110, 100, 80));
    }

    public boolean difficultyIncreaseTrue(float robustness, float speed, float acceleration, float maneuverabillity) {
        float[] level = new float[] { 0.92f, 0.97f, 1f };
        float[] stats = new float[] { robustness, speed, acceleration, maneuverabillity };
        float[][] statsOnEachLeg = new float[3][4];
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 4; i++) {
                statsOnEachLeg[j][i] = level[j] * stats[i];
            }
        }
        if ((statsOnEachLeg[0][0] < statsOnEachLeg[1][0] && statsOnEachLeg[1][0] < statsOnEachLeg[2][0])
                && (statsOnEachLeg[0][1] < statsOnEachLeg[1][1] && statsOnEachLeg[1][1] < statsOnEachLeg[2][1])
                && (statsOnEachLeg[0][2] < statsOnEachLeg[1][2] && statsOnEachLeg[1][2] < statsOnEachLeg[2][2])
                && (statsOnEachLeg[0][3] < statsOnEachLeg[1][3] && statsOnEachLeg[1][3] < statsOnEachLeg[2][3])) {
            return true;
        }
        return false;
    }

    public boolean difficultyIncreaseFalse(float robustness, float speed, float acceleration, float maneuverabillity) {
        float[] level = new float[] { 0.92f, 0.97f, 0.95f };
        float[] stats = new float[] { robustness, speed, acceleration, maneuverabillity };
        float[][] statsOnEachLeg = new float[3][4];
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 4; i++) {
                statsOnEachLeg[j][i] = level[j] * stats[i];
            }
        }
        if ((statsOnEachLeg[0][0] < statsOnEachLeg[1][0] && statsOnEachLeg[1][0] < statsOnEachLeg[2][0])
                && (statsOnEachLeg[0][1] < statsOnEachLeg[1][1] && statsOnEachLeg[1][1] < statsOnEachLeg[2][1])
                && (statsOnEachLeg[0][2] < statsOnEachLeg[1][2] && statsOnEachLeg[1][2] < statsOnEachLeg[2][2])
                && (statsOnEachLeg[0][3] < statsOnEachLeg[1][3] && statsOnEachLeg[1][3] < statsOnEachLeg[2][3])) {
            return true;
        }
        return false;
    }
}
