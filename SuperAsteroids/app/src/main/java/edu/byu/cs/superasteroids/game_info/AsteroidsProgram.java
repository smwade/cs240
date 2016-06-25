package edu.byu.cs.superasteroids.game_info;

import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.superasteroids.androids_game_definitions.AsteroidType;
import edu.byu.cs.superasteroids.androids_game_definitions.BgObjectType;
import edu.byu.cs.superasteroids.androids_game_definitions.CannonType;
import edu.byu.cs.superasteroids.androids_game_definitions.EngineType;
import edu.byu.cs.superasteroids.androids_game_definitions.ExtraPartType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelType;
import edu.byu.cs.superasteroids.androids_game_definitions.MainBodyType;
import edu.byu.cs.superasteroids.androids_game_definitions.PowerCoreType;
import edu.byu.cs.superasteroids.androids_game_objects.Asteroid;
import edu.byu.cs.superasteroids.androids_game_objects.BgObject;
import edu.byu.cs.superasteroids.androids_game_objects.Projectile;
import edu.byu.cs.superasteroids.androids_game_objects.Ship;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.DataAccessObject;
import edu.byu.cs.superasteroids.database.DbOpenHelper;

/**
 * The parent class of the game that contains all loaded in type definitions as well as current
 * instances and locations.
 */
public class AsteroidsProgram {

    private static AsteroidsProgram instance = null;

    private ContentManager contentManager;

    // Game Type Definitions
    private List<BgObject> BgObjectSet;
    private List<BgObjectType> BgObjectTypeSet;
    private List<AsteroidType> AsteroidTypeSet;
    private List<CannonType> CannonTypeSet;
    private List<EngineType> EngineTypeSet;
    private List<ExtraPartType> ExtraPartTypeSet;
    private List<MainBodyType> MainBodyTypeSet;
    private List<PowerCoreType> PowerCoreType;
    private Map<Integer, LevelType> LevelTypeMap;
    private Ship ship;

    private Set<Integer> allImages;
    private List<Integer> mainBodyImages;
    private List<Integer> cannonImages;
    private List<Integer> attackImages;
    private List<Integer> engineImages;
    private List<Integer> powerCoreImages;
    private List<Integer> extraPartImages;
    private List<Integer> bgImages;
    private List<Integer> asteroidImages;
    private List<Integer> attackSounds;
    private List<Integer> levelSounds;

    private List<Asteroid> curAsteroidList;
    private List<Projectile> curProjectileList;
    private List<BgObject> curBgObjectList;



    private DbOpenHelper dbOpenHelper;
    private DataAccessObject DAO;

    private AsteroidsProgram() {
        contentManager = ContentManager.getInstance();

        BgObjectSet = new ArrayList<>();
        BgObjectTypeSet = new ArrayList<>();
        AsteroidTypeSet = new ArrayList<>();
        CannonTypeSet = new ArrayList<>();
        EngineTypeSet = new ArrayList<>();
        ExtraPartTypeSet = new ArrayList<>();
        MainBodyTypeSet = new ArrayList<>();
        PowerCoreType = new ArrayList<>();
        LevelTypeMap = new HashMap<>();
        ship = new Ship();
        curAsteroidList = new ArrayList<>();
        curProjectileList = new ArrayList<>();
        curBgObjectList = new ArrayList<>();

        setAllImages(new HashSet<Integer>());
        setMainBodyImages(new ArrayList<Integer>());
        setCannonImages(new ArrayList<Integer>());
        setEngineImages(new ArrayList<Integer>());
        setExtraPartImages(new ArrayList<Integer>());
        setBgImages(new ArrayList<Integer>());
        setAsteroidImages(new ArrayList<Integer>());
        setAttackImages(new ArrayList<Integer>());
        setAttackSounds(new ArrayList<Integer>());
        setLevelSounds(new ArrayList<Integer>());
        setPowerCoreImages(new ArrayList<Integer>());
    }

    public static AsteroidsProgram getInstance() {
        if (instance == null) {
            instance = new AsteroidsProgram();
        }
        return instance;
    }

    // Game Type Getters and Setters
    //------------------------------
    public List<BgObject> getBgObjectSet() {
        return BgObjectSet;
    }

    public void setBgObjectSet(List<BgObject> bgObjectSet) {
        BgObjectSet = bgObjectSet;
    }

    public List<AsteroidType> getAsteroidTypeSet() {
        return AsteroidTypeSet;
    }

    public void setAsteroidTypeSet(List<AsteroidType> asteroidSet) {
        AsteroidTypeSet = asteroidSet;
    }

    public List<CannonType> getCannonTypeSet() {
        return CannonTypeSet;
    }

    public void setCannonTypeSet(List<CannonType> cannonTypeSet) {
        CannonTypeSet = cannonTypeSet;
    }

    public List<EngineType> getEngineTypeSet() {
        return EngineTypeSet;
    }

    public void setEngineTypeSet(List<EngineType> engineTypeSet) {
        EngineTypeSet = engineTypeSet;
    }

