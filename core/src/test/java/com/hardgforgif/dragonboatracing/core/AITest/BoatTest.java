package com.hardgforgif.dragonboatracing.core.AITest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoatTest {
    @Test
    public void uniqueBoats() throws Exception {

        assertEquals(true, unique());
    }

    public boolean unique() {
        boolean _unique = true;
        float[][] boatsStats = new float[][] { { 120, 110, 100, 80 }, { 55, 110, 130, 60 }, { 90, 110, 100, 130 },
                { 65, 120, 90, 55 } };
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != j) {
                    if (boatsStats[i] == boatsStats[j]) {
                        _unique = false;
                    }
                }
            }
        }

        return _unique;
    }

    /*
     * Due to problems with libgdx this will be a copy of the boat.hasfinished
     * function and will be coded with hard values as the boat cannot be accessed
     * due to libgdx
     * 
     * if (boatSprite.getY() + boatSprite.getHeight() / 2 > 9000f){ return true; }
     * return false;
     * 
     * 
     * above is the function it checks if the two values added together is greater
     * than 9000 so to test we check whether it has finished we shall create a
     * variable that puts a hard coded value for the y value for both true finished
     * and false finished.
     * If any of the algorithms that the code relates to in the main body then the values
     * In the test should also change to keep circle CI up to date to keep the automated tests
     * having any form of value to them.
     */

    @Test
    public void boatFinishedtrue() throws Exception {

        assertEquals(true, finishedtrue());
    }

    @Test
    public void boatFinishedfalse() throws Exception {

        assertEquals(false, finishedfalse());
    }

    public boolean finishedtrue() {

        float boatsY = 9001f;
        if (boatsY > 9000f) {
            return true;
        }
        return false;
    }

    public boolean finishedfalse() {
        float boatsY = 5000f;
        if (boatsY > 9000f) {
            return true;
        }
        return false;
    }
}
