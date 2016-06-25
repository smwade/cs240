package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is the type definitions for the power core from the database.
 */
public class PowerCoreType extends ObjectWithImageType {

    private int cannonBoost;
    private int engineBoost;

    public PowerCoreType(JSONObject powerCoreJson) throws JSONException {
        super(powerCoreJson);
        cannonBoost = powerCoreJson.getInt("cannon_boost");
        engineBoost = powerCoreJson.getInt("engine_boost");
    }

    public PowerCoreType(String imagePath, int imageWidth, int imageHeight, int cannonBoost,
                         int engineBoost) {
        super(imagePath, imageWidth, imageHeight);
        this.cannonBoost = cannonBoost;
        this.engineBoost = engineBoost;
    }

    public int getCannonBoost() {
        return cannonBoost;
    }

    public void setCannonBoost(int cannonBoost) {
        this.cannonBoost = cannonBoost;
    }

    public int getEngineBoost() {
        return engineBoost;
    }

    public void setEngineBoost(int engineBoost) {
        this.engineBoost = engineBoost;
    }

    @Override
    public boolean equals(Object o) {
        super.equals(o);
        PowerCoreType that = (PowerCoreType) o;

        if (this.getCannonBoost() != that.getCannonBoost()) { return false; }
        if (this.getEngineBoost() != that.getEngineBoost()) { return false; }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * cannonBoost;
    }
}
