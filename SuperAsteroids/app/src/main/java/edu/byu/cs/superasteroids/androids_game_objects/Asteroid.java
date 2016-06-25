package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.GC;

/**
 * This class is the general parent class for a asteroid.
 */
public class Asteroid extends MovingObject {

    private boolean hit;

    public Asteroid(int positionX, int positionY, int rotation, float speed, float direction, float acceleration,
                    int imageId) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
        hit = false;


    }

    public void update(double elapsedTime) {
        GraphicsUtils.MoveObjectResult result =
                GraphicsUtils.moveObject(getCenterPoint(), getRect(), getSpeed(), getDirection(), elapsedTime);
        setCenterPoint(result.getNewObjPosition());
        //setRect(result.getNewObjBounds());

    }

    public void draw() {
        DrawingHelper.drawImage(getImageId(), WorldRect.convertXtoScreen((int) getCenterPoint().x),
                WorldRect.convertYtoScreen((int) getCenterPoint().y), 0, (float) getXscale(), (float) getYscale(), GC.OPAQUE);
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

}
