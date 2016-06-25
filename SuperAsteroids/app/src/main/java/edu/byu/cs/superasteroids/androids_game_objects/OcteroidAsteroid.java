package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.RectF;

import edu.byu.cs.superasteroids.androids_game_definitions.OcteroidAsteroidType;

/**
 * This class is a Octeroid Asteroid you shoot in the game.
 */
public class OcteroidAsteroid extends Asteroid {

    private OcteroidAsteroidType asteroidType;

    public OcteroidAsteroid(int positionX, int positionY, int rotation, float speed, float direction,
                            float acceleration, int damage,
                            OcteroidAsteroidType asteroidType, int imageId) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
        this.asteroidType = asteroidType;
        int imageWidth = asteroidType.getImageWidth();
        int imageHeight = asteroidType.getImageHeight();
        setRect(new RectF(Math.round(positionX - (.5 * imageWidth)),Math.round(positionY - (.5 * imageHeight)),Math.round(positionX + (.5 * imageWidth)),Math.round(positionY + (.5 * imageHeight))));
    }

    public OcteroidAsteroidType getAsteroidType() {
        return asteroidType;
    }

    public void setAsteroidType(OcteroidAsteroidType asteroidType) {
        this.asteroidType = asteroidType;
    }

    @Override
    public void update(double elapsedTime) {
        super.update(elapsedTime);
        int imageWidth = asteroidType.getImageWidth();
        int imageHeight = asteroidType.getImageHeight();
        setRect(new RectF(Math.round(getPositionX() - (.5 * imageWidth)), Math.round(getPositionY() - (.5 * imageHeight)),
                Math.round(getPositionX() + (.5 * imageWidth)), Math.round(getPositionY() + (.5 * imageHeight))));
    }

    @Override
    public void draw() {
        super.draw();
    }
}

