package com.hardgforgif.dragonboatracing.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.hardgforgif.dragonboatracing.BodyEditorLoader;
import com.hardgforgif.dragonboatracing.GameData;

public class Bonus {
    // The bonus' visuals
    public Sprite bonusSprite;
    private Texture bonusTexture;

    // The bonus' rigid body
    public Body bonusBody;

    // Bonus data
    public String bonusType;

    public Bonus(String textureName) {
        bonusTexture = new Texture(textureName);
    }

    /**
     *
     * @param world    which world to create the body inside
     * @param posX     position x
     * @param posY     position y
     * @param bodyFile json file which is a box2D for the body fixture
     * @param scale
     */
    public void createBonusBody(World world, float posX, float posY, String bodyFile, float scale) {
        // Set the type of the bonus to its file locaton
        bonusType = bodyFile;

        // Set bonus' sprite and scale
        bonusSprite = new Sprite(bonusTexture);
        bonusSprite.scale(scale);

        // Define the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(posX, posY);

        // Create the bonus' body
        bonusBody = world.createBody(bodyDef);
        // Mark the body as the bonus' body
        bonusBody.setUserData(this);

        // Load the body fixture from box2D editor
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(bodyFile));
        FixtureDef fixtureDef = new FixtureDef();

        // Set the physical properties of the body
        fixtureDef.density = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;

        // Attach the fixture to the body
        scale = bonusSprite.getWidth() / GameData.METERS_TO_PIXELS * bonusSprite.getScaleX();
        loader.attachFixture(bonusBody, "Name", fixtureDef, scale);

        // Set the position of the bonus
        bonusSprite.setPosition((bonusBody.getPosition().x * GameData.METERS_TO_PIXELS) - bonusSprite.getWidth() / 2,
                (bonusBody.getPosition().y * GameData.METERS_TO_PIXELS) - bonusSprite.getHeight() / 2);

    }

    /**
     * Draw bonus
     * 
     * @param batch the batch to draw on
     */
    public void drawBonus(Batch batch) {
        bonusSprite.setPosition((bonusBody.getPosition().x * GameData.METERS_TO_PIXELS) - bonusSprite.getWidth() / 2,
                (bonusBody.getPosition().y * GameData.METERS_TO_PIXELS) - bonusSprite.getHeight() / 2);

        batch.begin();
        batch.draw(bonusSprite, bonusSprite.getX(), bonusSprite.getY(), bonusSprite.getOriginX(),
                bonusSprite.getOriginY(), bonusSprite.getWidth(), bonusSprite.getHeight(), bonusSprite.getScaleX(),
                bonusSprite.getScaleY(), bonusSprite.getRotation());
        batch.end();
    }
}
