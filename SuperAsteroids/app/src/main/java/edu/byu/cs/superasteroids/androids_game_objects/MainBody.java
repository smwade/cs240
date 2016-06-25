package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.androids_game_definitions.MainBodyType;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.GC;

/**
 * This is an instance of the Main Body of a ship.
 */
public class MainBody extends ShipParts {
    private MainBodyType mainBodyType;

    public MainBody(int positionX, int positionY, int rotation, float speed, float direction,
                    float acceleration, int imageId, MainBodyType mainBodyType) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
        this.mainBodyType = mainBodyType;
        int imageWidth = mainBodyType.getImageWidth();
        int imageHeight = mainBodyType.getImageHeight();
        setRect(new RectF(Math.round(positionX - (.5 * imageWidth)),Math.round(positionY - (.2 * imageHeight)),
                Math.round(positionX + (.5 * imageWidth)),Math.round(positionY + (.2 * imageHeight))));
    }

    public MainBody(int imageId) {
        super(imageId);
    }

    public MainBodyType getMainBodyType() {
        return mainBodyType;
    }

    public void setMainBodyType(MainBodyType mainBodyType) {
        this.mainBodyType = mainBodyType;
    }
}
