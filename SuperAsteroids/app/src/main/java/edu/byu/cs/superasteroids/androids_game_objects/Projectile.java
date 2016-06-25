package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import edu.byu.cs.superasteroids.androids_game_definitions.CannonType;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.GC;

/**
 * This is an instance of a projectile.
 */
public class Projectile extends MovingObject {

    private String projImage;
    private int projHeight;
    private int projWidth;
    private String projSound;

    public Projectile(int positionX, int positionY, double rotation, float speed, float direction,
                      float acceleration, CannonType cannonType, int imageId) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
        projImage = cannonType.getAttackImage();
        projHeight = cannonType.getAttackImageHeight();
        projWidth = cannonType.getAttackImageWidth();
        projSound = cannonType.getAttackSound();
        setRect(new RectF(Math.round(positionX - (.5 * projWidth)),Math.round(positionY - (.2 * projHeight)),Math.round(positionX + (.5 * projWidth)),Math.round(positionY + (.2 * projHeight))));
    }

    public void update(double elapsedTime) {
        GraphicsUtils.MoveObjectResult result =
                GraphicsUtils.moveObject(getCenterPoint(), getRect(), getSpeed(), getDirection(), elapsedTime);
        setCenterPoint(result.getNewObjPosition());
        setRect(result.getNewObjBounds());
    }

    @Override
    public void draw() {
        DrawingHelper.drawImage(getImageId(), WorldRect.convertXtoScreen(getPositionX()), WorldRect.convertYtoScreen(getPositionY()),
                (float) GraphicsUtils.radiansToDegrees(getRotation() + Math.PI/2),
                GC.XSCALE, GC.YSCALE, GC.OPAQUE);
    }
}
