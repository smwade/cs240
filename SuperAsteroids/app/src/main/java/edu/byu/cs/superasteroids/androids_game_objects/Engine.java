package edu.byu.cs.superasteroids.androids_game_objects;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.androids_game_definitions.EngineType;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_info.GC;

/**
 * This is an instance of an engine for the game.
 */
public class Engine extends ShipParts{

    private EngineType engineType;

    public Engine(int positionX, int positionY, int rotation, float speed, float direction, float acceleration,
                  int imageId, EngineType engineType) {
        super(positionX, positionY, rotation, speed, direction, acceleration, imageId);
        this.engineType = engineType;
        int imageWidth = engineType.getImageWidth();
        int imageHeight = engineType.getImageHeight();
        setRect(new RectF(Math.round(positionX - (.5 * imageWidth)),Math.round(positionY - (.2 * imageHeight)),
                Math.round(positionX + (.5 * imageWidth)),Math.round(positionY + (.2 * imageHeight))));
    }

    public Engine(int imageId) {
        super(imageId);
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

}
