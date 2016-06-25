package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.RectF;

import edu.byu.cs.superasteroids.androids_game_definitions.RegularAsteroidType;

/**
 * This class is a Regular Asteroid you shoot in the game.
 */
public class RegularAsteroid extends Asteroid {

    private RegularAsteroidType asteroidType;

    public RegularAsteroid(int positionX, int positionY, int rotation, float speed, float direction,
                           float acceleration, int damage,
                           RegularAsteroidType asteroidType, int imageId) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
        this.asteroidType = asteroidType;
        int imageWidth = asteroidType.getImageWidth();
        int imageHeight = asteroidType.getImageHeight();
        setRect(new RectF(Math.round(positionX - (.5 * imageWidth)),Math.round(positionY - (.5 * imageHeight)),Math.round(positionX + (.5 * imageWidth)),Math.round(positionY + (.5 * imageHeight))));
    }

    public RegularAsteroidType getAsteroidType() {
        return asteroidType;
    }

    public void setAsteroidType(RegularAsteroidType asteroidType) {
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

