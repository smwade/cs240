package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.androids_game_definitions.ExtraPartType;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.GC;

/**
 * This is an instance of the extra part of a ship
 */
public class ExtraPart extends ShipParts {

    private ExtraPartType extraPartType;

    public ExtraPart(int positionX, int positionY, int rotation, float speed, float direction,
                     float acceleration, int imageId, ExtraPartType extraPartType) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
        this.extraPartType = extraPartType;
        int imageWidth = extraPartType.getImageWidth();
        int imageHeight = extraPartType.getImageHeight();
        setRect(new RectF(Math.round(positionX - (.5 * imageWidth)),Math.round(positionY - (.2 * imageHeight)),
                Math.round(positionX + (.5 * imageWidth)),Math.round(positionY + (.2 * imageHeight))));
    }

    public ExtraPart(int imageId) {
        super(imageId);
    }

    public ExtraPartType getExtraPartType() {
        return extraPartType;
    }

    public void setExtraPartType(ExtraPartType extraPartType) {
        this.extraPartType = extraPartType;
    }
}
