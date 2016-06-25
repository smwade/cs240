package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is the type definition for a GrowingAsteroid from the dsatabase.
 */
public class GrowingAsteroidType extends AsteroidType{

    public GrowingAsteroidType(JSONObject growingAsteroidJson) throws JSONException{
        super(growingAsteroidJson);
    }

    public GrowingAsteroidType(String imagePath, int imageWidth, int imageHeight) {
        super(imagePath, imageWidth, imageHeight);
    }

    public String getTypeOfAsteroid() {
        return "growing";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
