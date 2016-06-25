package edu.byu.cs.superasteroids.androids_game_objects;

import edu.byu.cs.superasteroids.androids_game_definitions.BgObjectType;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.GC;

/**
 * An instance of a background object
 */
public class BgObject extends VisibleObject {
    private BgObjectType bgType;
    private int imageId;

    private double xScale;
    private double yScale;

    public BgObject(int positionX, int positionY, int rotation, BgObjectType bgType, int imageId) {
        super(positionX, positionY, rotation);
        this.bgType = bgType;
        this.imageId = imageId;
        this.xScale = 1;
        this.yScale = 1;
    }

    public BgObjectType getBgType() {
        return bgType;
    }

    public void setBgType(BgObjectType bgType) {
        this.bgType = bgType;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public double getxScale() {
        return xScale;
    }

    public void setxScale(double xScale) {
        this.xScale = xScale;
    }

    public double getyScale() {
        return yScale;
    }

    public void setyScale(double yScale) {
        this.yScale = yScale;
    }

    public void draw() {
        DrawingHelper.drawImage(
                imageId, WorldRect.convertXtoScreen(getPositionX()), WorldRect.convertYtoScreen(getPositionY())
                , (float) getRotation(), (float) xScale, (float) yScale, GC.OPAQUE);
    }
}
