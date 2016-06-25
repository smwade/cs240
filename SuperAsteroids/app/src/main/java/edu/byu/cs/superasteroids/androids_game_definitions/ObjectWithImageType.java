package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is the super class for all objects loaded from the database.  Each has a path to the
 * Image, width, and height.
 */
public class ObjectWithImageType extends GeneralDefinitionType {
    protected String imagePath;
    protected int imageWidth;
    protected int imageHeight;

    public ObjectWithImageType(JSONObject objectWithImageJson) throws JSONException{
        imagePath = objectWithImageJson.getString("image");
        imageHeight = objectWithImageJson.getInt("image_height");
        imageWidth = objectWithImageJson.getInt("image_width");
    }

    public ObjectWithImageType(String imagePath, int imageWidth, int imageHeight) {
        this.imagePath = imagePath;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
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
        ObjectWithImageType that = (ObjectWithImageType) o;
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
        return imageWidth * imageHeight * 11;
    }
}
