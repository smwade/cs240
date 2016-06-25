package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.androids_game_definitions.ObjectWithImageType;

/**
 * This class is a generic object for things that move and must be redrawn.  It has their speed,
 * direction, and velocity.
 */
public class MovingObject extends VisibleObject {
    private float speed;
    private float direction;
    private float acceleration;
    private int imageId;
    private ObjectWithImageType type;
    private double xscale;
    private double yscale;

    public MovingObject(int positionX, int positionY, double rotation, float speed, float direction,
                        float acceleration, int imageId, ObjectWithImageType type) {
        super(positionX, positionY, rotation);
        this.speed = speed;
        this.direction = direction;
        this.acceleration = acceleration;
        this.imageId = imageId;
        this.type = type;
        setRect(new RectF(positionX - type.getImageWidth()/2, positionY - type.getImageHeight()/2, positionX + type.getImageWidth()/2, positionY + type.getImageHeight()));
        setCenterPoint(new PointF(positionX, positionY));
    }

    public MovingObject(int positionX, int positionY, double rotation, float speed, float direction,
                        float acceleration, int imageId) {
        super(positionX, positionY, rotation);
        this.speed = speed;
        this.direction = direction;
        this.acceleration = acceleration;
        this.imageId = imageId;
        setRect(new RectF(0,0,0,0));
        setCenterPoint(new PointF(positionX, positionY));
    }

    public MovingObject() {
        super();
        this.speed = 0;
        this.direction = 0;
        this.acceleration = 0;
        this.imageId = -1;
        setRect(new RectF(0, 0, 0, 0));
        setCenterPoint(new PointF(0, 0));
    }


    @Override
    public void setPositionX(int newX) {
        super.setPositionX(newX);
        if (type != null) {
            float rectWidth = getRect().right - getRect().left;
            float rectHeight = getRect().bottom - getRect().top;

            float newWidth = rectWidth * (float) getXscale();
            float newHeight = rectHeight * (float) getYscale();

            float newDiffX = (newWidth - rectWidth) * (float) getXscale() / 2;
            float newDiffY = (newHeight - rectHeight) * (float) getYscale()/ 2;

            setRect(new RectF(newX - newDiffX, getPositionY() - newDiffY, newX + newDiffX, getPositionY() + newDiffY));
            setCenterPoint(new PointF(newX, getPositionY()));
        }
    }

    @Override
    public void setPositionY(int newY) {
        super.setPositionY(newY);
        if (type != null) {
            float rectWidth = getRect().right - getRect().left;
            float rectHeight = getRect().bottom - getRect().top;

            float newWidth = rectWidth * (float) getXscale();
            float newHeight = rectHeight * (float) getYscale();

            float newDiffX = (newWidth - rectWidth) * (float) getXscale()/ 2;
            float newDiffY = (newHeight - rectHeight) * (float) getYscale()/ 2;

            setRect(new RectF(getPositionX() - newDiffX, newY - newDiffY, getPositionX() + newDiffX, newY + newDiffY));
            setCenterPoint(new PointF(getPositionX(), newY));
        }
    }

    public double getXscale() {
        return xscale;
    }

    public void setXscale(double xscale) {
        this.xscale = xscale;
        if (this.getType() != null) {
            this.getType().setImageWidth(Math.round((float) this.getType().getImageWidth() *(float)xscale));
        }

    }

    public double getYscale() {
        return yscale;
    }

    public void setYscale(double yscale) {
        this.yscale = yscale;
        if (this.getType() != null) {
            this.getType().setImageHeight(Math.round((float) this.getType().getImageHeight() *(float)yscale));
        }
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public ObjectWithImageType getType() {
        return type;
    }

    public void setType(ObjectWithImageType type) {
        this.type = type;
    }


}
