package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is the type definitions for an OcteroidAsteroid from the database.
 */
public class OcteroidAsteroidType extends AsteroidType{

    public OcteroidAsteroidType(JSONObject growingAsteroidJson) throws JSONException {
        super(growingAsteroidJson);
    }

    public OcteroidAsteroidType(String imagePath, int imageWidth, int imageHeight) {
        super(imagePath, imageWidth, imageHeight);
    }

    public String getTypeOfAsteroid() {
        return "octeroid";
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