    public List<ExtraPartType> getExtraPartTypeSet() {
        return ExtraPartTypeSet;
    }

    public void setExtraPartTypeSet(List<ExtraPartType> extraPartTypeSet) {
        ExtraPartTypeSet = extraPartTypeSet;
    }

    public List<MainBodyType> getMainBodyTypeSet() {
        return MainBodyTypeSet;
    }

    public void setMainBodyTypeSet(List<MainBodyType> mainBodyTypeSet) {
        MainBodyTypeSet = mainBodyTypeSet;
    }

    public List<edu.byu.cs.superasteroids.androids_game_definitions.PowerCoreType> getPowerCoreType() {
        return PowerCoreType;
    }

    public void setPowerCoreType(List<edu.byu.cs.superasteroids.androids_game_definitions.PowerCoreType> powerCoreType) {
        PowerCoreType = powerCoreType;
    }

    public Map<Integer, LevelType> getLevelTypeMap() {
        return LevelTypeMap;
    }

    public void setLevelTypeMap(Map<Integer, LevelType> levelTypeMap) {
        LevelTypeMap = levelTypeMap;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public List<BgObjectType> getBgObjectTypeSet() {
        return BgObjectTypeSet;
    }

    public void setBgObjectTypeSet(List<BgObjectType> bgObjectTypeSet) {
        BgObjectTypeSet = bgObjectTypeSet;
    }

    public DbOpenHelper getDbOpenHelper() {
        return dbOpenHelper;
    }

    public void setDbOpenHelper(DbOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    public DataAccessObject getDAO() {
        return DAO;
    }

    public void setDAO(DataAccessObject DAO) {
        this.DAO = DAO;
    }

    public List<Asteroid> getCurAsteroidList() {
        return curAsteroidList;
    }

    public void setCurAsteroidList(List<Asteroid> curAsteroidList) {
        this.curAsteroidList = curAsteroidList;
    }

    public List<Projectile> getCurProjectileList() {
        return curProjectileList;
    }

    public void setCurProjectileList(List<Projectile> curProjectileList) {
        this.curProjectileList = curProjectileList;
    }

    public Set<Integer> getAllImages() {
        return allImages;
    }

    public void setAllImages(Set<Integer> allImages) {
        this.allImages = allImages;
    }

    public ContentManager getContentManager() {
        return contentManager;
    }

    public void setContentManager(ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    public List<BgObject> getCurBgObjectList() {
        return curBgObjectList;
    }

    public void setCurBgObjectList(List<BgObject> curBgObjectList) {
        this.curBgObjectList = curBgObjectList;
    }

    public List<Integer> getMainBodyImages() {
        return mainBodyImages;
    }

    public void setMainBodyImages(List<Integer> mainBodyImages) {
        this.mainBodyImages = mainBodyImages;
    }

    public List<Integer> getCannonImages() {
        return cannonImages;
    }

    public void setCannonImages(List<Integer> cannonImages) {
        this.cannonImages = cannonImages;
    }

    public List<Integer> getAttackImages() {
        return attackImages;
    }

    public void setAttackImages(List<Integer> attackImages) {
        this.attackImages = attackImages;
    }

    public List<Integer> getEngineImages() {
        return engineImages;
    }

    public void setEngineImages(List<Integer> engineImages) {
        this.engineImages = engineImages;
    }

    public List<Integer> getPowerCoreImages() {
        return powerCoreImages;
    }

    public void setPowerCoreImages(List<Integer> powerCoreImages) {
        this.powerCoreImages = powerCoreImages;
    }

    public List<Integer> getExtraPartImages() {
        return extraPartImages;
    }

    public void setExtraPartImages(List<Integer> extraPartImages) {
        this.extraPartImages = extraPartImages;
    }

    public List<Integer> getBgImages() {
        return bgImages;
    }

    public void setBgImages(List<Integer> bgImages) {
        this.bgImages = bgImages;
    }

    public List<Integer> getAsteroidImages() {
        return asteroidImages;
    }

    public void setAsteroidImages(List<Integer> asteroidImages) {
        this.asteroidImages = asteroidImages;
    }

    public List<Integer> getAttackSounds() {
        return attackSounds;
    }

    public void setAttackSounds(List<Integer> attackSounds) {
        this.attackSounds = attackSounds;
    }

    public List<Integer> getLevelSounds() {
        return levelSounds;
    }

    public void setLevelSounds(List<Integer> levelSounds) {
        this.levelSounds = levelSounds;
    }

    /**This method populates the AsteroidsProgram with the surrent data in the database.  It clears
     * the old data.*/
    public void loadInFromDatabase(DataAccessObject DAO) {
        this.clear();
        this.setBgObjectTypeSet(DAO.readBgObject());
        this.setAsteroidTypeSet(DAO.readAsteroid());
        this.setCannonTypeSet(DAO.readCannon());
        this.setEngineTypeSet(DAO.readEngine());
        this.setExtraPartTypeSet(DAO.readExtraPart());
        this.setMainBodyTypeSet(DAO.readMainBody());
        this.setPowerCoreType(DAO.readPowerCore());
        this.setLevelTypeMap(DAO.readLevel());
    }

    /**Clears all current information in the member variables.*/
    public void clear() {
        BgObjectSet = new ArrayList<>();
        BgObjectTypeSet = new ArrayList<>();
        AsteroidTypeSet = new ArrayList<>();
        CannonTypeSet = new ArrayList<>();
        EngineTypeSet = new ArrayList<>();
        ExtraPartTypeSet = new ArrayList<>();
        MainBodyTypeSet = new ArrayList<>();
        PowerCoreType = new ArrayList<>();
        LevelTypeMap = new HashMap<>();
        ship = new Ship();
    }

    public void loadContent(ContentManager content) {
        try {

            // Load BgObjects
            List<BgObjectType> bgObjectsList = getBgObjectTypeSet();
            for (BgObjectType object : bgObjectsList) {
                bgImages.add(content.loadImage(object.getImagePath()));
            }

            // Load Asteroids
            List<AsteroidType> asteroidList = getAsteroidTypeSet();
            for (AsteroidType asteroid : asteroidList) {
                asteroidImages.add(content.loadImage(asteroid.getImagePath()));
            }

            // Load Cannons
            List<CannonType> cannonList = getCannonTypeSet();
            for (CannonType cannon : cannonList) {
                cannonImages.add(content.loadImage(cannon.getImagePath()));
            }

            // Load Attack Sound
            for (CannonType cannon : cannonList) {
                attackSounds.add(content.loadSound(cannon.getAttackSound()));
            }

            // Load Attack Image
            for (CannonType cannon : cannonList) {
                attackImages.add(content.loadImage(cannon.getAttackImage()));
            }

            // Load Engines
            List<EngineType> engineList = getEngineTypeSet();
            for (EngineType engine : engineList) {
                engineImages.add(content.loadImage(engine.getImagePath()));
            }

            // Load Extra Parts
            List<ExtraPartType> extraPartList = getExtraPartTypeSet();
            for (ExtraPartType extraPart : extraPartList) {
                extraPartImages.add(content.loadImage(extraPart.getImagePath()));
            }

            // Load Main Bodies
            List<MainBodyType> mainBodyList = getMainBodyTypeSet();
            for (MainBodyType mainBody : mainBodyList) {
                mainBodyImages.add(content.loadImage(mainBody.getImagePath()));
            }

            // Load Power Cores
            List<PowerCoreType> powerCoreList = getPowerCoreType();
            for (PowerCoreType powerCore : powerCoreList) {
                powerCoreImages.add(content.loadImage(powerCore.getImagePath()));
            }

            // Load Level Sound
            Map<Integer, LevelType> levelMap = getLevelTypeMap();
            for (Integer i : levelMap.keySet()) {
                levelSounds.add(content.loadSound(levelMap.get(i).getMusicPath()));
            }
        } catch (IOException e) {
            Log.v("ERROR", "Load did not work.");
        }
    }

    public void unloadContent() {
            // Load BgObjects
            for (int id : getBgImages()) {
                contentManager.unloadImage(id);
            }

            // Load Asteroids
            for (int id : getAsteroidImages()) {
                contentManager.unloadImage(id);
            }

            // Load Cannons
            for (int id : getCannonImages()) {
                contentManager.unloadImage(id);
            }

            // Load Attack Sound
            for (int id : getAttackSounds()) {
                contentManager.unloadSound(id);
            }

            // Load Attack Image
            for (int id : getAttackImages()) {
                contentManager.unloadImage(id);
            }

            // Load Engines
            for (int id : getEngineImages()) {
                contentManager.unloadImage(id);
            }

            // Load Extra Parts
            for (int id : getExtraPartImages()) {
                contentManager.unloadImage(id);
            }

            // Load Main Bodies
            for (int id : getMainBodyImages()) {
                contentManager.unloadImage(id);
            }

            // Load Power Cores
            for (int id : getPowerCoreImages()) {
                contentManager.unloadImage(id);
            }

            // Load Level Sound
            for (int id : getLevelSounds()) {
                contentManager.unloadSound(id);
            }

        mainBodyImages.clear();
        cannonImages.clear();
        attackImages.clear();
        engineImages.clear();
        powerCoreImages.clear();
        extraPartImages.clear();
        bgImages.clear();
        asteroidImages.clear();
        attackSounds.clear();
        levelSounds.clear();

    }

        public class Level {
            List<Asteroid> asteroidList;
            List<BgObject> bgOjectList;

            public Level() {
            }

            public List<Asteroid> getAsteroidList() {
                return asteroidList;
            }

            public void setAsteroidList(List<Asteroid> asteroidList) {
                this.asteroidList = asteroidList;
            }

            public List<BgObject> getBgOjectList() {
                return bgOjectList;
            }

            public void setBgOjectList(List<BgObject> bgOjectList) {
                this.bgOjectList = bgOjectList;
            }
        }
    }

