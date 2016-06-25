package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by seanwade on 2/26/16.
 */
public class RectConverter {

    public static Rect makeRect(int xPos, int yPos, int imageWidth, int imageHeight) {
        int left = (int) Math.round(xPos - .5 * imageWidth);
        int right = (int) Math.round(xPos + .5 * imageWidth);
        int top = (int) Math.round(yPos - .5 * imageHeight);
        int bottom = (int) Math.round(yPos + .5 * imageHeight);
        return new Rect(left, top, right, bottom);
    }

    public static Rect makeRect(Point point, int imageWidth, int imageHeight) {
        int left = (int) Math.round(point.x - .5 * imageWidth);
        int right = (int) Math.round(point.x + .5 * imageWidth);
        int top = (int) Math.round(point.y - .5 * imageHeight);
        int bottom = (int) Math.round(point.y + .5 * imageHeight);
        return new Rect(left, top, right, bottom);
    }
}
