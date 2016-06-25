package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is the type definitions for the Main Body from the database.
 */
public class MainBodyType extends ObjectWithImageType {

    private int cannonAttachX;
    private int cannonAttachY;
    private int engineAttachX;
    private int engineAttachY;
    private int extraAttachX;
    private int extraAttachY;

    public MainBodyType(JSONObject Json) throws JSONException {
        super(Json);
        cannonAttachX = Json.getInt("cannon_attach");
        cannonAttachY = Json.getInt("cannon_attach");
        engineAttachX = Json.getInt("engine_attach");
        engineAttachY = Json.getInt("engine_attach");
        extraAttachX = Json.getInt("extra_attach");
        extraAttachY = Json.getInt("extra_attach");

    }

    public MainBodyType(String imagePath, int imageWidth, int imageHeight, int cannonAttachX,
                        int cannonAttachY, int engineAttachX, int engineAttachY, int extraAttachX,
                        int extraAttachY) {
        super(imagePath, imageWidth, imageHeight);
        this.cannonAttachX = cannonAttachX;
        this.cannonAttachY = cannonAttachY;
        this.engineAttachX = engineAttachX;
        this.engineAttachY = engineAttachY;
        this.extraAttachX = extraAttachX;
        this.extraAttachY = extraAttachY;
    }

    public int getCannonAttachX() {
        return cannonAttachX;
    }

    public void setCannonAttachX(int cannonAttachX) {
        this.cannonAttachX = cannonAttachX;
    }

    public int getCannonAttachY() {
        return cannonAttachY;
    }

    public void setCannonAttachY(int cannonAttachY) {
        this.cannonAttachY = cannonAttachY;
    }

    public int getEngineAttachX() {
        return engineAttachX;
    }

    public void setEngineAttachX(int engineAttachX) {
        this.engineAttachX = engineAttachX;
    }

    public int getEngineAttachY() {
        return engineAttachY;
    }

    public void setEngineAttachY(int engineAttachY) {
        this.engineAttachY = engineAttachY;
    }

    public int getExtraAttachX() {
        return extraAttachX;
    }

    public void setExtraAttachX(int extraAttachX) {
        this.extraAttachX = extraAttachX;
    }

    public int getExtraAttachY() {
        return extraAttachY;
    }

    public void setExtraAttachY(int extraAttachY) {
        this.extraAttachY = extraAttachY;
    }

    @Override
    public boolean equals(Object o) {
        super.equals(o);
        MainBodyType that = (MainBodyType) o;

        if (this.getCannonAttachX() != that.getCannonAttachX()) { return false; }
        if (this.getCannonAttachY() != that.getCannonAttachY()) { return false; }
        if (this.getEngineAttachX() != that.getEngineAttachX()) { return false; }
        if (this.getEngineAttachY() != that.getEngineAttachY()) { return false; }
        if (this.getExtraAttachX() != that.getExtraAttachX()) { return false; }
        if (this.getExtraAttachY() != that.getExtraAttachY()) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * extraAttachX;
    }
}
