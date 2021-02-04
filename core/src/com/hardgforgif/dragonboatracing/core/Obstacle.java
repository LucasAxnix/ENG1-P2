package com.hardgforgif.dragonboatracing.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.hardgforgif.dragonboatracing.GameData;
import com.hardgforgif.dragonboatracing.BodyEditorLoader;

public class Obstacle {
    // Obstacle visuals
    public Sprite obstacleSprite;
    private Texture obstacleTexture;

    // Obstacle rigid body
    public Body obstacleBody;
    public String obstacleType;

    public Obstacle(String textureName) {
        obstacleTexture = new Texture(textureName);
    }

    /**
     * Creates a new obstacle body
     * 
     * @param world    World to create the body in
     * @param posX     x location of the body, in meters
     * @param posY     y location of the body, in meters
     * @param bodyFile the name of the box2D editor json file for the body fixture
     */
    public void createObstacleBody(World world, float posX, float posY, String bodyFile, float scale) {
        // Set the tpye of the obstacle to its file location
        obstacleType = bodyFile;

        // Set obstacle's sprite and scale
        obstacleSprite = new Sprite(obstacleTexture);
        obstacleSprite.scale(scale);

        // Define the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(posX, posY);

        // Create the obstacle's body
        obstacleBody = world.createBody(bodyDef);
        // Mark the body as the obstacle's body
        obstacleBody.setUserData(this);

        // Load the body fixture from box2D editor
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(bodyFile));
        FixtureDef fixtureDef = new FixtureDef();

        // Set the physical properties of the body
        fixtureDef.density = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;

        // Attach the fixture to the body
        scale = obstacleSprite.getWidth() / GameData.METERS_TO_PIXELS * obstacleSprite.getScaleX();
        loader.attachFixture(obstacleBody, "Name", fixtureDef, scale);

        // Set the position of the obstalce
        obstacleSprite.setPosition(
                (obstacleBody.getPosition().x * GameData.METERS_TO_PIXELS) - obstacleSprite.getWidth() / 2,
                (obstacleBody.getPosition().y * GameData.METERS_TO_PIXELS) - obstacleSprite.getHeight() / 2);
    }

    /**
     * Draw the obstacle
     * 
     * @param batch Batch to draw on
     */
    public void drawObstacle(Batch batch) {
        obstacleSprite.setPosition(
                (obstacleBody.getPosition().x * GameData.METERS_TO_PIXELS) - obstacleSprite.getWidth() / 2,
                (obstacleBody.getPosition().y * GameData.METERS_TO_PIXELS) - obstacleSprite.getHeight() / 2);
        batch.begin();
        batch.draw(obstacleSprite, obstacleSprite.getX(), obstacleSprite.getY(), obstacleSprite.getOriginX(),
                obstacleSprite.getOriginY(), obstacleSprite.getWidth(), obstacleSprite.getHeight(),
                obstacleSprite.getScaleX(), obstacleSprite.getScaleY(), obstacleSprite.getRotation());
        batch.end();
    }
}
