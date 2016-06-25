package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by seanwade on 2/26/16.
 */
public class ScreenRect {
    static  public RectF rect;
    static public int width;
    static public int height;
    static public Point leftCorner;
    static public Point centerPoint;


    public static void calcRect() {
        rect = new RectF(leftCorner.x, leftCorner.y, leftCorner.x + width, leftCorner.y + height);
    }

    public static void calcCenter() {
        centerPoint = new Point(Math.round(leftCorner.x + width / 2), Math.round(leftCorner.y + height / 2));
    }

    public static void calcLeftPoint() {
        leftCorner = new Point(Math.round(centerPoint.x - width / 2), Math.round(centerPoint.y - height / 2));
    }

    public static Point convertScreentoWorld(Point screenPoint) {
        return new Point(leftCorner.x, screenPoint.y + leftCorner.y);
    }
}
