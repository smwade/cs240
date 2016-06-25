package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.androids_game_definitions.PowerCoreType;

/**
 * This is an instance of the power core of a ship.
 */
public class PowerCore extends ShipParts {
    private PowerCoreType powerCoreType;

    public PowerCore(int positionX, int positionY, int rotation, float speed, float direction,
                     float acceleration, int imageId, PowerCoreType powerCoreType) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
        this.powerCoreType = powerCoreType;
        int imageWidth = powerCoreType.getImageWidth();
        int imageHeight = powerCoreType.getImageHeight();
        setRect(new RectF(Math.round(positionX - (.5 * imageWidth)),Math.round(positionY - (.2 * imageHeight)),
                Math.round(positionX + (.5 * imageWidth)),Math.round(positionY + (.2 * imageHeight))));
    }

    public PowerCore(int imageId) {
        super(imageId);
    }

    public PowerCoreType getPowerCoreType() {
        return powerCoreType;
    }

    public void setPowerCoreType(PowerCoreType powerCoreType) {
        this.powerCoreType = powerCoreType;
    }
}
