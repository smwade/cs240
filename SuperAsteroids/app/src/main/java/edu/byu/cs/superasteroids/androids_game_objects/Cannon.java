package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.androids_game_definitions.CannonType;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.GC;

/**
 * This is an instance of a cannon.
 */
public class Cannon extends ShipParts {
    private CannonType cannonType;

    public Cannon(int positionX, int positionY, int rotation, float speed, float direction, float acceleration,
                  int imageId, CannonType cannonType) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
        this.cannonType = cannonType;
        int imageWidth = cannonType.getImageWidth();
        int imageHeight = cannonType.getImageHeight();
        setRect(new RectF(Math.round(positionX - (.5 * imageWidth)),Math.round(positionY - (.2 * imageHeight)),
                Math.round(positionX + (.5 * imageWidth)),Math.round(positionY + (.2 * imageHeight))));
    }

    public Cannon(int imageId) {
        super(imageId);
    }

    public CannonType getCannonType() {
        return cannonType;
    }

    public void setCannonType(CannonType cannonType) {
        this.cannonType = cannonType;
    }

}
