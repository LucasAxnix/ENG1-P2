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
    public Sprite bonusSprite;
    private Texture bonusTexture;
    public Body bonusBody;
    public String bonusType;

    public Bonus(String textureName) {
        bonusTexture = new Texture(textureName);
    }

    /**
     *
     * @param world which world to create the body inside
     * @param posX position x
     * @param posY position y
     * @param bodyFile json file which is a box2D for the body fixture
     * @param scale
     */
    public void createBonusBody(World world, float posX, float posY, String bodyFile, float scale){
        bonusType = bodyFile;

        bonusSprite = new Sprite(bonusTexture);
        bonusSprite.scale(scale);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(posX,posY);
        bonusBody = world.createBody(bodyDef);

        bonusBody.setUserData(this);

        BodyEditorLoader loader =new BodyEditorLoader(Gdx.files.internal(bodyFile));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;

        scale = bonusSprite.getWidth() / GameData.METERS_TO_PIXELS * bonusSprite.getScaleX();
        loader.attachFixture(bonusBody, "Name", fixtureDef, scale);

        bonusSprite.setPosition((bonusBody.getPosition().x * GameData.METERS_TO_PIXELS) - bonusSprite.getWidth()/2 ,
                (bonusBody.getPosition().y * GameData.METERS_TO_PIXELS) - bonusSprite.getHeight() / 2);


    }

    /**
     * Draw bonus
     * @param batch
     */
    public void drawBonus(Batch batch) {
        bonusSprite.setPosition((bonusBody.getPosition().x * GameData.METERS_TO_PIXELS) - bonusSprite.getWidth()/2 ,
                (bonusBody.getPosition().y * GameData.METERS_TO_PIXELS) - bonusSprite.getHeight() / 2);

        batch.begin();
        batch.draw(bonusSprite, bonusSprite.getX(), bonusSprite.getY(), bonusSprite.getOriginX(), bonusSprite.getOriginY(),
                bonusSprite.getWidth(), bonusSprite.getHeight(), bonusSprite.getScaleX(), bonusSprite.getScaleY(), bonusSprite.getRotation());
        batch.end();
    }
}

