package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The type defintions for a RegularAsteroid from the database.
 */
public class RegularAsteroidType extends AsteroidType {

    public RegularAsteroidType(JSONObject regularAsteroidJson) throws JSONException{
        super(regularAsteroidJson);
    }

    public RegularAsteroidType(String imagePath, int imageWidth, int imageHeight) {
        super(imagePath, imageWidth, imageHeight);
    }

    public String getTypeOfAsteroid() {
        return "regular";
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
