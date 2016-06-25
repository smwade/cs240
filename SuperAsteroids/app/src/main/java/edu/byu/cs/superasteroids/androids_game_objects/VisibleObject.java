package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * This class is a visible object on the screen.
 */
public class VisibleObject {

    private int positionX;
    private int positionY;
    private PointF centerPoint;
    private double rotation;
    private RectF rect;

    public VisibleObject(int positionX, int positionY, double rotation) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.rotation = rotation;
        this.centerPoint = new PointF(positionX, positionY);
    }

    public VisibleObject() {
        this.positionX = 0;
        this.positionY = 0;
        this.rotation = 0;
        this.centerPoint = new PointF(positionX, positionY);
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
        this.centerPoint.x = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
        this.centerPoint.y = positionY;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public PointF getCenterPoint() {
        centerPoint.x = positionX;
        centerPoint.y = positionY;
        return centerPoint;
    }

    public void setCenterPoint(PointF centerPoint) {
        this.centerPoint = centerPoint;
        this.positionX = (int) centerPoint.x;
        this.positionY = (int) centerPoint.y;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    /** Updates the location and look of a visible object.*/
    public void update(){}

    /** Draws a visible object in its new state.*/
    public void draw(){}
}
