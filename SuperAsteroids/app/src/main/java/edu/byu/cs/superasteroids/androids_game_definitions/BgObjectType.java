package edu.byu.cs.superasteroids.androids_game_definitions;

/**
 * The type for a background image loaded from the database;
 */
public class BgObjectType extends ObjectWithImageType {

    public BgObjectType(String imagePath, int imageWidth, int imageHeight) {
        super(imagePath, -1, -1);
    }
}
