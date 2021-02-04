package com.hardgforgif.dragonboatracing.core;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.hardgforgif.dragonboatracing.GameData;

import java.util.Random;

public class Lane {
    public float[][] leftBoundry;
    public int leftIterator = 0;
    public float[][] rightBoundry;
    public int rightIterator = 0;
    private MapLayer leftLayer;
    private MapLayer rightLayer;

    public Obstacle[] obstacles;

    public Bonus[] bonuses;

    public Lane(int mapHeight, MapLayer left, MapLayer right, int nrObstacles, int nrbounses) {
        leftBoundry = new float[mapHeight][2];
        rightBoundry = new float[mapHeight][2];

        leftLayer = left;
        rightLayer = right;

        obstacles = new Obstacle[nrObstacles];
        bonuses = new Bonus[nrbounses];

    }

    /**
     * Construct bodies that match the lane separators
     * 
     * @param unitScale The size of a tile in pixels
     */
    public void constructBoundries(float unitScale) {
        MapObjects objects = leftLayer.getObjects();
        
        // Loop through all the objects making up the left side of the river
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            float height = rectangle.getY() * unitScale;
            float limit = (rectangle.getX() * unitScale) + (rectangle.getWidth() * unitScale);
            // Set the height and limit of the boundary
            leftBoundry[leftIterator][0] = height;
            leftBoundry[leftIterator++][1] = limit;
        }

        objects = rightLayer.getObjects();

        // Loop through all the objects making up the right side of the river
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            float height = rectangle.getY() * unitScale;
            float limit = rectangle.getX() * unitScale;
            // Set the height and limit of the boundary
            rightBoundry[rightIterator][0] = height;
            rightBoundry[rightIterator++][1] = limit;
        }
    }

    /**
     * Get the limits of the lane at the given y position
     * 
     * @param yPosition the position to get the limit at
     * @return a float array of the left and right boundary
     */
    public float[] getLimitsAt(float yPosition) {
        float[] boundaries = new float[2];
        int i;

        // Loop through all of the left boundaries
        for (i = 1; i < leftIterator; i++) {
            // Find the boundary greater than the y position given
            if (leftBoundry[i][0] > yPosition) {
                break;
            }
        }
        // Add the previous left boundary to the boundaries array at index 0
        boundaries[0] = leftBoundry[i - 1][1];

        // Loop through all of the right boundaries
        for (i = 1; i < rightIterator; i++) {
            // Find the boundary greater than the y position given
            if (rightBoundry[i][0] > yPosition) {
                break;
            }
        }
        // Add the previous right boundary to the boundaries array at index 1
        boundaries[1] = rightBoundry[i - 1][1];
        return boundaries;
    }

    /**
     * Spawn obstacles on the lane
     * 
     * @param world     the world to spawn obstacles in
     * @param mapHeight height of the map to draw on
     */
    public void spawnObstacles(World world, float mapHeight) {
        int obstacleCount = obstacles.length;
        float segmentLength = mapHeight / obstacleCount;

        // Loop for the amount of obstacles to be added
        for (int i = 0; i < obstacleCount; i++) {
            // Pick a random obstacle
            int randomIndex = new Random().nextInt(6);
            float scale = 0f;
            // Scale the obstacle to the correct size if it is obstacle 0 or 5
            if (randomIndex == 0 || randomIndex == 5)
                scale = -0.8f;
            // Create the new obstacle
            obstacles[i] = new Obstacle("Obstacles/Obstacle" + (randomIndex + 1) + ".png");
            float segmentStart = i * segmentLength;
            float yPos = (float) (600f + (segmentStart + Math.random() * segmentLength));

            // Create the rigid body for the obstacle
            float[] limits = this.getLimitsAt(yPos);
            float leftLimit = limits[0] + 50;
            float rightLimit = limits[1];
            float xPos = (float) (leftLimit + Math.random() * (rightLimit - leftLimit));

            obstacles[i].createObstacleBody(world, xPos / GameData.METERS_TO_PIXELS, yPos / GameData.METERS_TO_PIXELS,
                    "Obstacles/Obstacle" + (randomIndex + 1) + ".json", scale);
        }
    }

    /**
     * Spawn bonuses in the lane
     * 
     * @param world the world to spawn the bonuses in
     * @param mapHeight the height of the map to draw on
     */
    public void spawnBonus(World world, float mapHeight) {
        int bonusCount = bonuses.length;
        float segmentLength = mapHeight / bonusCount;

        // Loop for the amount of bonuses to be added
        for (int i = 0; i < bonusCount; i++) {
            // Pick a random bonus
            int randomIndex = new Random().nextInt(5);
            float scale = 0f;
            // Create the new bonus
            bonuses[i] = new Bonus("bonuses/bonus" + (randomIndex + 1) + ".png");
            float segmentStart = i * segmentLength;
            float yPos = (float) (600f + (segmentStart + Math.random() * segmentLength));

            // Create the rigid body for the bonus
            float[] limits = this.getLimitsAt(yPos);
            float leftLimit = limits[0] + 50;
            float rightLimit = limits[1];
            float xPox = (float) (leftLimit + Math.random() * (rightLimit - leftLimit));

            bonuses[i].createBonusBody(world, xPox / GameData.METERS_TO_PIXELS, yPos / GameData.METERS_TO_PIXELS,
                    "bonuses/bonus" + (randomIndex + 1) + ".json", scale);
        }
    }

}
