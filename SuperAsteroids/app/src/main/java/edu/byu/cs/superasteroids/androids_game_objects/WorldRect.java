package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by seanwade on 2/26/16.
 */
public class WorldRect {
    static  public RectF rect;
    static public int width;
    static public int height;
    static public Point leftCorner;

    public static void calcRect() {
        rect = new RectF(leftCorner.x, leftCorner.y, leftCorner.x + width, leftCorner.y + height);
    }

    public static Point convertWorldToScreen(Point worldPoint) {
        return new Point(worldPoint.x - ScreenRect.leftCorner.x, worldPoint.y - ScreenRect.leftCorner.y);
    }

    public static PointF convertWorldToScreen(PointF worldPoint) {
        return new PointF(worldPoint.x - ScreenRect.leftCorner.x, worldPoint.y - ScreenRect.leftCorner.y);
    }

    public static int convertXtoScreen(int oldX) {
        return oldX - ScreenRect.leftCorner.x;
    }

    public static int convertYtoScreen(int oldY) {
        return oldY - ScreenRect.leftCorner.y;
    }
}
