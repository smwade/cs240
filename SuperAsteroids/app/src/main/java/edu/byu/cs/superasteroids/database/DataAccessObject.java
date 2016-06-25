package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.superasteroids.androids_game_definitions.*;
import edu.byu.cs.superasteroids.androids_game_objects.Asteroid;
import edu.byu.cs.superasteroids.androids_game_objects.BgObject;
import edu.byu.cs.superasteroids.game_info.AsteroidsProgram;

/**
 * The data access object for the database.  Implements the access for every type of object.
 * This is made up of Create, Read, and, Delete for every type object.
 */
public class DataAccessObject {

    private SQLiteDatabase db;

    public DataAccessObject(SQLiteDatabase db) {
        this.db = db;
    }

    
    // Create
    //---------------------------
    public boolean createBgObject(BgObjectType bgObject) {
        try {
            ContentValues values = new ContentValues();
            values.put("file_path", bgObject.getImagePath());
            db.insert("bg_objects", null, values);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean createAsteroid(AsteroidType asteroidType) {
        try {
            ContentValues values = new ContentValues();
            values.put("image_path", asteroidType.getImagePath());
            values.put("image_width", asteroidType.getImageWidth());
            values.put("image_height", asteroidType.getImageHeight());
            values.put("asteroid_name", asteroidType.getTypeOfAsteroid());
            db.insert("asteroids", null, values);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean createCannon(CannonType cannonType) {
        try {
            ContentValues values = new ContentValues();
            values.put("attach_point_x", cannonType.getAttachPointX());
            values.put("attach_point_y", cannonType.getAttachPointY());
            values.put("emit_point_x", cannonType.getEmitPointX());
            values.put("emit_point_y", cannonType.getEmitPointY());
            values.put("image_path", cannonType.getImagePath());
            values.put("image_height", cannonType.getImageHeight());
            values.put("image_width", cannonType.getImageWidth());
            values.put("attack_image_path", cannonType.getAttackImage());
            values.put("attack_image_height", cannonType.getAttackImageHeight());
            values.put("attack_image_width", cannonType.getAttackImageWidth());
            values.put("attack_sound_path", cannonType.getAttackSound());
            values.put("damage", cannonType.getDamage());

            db.insert("cannons", null, values);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean createEngine(EngineType engineType) {
        try {
            ContentValues values = new ContentValues();
            values.put("base_speed", engineType.getBaseSpeed());
            values.put("base_turn_rate", engineType.getBaseTurnRate());
            values.put("attach_point_x", engineType.getAttachPopintX());
            values.put("attach_point_y", engineType.getAttachPointY());
            values.put("image_path", engineType.getImagePath());
            values.put("image_height", engineType.getImageHeight());
            values.put("image_width", engineType.getImageWidth());

            db.insert("engines", null, values);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean createMainBody(MainBodyType mainBodyType) {
        try {
            ContentValues values = new ContentValues();
            values.put("cannon_attach_x", mainBodyType.getCannonAttachX());
            values.put("cannon_attach_y", mainBodyType.getCannonAttachY());
            values.put("engine_attach_x", mainBodyType.getEngineAttachX());
            values.put("engine_attach_y", mainBodyType.getEngineAttachY());
            values.put("extra_attach_x", mainBodyType.getExtraAttachX());
            values.put("extra_attach_y", mainBodyType.getExtraAttachY());
            values.put("image_path", mainBodyType.getImagePath());
            values.put("image_height", mainBodyType.getImageHeight());
            values.put("image_width", mainBodyType.getImageWidth());

            db.insert("main_bodies", null, values);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean createExtraPart(ExtraPartType extraPartType) {
        try {
            ContentValues values = new ContentValues();
            values.put("attach_point_x", extraPartType.getAttachPointX());
            values.put("attach_point_y", extraPartType.getAttachPointY());
            values.put("image_path", extraPartType.getImagePath());
            values.put("image_height", extraPartType.getImageHeight());
            values.put("image_width", extraPartType.getImageWidth());

            db.insert("extra_parts", null, values);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean createPowerCore(PowerCoreType powerCoreType) {
            ContentValues values = new ContentValues();
            values.put("cannon_boost", powerCoreType.getCannonBoost());
            values.put("engine_boost", powerCoreType.getEngineBoost());
            values.put("image_path", powerCoreType.getImagePath());

            db.insert("power_cores", null, values);
            return true;
    }

    public boolean createLevel(LevelType levelType) {
        try {
            ContentValues levels_values = new ContentValues();
            ContentValues level_objects_values = new ContentValues();
            ContentValues level_asteroids_values = new ContentValues();

            // levels table
            levels_values.put("level_number", levelType.getLevel_number());
            levels_values.put("title", levelType.getTitle());
            levels_values.put("hint", levelType.getHint());
            levels_values.put("width", levelType.getWidth());
            levels_values.put("height", levelType.getHeight());
            levels_values.put("music_path", levelType.getMusicPath());
            //  level_objects
            List<LevelObjectsType> levelObjects= levelType.getLevelBgObjects();
            for (LevelObjectsType levelObject: levelObjects) {
                level_objects_values.put("object_id", levelObject.getObjectId());
                level_objects_values.put("position_x", levelObject.getPositionX());
                level_objects_values.put("position_y", levelObject.getPositionY());
                level_objects_values.put("scale", levelObject.getScale());
                level_objects_values.put("level_number", levelType.getLevel_number());
                db.insert("level_objects", null, level_objects_values);
                level_objects_values.clear();
            }
            // level_asteroids
            List<LevelAsteroidsType> levelAsteroids = levelType.getLevelAsteroids();
            for (LevelAsteroidsType levelAstreroid : levelAsteroids) {
                level_asteroids_values.put("asteroid_id", levelAstreroid.getAsteroidId());
                level_asteroids_values.put("number", levelAstreroid.getNumberOfAsteroids());
                level_asteroids_values.put("level_number", levelAstreroid.getLevelNumber());
                db.insert("level_asteroids", null, level_asteroids_values);
                level_asteroids_values.clear();
            }
            db.insert("levels", null, levels_values);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    
    // Read
    //---------------------------
    public List<CannonType> readCannon() {
        List<CannonType> cannonTypeList = new ArrayList<>();
        Cursor c = db.rawQuery(SELECT_ALL_CANNONS, null);
        int attachPointXCol = c.getColumnIndex("attach_point_x");
        int attachPointYCol = c.getColumnIndex("attach_point_y");
        int emitPointXCol = c.getColumnIndex("emit_point_x");
        int emitPointYCol = c.getColumnIndex("emit_point_y");
        int imagePathCol = c.getColumnIndex("image_path");
        int imageWidthCol = c.getColumnIndex("image_width");
        int imageHeightCol = c.getColumnIndex("image_height");
        int attackImagePathCol = c.getColumnIndex("attack_image_path");
        int attackImageWidthCol = c.getColumnIndex("attack_image_width");
        int attackImageHeightCol = c.getColumnIndex("attack_image_height");
        int attackSoundPathCol = c.getColumnIndex("attack_sound_path");
        int damageCol = c.getColumnIndex("damage");

        if (c != null) {
            if (c.moveToFirst()){
                do {
                    String imagePath = c.getString(imagePathCol);
                    int attachPointX = c.getInt(attachPointXCol);
                    int attachPointY = c.getInt(attachPointYCol);
                    int imageWidth = c.getInt(imageWidthCol);
                    int imageHeight = c.getInt(imageHeightCol);
                    int emitPointX = c.getInt(emitPointXCol);
                    int emitPointY = c.getInt(emitPointYCol);
                    String attackImagePath = c.getString(attackImagePathCol);
                    int attackImageWidth = c.getInt(attackImageWidthCol);
                    int attackImageHeight = c.getInt(attackImageHeightCol);
                    String attackSoundPath = c.getString(attackSoundPathCol);
                    int damage = c.getInt(damageCol);
                    cannonTypeList.add(new CannonType(imagePath, imageWidth, imageHeight,
                            attachPointX, attachPointY, emitPointX, emitPointY, attackImagePath,
                            attackImageWidth, attackImageHeight, attackSoundPath, damage));
                } while (c.moveToNext());
            }
        }
        return cannonTypeList;
    }

    public List<EngineType> readEngine() {
        List<EngineType> engineTypeList = new ArrayList<>();
        Cursor c = db.rawQuery(SELECT_ALL_ENGINES, null);
        int baseSpeedCol = c.getColumnIndex("base_speed");
        int baseTurnRateCol = c.getColumnIndex("base_turn_rate");
        int attachPointXCol = c.getColumnIndex("attach_point_x");
        int attachPointYCol = c.getColumnIndex("attach_point_y");
        int imagePathCol = c.getColumnIndex("image_path");
        int imageWidthCol = c.getColumnIndex("image_width");
        int imageHeightCol = c.getColumnIndex("image_height");
        if (c != null) {
            if (c.moveToFirst()){
                do {
                    String imagePath = c.getString(imagePathCol);
                    int attachPointX = c.getInt(attachPointXCol);
                    int attachPointY = c.getInt(attachPointYCol);
                    int imageWidth = c.getInt(imageWidthCol);
                    int imageHeight = c.getInt(imageHeightCol);
                    int baseSpeed = c.getInt(baseSpeedCol);
                    int baseTurnRate = c.getInt(baseTurnRateCol);
                    engineTypeList.add(new EngineType(imagePath, imageWidth, imageHeight,
                            baseSpeed, attachPointX, attachPointY, baseTurnRate));
                } while (c.moveToNext());
            }
        }
        return engineTypeList;
    }

    public List<MainBodyType> readMainBody() {
        List<MainBodyType> mainBodyTypeList = new ArrayList<>();
        Cursor c = db.rawQuery(SELECT_ALL_MAIN_BODIES, null);
        int cannonAttachXCol = c.getColumnIndex("cannon_attach_x");
        int cannonAttachYCol = c.getColumnIndex("cannon_attach_y");
        int engineAttachXCol = c.getColumnIndex("engine_attach_x");
        int engineAttachYCol = c.getColumnIndex("engine_attach_y");
        int extraAttachXCol = c.getColumnIndex("extra_attach_x");
        int extraAttachYCol = c.getColumnIndex("extra_attach_y");
        int imagePathCol = c.getColumnIndex("image_path");
        int imageWidthCol = c.getColumnIndex("image_width");
        int imageHeightCol = c.getColumnIndex("image_height");
        if (c != null) {
            if (c.moveToFirst()){
                do {
                    String imagePath = c.getString(imagePathCol);
                    int cannonAttachX = c.getInt(cannonAttachXCol);
                    int cannonAttachY = c.getInt(cannonAttachYCol);
                    int engineAttachX = c.getInt(engineAttachXCol);
                    int engineAttachY = c.getInt(engineAttachYCol);
                    int extraAttachX = c.getInt(extraAttachXCol);
                    int extraAttachY = c.getInt(extraAttachYCol);
                    int imageWidth = c.getInt(imageWidthCol);
                    int imageHeight = c.getInt(imageHeightCol);
                    mainBodyTypeList.add(new MainBodyType(
                            imagePath, imageWidth, imageHeight, cannonAttachX, cannonAttachY,
                            engineAttachX, engineAttachY, extraAttachX, extraAttachY));
                } while (c.moveToNext());
            }
        }
        return mainBodyTypeList;
    }

    public List<ExtraPartType> readExtraPart() {
        List<ExtraPartType> extraPartTypeList = new ArrayList<>();
        Cursor c = db.rawQuery(SELECT_ALL_EXTRA_PARTS, null);
        int attachPointXCol = c.getColumnIndex("attach_point_x");
        int attachPointYCol = c.getColumnIndex("attach_point_y");
        int imagePathCol = c.getColumnIndex("image_path");
        int imageWidthCol = c.getColumnIndex("image_width");
        int imageHeightCol = c.getColumnIndex("image_height");
        if (c != null) {
            if (c.moveToFirst()){
                do {
                    String imagePath = c.getString(imagePathCol);
                    int attachPointX = c.getInt(attachPointXCol);
                    int attachPointY = c.getInt(attachPointYCol);
                    int imageWidth = c.getInt(imageWidthCol);
                    int imageHeight = c.getInt(imageHeightCol);
                    extraPartTypeList.add(new ExtraPartType(
                            imagePath, imageWidth, imageHeight, attachPointX, attachPointY));
                } while (c.moveToNext());
            }
        }
        return extraPartTypeList;
    }

    public List<PowerCoreType> readPowerCore() {
        List<PowerCoreType> powerCoreTypeList = new ArrayList<>();
        Cursor c = db.rawQuery(SELECT_ALL_POWER_CORES, null);
        int cannonBoostCol = c.getColumnIndex("cannon_boost");
        int engineBoostCol = c.getColumnIndex("engine_boost");
        int imagePathCol = c.getColumnIndex("image_path");
        if (c != null) {
            if (c.moveToFirst()){
                do {
                    String imagePath = c.getString(imagePathCol);
                    int cannonBoost = c.getInt(cannonBoostCol);
                    int engineBoost = c.getInt(engineBoostCol);
                    powerCoreTypeList.add(new PowerCoreType(imagePath, -1, -1, cannonBoost, engineBoost));
                } while (c.moveToNext());
            }
        }
        return powerCoreTypeList;
    }

    public Map<Integer, LevelType> readLevel() {
        AsteroidsProgram asteroidsProgram = AsteroidsProgram.getInstance();
        Map<Integer, LevelType> levelTypeMap = new HashMap<>();
        Cursor c = db.rawQuery(SELECT_ALL_LEVELS, null);
        int levelNumberCol = c.getColumnIndex("level_number");
        int titleCol = c.getColumnIndex("title");
        int hintCol = c.getColumnIndex("hint");
        int widthCol = c.getColumnIndex("width");
        int heightCol = c.getColumnIndex("height");
        int musicPathCol = c.getColumnIndex("music_path");

        // Iteration through the level table
        if (c != null) {
            if (c.moveToFirst()){
                do {
                    Integer levelNumber = c.getInt(levelNumberCol);
                    String title = c.getString(titleCol);
                    String hint = c.getString(hintCol);
                    int width = c.getInt(widthCol);
                    int height = c.getInt(heightCol);
                    String musicPath = c.getString(musicPathCol);
                    levelTypeMap.put(levelNumber, new LevelType(levelNumber, title, hint, width, height,
                            musicPath, new ArrayList<LevelObjectsType>(), new ArrayList<LevelAsteroidsType>()));
                } while (c.moveToNext());
            }
        }
        //Iteration through the level_objects table
        c = db.rawQuery(SELECT_ALL_LEVEL_OBJECTS, null);
        levelNumberCol = c.getColumnIndex("level_number");
        int objectIdCol = c.getColumnIndex("object_id");
        int positionXCol = c.getColumnIndex("position_x");
        int positionYCol = c.getColumnIndex("position_y");
        int scaleCol = c.getColumnIndex("scale");

        if (c != null) {
            if (c.moveToFirst()){
                do {
                    Integer levelNumber = c.getInt(levelNumberCol);
                    int objectId = c.getInt(objectIdCol);
                    int positionX = c.getInt(positionXCol);
                    int positionY = c.getInt(positionYCol);
                    double scale = c.getDouble(scaleCol);
                    levelTypeMap.get(levelNumber).getLevelBgObjects()
                            .add(new LevelObjectsType(positionX, positionY, objectId, scale));
                } while (c.moveToNext());
            }
        }

        c = db.rawQuery(SELECT_ALL_LEVEL_ASTEROIDS, null);
        int asteroidIdCol = c.getColumnIndex("asteroid_id");
        int numberOfAsteroidsCol = c.getColumnIndex("number");
        levelNumberCol = c.getColumnIndex("level_number");

        if (c != null) {
            if (c.moveToFirst()){
                do {
                    int asteroidId = c.getInt(asteroidIdCol);
                    int numberOfAsteroids = c.getInt(numberOfAsteroidsCol);
                    int levelNumber = c.getInt(levelNumberCol);
                    levelTypeMap.get(levelNumber).getLevelAsteroids()
                            .add(new LevelAsteroidsType(asteroidId, numberOfAsteroids, levelNumber));
                } while (c.moveToNext());
            }
        }

        // Debug Check
        for (Map.Entry<Integer, LevelType> entry: levelTypeMap.entrySet()) {
            Log.d("Level Check",
                    "Level_number: " + String.valueOf(entry.getValue().getLevel_number()) +
                            "\ntitle: " + entry.getValue().getTitle() +
                            "\nhint: " + entry.getValue().getHint() +
                            "\nwidth: " + String.valueOf(entry.getValue().getWidth()) +
                            "\nheight: " + String.valueOf(entry.getValue().getHeight()));
            for (LevelObjectsType bgObject : entry.getValue().getLevelBgObjects()) {
                Log.d("bg_object check",
                        "\nobject_id: " + bgObject.getObjectId() +
                                "\nposition_x: " + bgObject.getPositionX() +
                                "\nposition_y: " + bgObject.getPositionY() +
                                "\nscale: " + bgObject.getScale()
                );
            }
            for (LevelAsteroidsType levelAsteroid : entry.getValue().getLevelAsteroids()) {
                Log.d("level_asteroids check",
                        "\nasteroid_id: " + levelAsteroid.getAsteroidId() +
                                "\nnumberOfAsteroids: " + levelAsteroid.getNumberOfAsteroids()
                );
            }
        }
        return levelTypeMap;
    }
    
    public List<AsteroidType> readAsteroid() {
        Cursor c = db.rawQuery(SELECT_ALL_ASTEROIDS, null);
        int asteroidNameCol = c.getColumnIndex("asteroid_name");
        int imagePathCol = c.getColumnIndex("image_path");
        int imageWidthCol = c.getColumnIndex("image_width");
        int imageHeightCol = c.getColumnIndex("image_height");
        List<AsteroidType> asteroidTypeSet = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()){
                do {
                    String asteroidName = c.getString(asteroidNameCol);
                    String imagePath = c.getString(imagePathCol);
                    int imageWidth = c.getInt(imageWidthCol);
                    int imageHeight = c.getInt(imageHeightCol);
                    Log.d("check_add",
                            "type: " + asteroidName +
                            "\nimagePath: " + imagePath +
                            "\nimageWidth: " + String.valueOf(imageWidth) +
                            "\nimageHeight: " + String.valueOf(imageHeight));

                    if (asteroidName.equals("regular")) {
                        asteroidTypeSet.add(new RegularAsteroidType(imagePath, imageWidth, imageHeight));
                    }
                    else if (asteroidName.equals("growing")) {
                        asteroidTypeSet.add(new GrowingAsteroidType(imagePath, imageWidth, imageHeight));
                    }
                    else {
                        asteroidTypeSet.add(new OcteroidAsteroidType(imagePath, imageWidth, imageHeight));
                    }

                } while (c.moveToNext());
            }
        }
        return asteroidTypeSet;

    }

    public List<BgObjectType> readBgObject() {
            Cursor c = db.rawQuery(SELECT_ALL_BG_OBJECTS, null);
            int imagePathCol = c.getColumnIndex("file_path");
            List<BgObjectType> bgObjectTypeSet = new ArrayList<>();
            if (c != null) {
                if (c.moveToFirst()){
                    do {
                        //String imagePath = c.getColumnName(imagePathCol);
                        String imagePath = c.getString(imagePathCol);
                        bgObjectTypeSet.add(new BgObjectType(imagePath, -1, -1));
                    } while (c.moveToNext());
                }
            }
            return bgObjectTypeSet;
    }

    // SQL Select Commands
    String SELECT_ALL_BG_OBJECTS = "select * from bg_objects;";
    String SELECT_ALL_ASTEROIDS = "select * from asteroids";
    String SELECT_ALL_MAIN_BODIES = "select * from main_bodies;";
    String SELECT_ALL_EXTRA_PARTS = "select * from extra_parts;";
    String SELECT_ALL_CANNONS = "select * from cannons;";
    String SELECT_ALL_ENGINES = "select * from engines;";
    String SELECT_ALL_POWER_CORES = "select * from power_cores";
    String SELECT_ALL_LEVELS = "select * from levels";
    String SELECT_ALL_LEVEL_OBJECTS = "select * from level_objects";
    String SELECT_ALL_LEVEL_ASTEROIDS = "select * from level_asteroids";


    // Delete
    //--------------------------

    public boolean deleteCannon() {
        try {
            db.delete("cannons", null, null);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deleteEngine() {
        try {
            db.delete("engines", null, null);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deleteMainBody() {
        try {
            db.delete("main_bodies", null, null);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deleteExtraPart() {
        try {
            db.delete("extra_parts", null, null);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deletePowerCore() {
        try {
            db.delete("power_cores", null, null);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
   
    public boolean deleteLevel() {
        try {
            db.delete("levels", null, null);
            db.delete("level_objects", null, null);
            db.delete("level_asteroids", null, null);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean deleteAsteroid() {
        try {
            db.delete("asteroids", null, null);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deleteBgObject() {
        try {
            db.delete("bg_objects", null, null);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /** Deletes all the data in the database. */
    public boolean deleteAll() {
        try {
            deleteAsteroid();
            deleteBgObject();
            deleteCannon();
            deleteEngine();
            deleteExtraPart();
            deleteLevel();
            deleteMainBody();
            deletePowerCore();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
//    public boolean clear() {
//        db.execSQL(BG_OBJECTS_DROP);
//        db.execSQL(ASTEROIDS_DROP);
//        db.execSQL(MAIN_BODIES_DROP);
//        db.execSQL(EXTRA_PARTS_DROP);
//        db.execSQL(CANNONS_DROP);
//        db.execSQL(ENGINES_DROP);
//        db.execSQL(POWER_CORES_DROP);
//        db.execSQL(LEVELS_DROP);
//        db.execSQL(LEVEL_OBJECTS_DROP);
//        db.execSQL(LEVEL_ASTEROIDS_DROP);
//        return true;
//    }
//
//    // SQL Drop Commands
//    String BG_OBJECTS_DROP = "drop table if exists bg_objects;";
//    String ASTEROIDS_DROP = "drop table if exists asteroids;";
//    String MAIN_BODIES_DROP = "drop table if exists main_bodies;";
//    String EXTRA_PARTS_DROP = "drop table if exists extra_parts;";
//    String CANNONS_DROP = "drop table if exists cannons;";
//    String ENGINES_DROP = "drop table if exists engines;";
//    String POWER_CORES_DROP = "drop table if exists power_cores;";
//    String LEVELS_DROP = "drop table if exists levels;";
//    String LEVEL_OBJECTS_DROP = "drop table if exists level_objects;";
//    String LEVEL_ASTEROIDS_DROP = "drop table if exists  level_asteroids;";
}
