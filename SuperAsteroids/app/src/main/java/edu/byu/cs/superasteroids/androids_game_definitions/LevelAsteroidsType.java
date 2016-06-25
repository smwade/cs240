package edu.byu.cs.superasteroids.androids_game_definitions;

/**
 * This is a class for the type defintion of number of asteroids in the Json file.
 */
public class LevelAsteroidsType extends GeneralDefinitionType {
    private int numberOfAsteroids;
    private int asteroidId;
    private int levelNumber;

    public LevelAsteroidsType(int asteroidId,int number, int levelNumber) {
        this.numberOfAsteroids = number;
        this.asteroidId = asteroidId;
        this.levelNumber = levelNumber;
    }

    public int getNumberOfAsteroids() {
        return numberOfAsteroids;
    }

    public void setNumberOfAsteroids(int number) {
        this.numberOfAsteroids = number;
    }

    public int getAsteroidId() {
        return asteroidId;
    }

    public void setAsteroidId(int asteroidId) {
        this.asteroidId = asteroidId;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }

        LevelAsteroidsType that = (LevelAsteroidsType) o;
        if (this.getNumberOfAsteroids() != that.getNumberOfAsteroids()) return false;
        if (this.getAsteroidId() != that.getAsteroidId()) return false;
        if (this.getLevelNumber() != that.getLevelNumber()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return numberOfAsteroids * levelNumber + asteroidId - 5;
    }
}
