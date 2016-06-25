package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is the type definition for the Extra part from the database.
 */
public class ExtraPartType extends ObjectWithImageType {
    private int attachPointX;
    private int attachPointY;

    public ExtraPartType(JSONObject extraPartJson) throws JSONException {
        super(extraPartJson);
        attachPointX = extraPartJson.getInt("attach_point");
        attachPointY = extraPartJson.getInt("attach_point");
    }

    public ExtraPartType(String imagePath, int imageWidth, int imageHeight, int attachPointX,
                         int attachPointY) {
        super(imagePath, imageWidth, imageHeight);
        this.attachPointX = attachPointX;
        this.attachPointY = attachPointY;
    }

    public int getAttachPointX() {
        return attachPointX;
    }

    public void setAttachPointX(int attachPointX) {
        this.attachPointX = attachPointX;
    }

    public int getAttachPointY() {
        return attachPointY;
    }

    public void setAttachPointY(int attachPointY) {
        this.attachPointY = attachPointY;
    }

    @Override
    public boolean equals(Object o) {
        super.equals(o);
        ExtraPartType that = (ExtraPartType) o;

        if (this.getAttachPointX() != that.getAttachPointX()) { return false; }
        if (this.getAttachPointY() != that.getAttachPointY()) { return false; }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + attachPointY;
    }
}
