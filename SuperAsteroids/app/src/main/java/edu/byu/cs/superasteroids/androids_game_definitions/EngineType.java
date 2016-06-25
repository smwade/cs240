package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is the type definition for an engine from the database.
 */
public class EngineType extends ObjectWithImageType {
    private int baseSpeed;
    private int baseTurnRate;
    private int attachPointX;
    private int attachPointY;


    public EngineType(String imagePath, int imageWidth, int imageHeight, int baseSpeed,
                      int attachPointX, int attachPointY, int baseTurnRate) {
        super(imagePath, imageWidth, imageHeight);
        this.baseSpeed = baseSpeed;
        this.attachPointX = attachPointX;
        this.attachPointY = attachPointY;
        this.baseTurnRate = baseTurnRate;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getAttachPopintX() {
        return attachPointX;
    }

    public void setAttachPointX(int attachPointX) {
        this.attachPointX = attachPointX;
    }

    public int getAttachPointY() {
        return attachPointY;
    }

    public void setAttachPointY(int attachPointY) {
        this.attachPointY = attachPointY;
    }

    public double getBaseTurnRate() {
        return baseTurnRate;
    }

    public void setBaseTurnRate(int baseTurnRate) {
        this.baseTurnRate = baseTurnRate;
    }

    @Override
    public boolean equals(Object o) {
        super.equals(o);
        EngineType that = (EngineType) o;
        if (this.getBaseSpeed() != that.getBaseSpeed()) {
            return false;
        }
        if (this.getAttachPopintX() != that.getAttachPointY()) {
            return false;
        }
        if (this.getAttachPointY() != that.getAttachPointY()) {
            return false;
        }
        if (this.getBaseTurnRate() != that.getBaseTurnRate()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * attachPointX * attachPointY - baseTurnRate + baseSpeed;
    }


    @Override
    public String toString() {

        String toReturn = imagePath + ", " + imageWidth + ", " + imageHeight + ", " + baseSpeed + ", " +
        attachPointX + ", " + attachPointY + ", " + baseTurnRate;
        return toReturn;
    }
}
