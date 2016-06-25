package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.GC;

/**
 * This is the general class for what it means to be a part for a ship.
 */
public class ShipParts extends MovingObject{
    public ShipParts(int positionX, int positionY, double rotation, float speed, float direction,
            float acceleration, int imageId) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
    }

    public ShipParts(int imageId) {
        this.setImageId(imageId);
    }

    public void update(int xPos, int yPos, double rotation, float speed) {
        setPositionX(xPos);
        setPositionY(yPos);
        setRotation(rotation);
        setSpeed(speed);
        setCenterPoint(new PointF(xPos, yPos));
        getRect();
    }

    @Override
    public void draw() {

        DrawingHelper.drawImage(getImageId(), getPositionX() - ScreenRect.leftCorner.x, getPositionY() - ScreenRect.leftCorner.y, (float) getRotation(),
                GC.XSCALE, GC.YSCALE, GC.OPAQUE);


    }
}
