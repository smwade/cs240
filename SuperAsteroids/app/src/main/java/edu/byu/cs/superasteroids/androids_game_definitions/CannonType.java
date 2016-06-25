package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is the definition for a cannon from the database.
 */
public class CannonType extends ObjectWithImageType {
    private int attachPointX;
    private int attachPointY;
    private int emitPointX;
    private int emitPointY;
    private String attackImage;
    private int attackImageWidth;
    private int attackImageHeight;
    private String attackSound;
    private int damage;



    public CannonType(JSONObject cannonJson) throws JSONException {
        super(cannonJson);
        attachPointX = cannonJson.getInt("attach_point_x");
        attachPointY = cannonJson.getInt("attach_point_y");
        emitPointX = cannonJson.getInt("emit_point_x");
        emitPointY = cannonJson.getInt("emit_point_y");
        attackImage = cannonJson.getString("attack_image_path");
        attackImageWidth = cannonJson.getInt("attack_image_width");
        attackImageHeight = cannonJson.getInt("attack_image_height");
        attackSound = cannonJson.getString("attack_sound_path");
        damage = cannonJson.getInt("damage");
    }

    public CannonType(String imagePath, int imageWidth, int imageHeight, int attachPointX,
                      int attachPointY, int emitPointX, int emitPointY, String attackImage,
                      int attackImageWidth, int attackImageHeight, String attackSound, int damage){
        super(imagePath, imageWidth, imageHeight);
        this.attachPointX = attachPointX;
        this.attachPointY = attachPointY;
        this.emitPointX = emitPointX;
        this.emitPointY = emitPointY;
        this.attackImage = attackImage;
        this.attackImageWidth = attackImageWidth;
        this.attackImageHeight = attackImageHeight;
        this.attackSound = attackSound;
        this.damage = damage;
    }

    @Override
    public boolean equals(Object o) {
        super.equals(o);
        CannonType that = (CannonType) o;

        if (this.getAttachPointX() != that.getAttachPointX()) return false;
        if (this.getAttachPointY() != that.getAttachPointY()) return false;
        if (this.getEmitPointX() != that.getEmitPointX()) return false;
        if (this.getEmitPointY() != that.getEmitPointY()) return false;
        if (!this.getAttackImage().equals(that.getAttackImage())) return false;
        if (this.getAttackImageWidth() != that.getAttackImageWidth()) return false;
        if (this.getAttackImageHeight() != that.getAttackImageHeight()) return false;
        if (!this.getAttackSound().equals(that.getAttackSound())) return false;
        if (this.getDamage() != that.getDamage()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + attackImageWidth * imageHeight * 21;
    }

    public int getAttachPointX() { return attachPointX; }

    public void setAttachPointX(int attachPointX) {
        this.attachPointX = attachPointX;
    }

    public int getAttachPointY() {
        return attachPointY;
    }

    public void setAttachPointY(int attachPointY) {
        this.attachPointY = attachPointY;
    }

    public int getEmitPointX() {
        return emitPointX;
    }

    public void setEmitPointX(int emitPointX) {
        this.emitPointX = emitPointX;
    }

    public int getEmitPointY() {
        return emitPointY;
    }

    public void setEmitPointY(int emitPointY) {
        this.emitPointY = emitPointY;
    }

    public String getAttackImage() {
        return attackImage;
    }

    public void setAttackImage(String attackImage) {
        this.attackImage = attackImage;
    }

    public int getAttackImageWidth() {
        return attackImageWidth;
    }

    public void setAttackImageWidth(int attackImageWidth) {this.attackImageWidth = attackImageWidth;}

    public int getAttackImageHeight() {
        return attackImageHeight;
    }

    public void setAttackImageHeight(int attackImageHeight) {
        this.attackImageHeight = attackImageHeight;
    }

    public String getAttackSound() {
        return attackSound;
    }

    public void setAttackSound(String attackSound) {
        this.attackSound = attackSound;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}
