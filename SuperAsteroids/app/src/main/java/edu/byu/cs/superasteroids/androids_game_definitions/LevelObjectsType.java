package edu.byu.cs.superasteroids.androids_game_definitions;

/**
 * This class is the format for the levelObjects in the Json file.
 */
public class LevelObjectsType extends GeneralDefinitionType {
    private int positionX;
    private int positionY;
    private int objectId;
    private double scale;

    public LevelObjectsType(int positionX, int positionY, int objectId, double scale) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.objectId = objectId;
        this.scale = scale;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
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

        LevelObjectsType that = (LevelObjectsType) o;
        if (this.getPositionX() != that.getPositionX()) return false;
        if (this.getPositionY() != that.getPositionY()) return false;
        if (this.getObjectId() != that.getObjectId()) return false;
        if (this.getScale() != that.getScale()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return positionX * objectId + 5 + positionY;
    }
}
