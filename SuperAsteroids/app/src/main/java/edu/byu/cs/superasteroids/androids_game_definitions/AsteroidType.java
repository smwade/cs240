package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is the general type for the asteroid class from the database.
 */
public class AsteroidType extends ObjectWithImageType {
    private String type;

    public AsteroidType(JSONObject growingAsteroidJson) throws JSONException {
        super(growingAsteroidJson);
    }

    public AsteroidType(String imagePath, int imageWidth, int imageHeight) {
        super(imagePath, imageWidth, imageHeight);
    }

    public AsteroidType(String imagePath, int imageWidth, int imageHeight, String type) {
        super(imagePath, imageWidth, imageHeight);
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        AsteroidType that = (AsteroidType) o;
        if (!this.getImagePath().equals(that.getImagePath())) {
            return false;
        }
        if (this.getImageWidth() != that.getImageWidth()) {
            return false;
        }
        if (this.getImageHeight() != that.getImageHeight()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return imageHeight + imageWidth * 17;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeOfAsteroid() {
        return type;
    }

}
