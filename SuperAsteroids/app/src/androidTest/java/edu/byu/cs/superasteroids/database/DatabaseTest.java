package edu.byu.cs.superasteroids.database;

import android.content.res.AssetManager;
import android.test.AndroidTestCase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import edu.byu.cs.superasteroids.androids_game_definitions.AsteroidType;
import edu.byu.cs.superasteroids.androids_game_definitions.BgObjectType;

import edu.byu.cs.superasteroids.androids_game_definitions.CannonType;
import edu.byu.cs.superasteroids.androids_game_definitions.EngineType;
import edu.byu.cs.superasteroids.androids_game_definitions.ExtraPartType;
import edu.byu.cs.superasteroids.androids_game_definitions.GrowingAsteroidType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelAsteroidsType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelObjectsType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelType;
import edu.byu.cs.superasteroids.androids_game_definitions.MainBodyType;
import edu.byu.cs.superasteroids.androids_game_definitions.OcteroidAsteroidType;
import edu.byu.cs.superasteroids.androids_game_definitions.PowerCoreType;
import edu.byu.cs.superasteroids.androids_game_definitions.RegularAsteroidType;
import edu.byu.cs.superasteroids.importer.GameDataImporter;


/**
 * An unit test for the database, including DbHelper, DAO, and DBManager.
 */
public class DatabaseTest extends AndroidTestCase {

    private DataAccessObject DAO;
    private DatabaseManager dbManager;
    private GameDataImporter dataImporter;
    private AssetManager am;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        am = getContext().getAssets();
        dbManager = new DatabaseManager(getContext());
        DAO = dbManager.DAO;
        dataImporter = new GameDataImporter(getContext());

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        dbManager.clearDatabase();
    }

    public void testReadBgObject() throws IOException{
        dataImporter.importData(new InputStreamReader(
                new BufferedInputStream(am.open("gamedata.json"))));
        List<BgObjectType> imortedBgObjectSet = DAO.readBgObject();
        Set<String> correctBgObjectSet = new HashSet<>();
        correctBgObjectSet.add("images/planet1.png");
        correctBgObjectSet.add("images/planet2.png");
        correctBgObjectSet.add("images/planet3.png");
        correctBgObjectSet.add("images/planet4.png");
        correctBgObjectSet.add("images/planet5.png");
        correctBgObjectSet.add("images/planet6.png");
        correctBgObjectSet.add("images/station.png");
        correctBgObjectSet.add("images/nebula1.png");
        correctBgObjectSet.add("images/nebula2.png");
        correctBgObjectSet.add("images/nebula3.png");
        correctBgObjectSet.add("images/nebula4.png");
        correctBgObjectSet.add("images/nebula5.png");

        for (BgObjectType bg : imortedBgObjectSet) {
            if (!correctBgObjectSet.contains(bg.getImagePath())) {
                fail("correctBgObjectSet does not contain " + bg.getImagePath());
            }
        }
        return;
    }

    public void testReadAsteroid() throws IOException {
        dataImporter.importData(new InputStreamReader(
                new BufferedInputStream(am.open("gamedata.json"))));
        List<AsteroidType> myAsteroidTypeSet = DAO.readAsteroid();
        List<AsteroidType> correctAsteroidTypeSet = new ArrayList<>();

        //Correct Results
        correctAsteroidTypeSet.add(new RegularAsteroidType(
                "images/asteroids/asteroid.png", 169, 153));
        correctAsteroidTypeSet.add(new GrowingAsteroidType(
                "images/asteroids/blueasteroid.png", 161, 178));
        correctAsteroidTypeSet.add(new OcteroidAsteroidType(
                "images/asteroids/asteroid.png", 169, 153));

        if (!myAsteroidTypeSet.equals(correctAsteroidTypeSet)) {
            fail("The set is not the expected set");
        }
    }

    public void testReadCannon() throws IOException {
        dataImporter.importData(new InputStreamReader(
                new BufferedInputStream(am.open("gamedata.json"))));
        List<CannonType> myCannonList = DAO.readCannon();
        List<CannonType> correctCannonList = new ArrayList<>();

        //correct results
        correctCannonList.add(new CannonType(
                "images/parts/cannon1.png", 160, 360, 14, 240, 104, 36, "images/parts/laser.png",
                50, 250, "sounds/laser.mp3", 1));
        correctCannonList.add(new CannonType(
                "images/parts/cannon2.png", 325, 386, 19, 137, 184, 21, "images/parts/laser2.png",
                105, 344, "sounds/laser.mp3", 2));

        if (!myCannonList.equals(correctCannonList)) {
            fail("The cannon list does not match.");
        }
    }

    public void testReadEngine() throws IOException {
        dataImporter.importData(new InputStreamReader(
                new BufferedInputStream(am.open("gamedata.json"))));
        List<EngineType> myEngineList = DAO.readEngine();
        List<EngineType> correctEngineList = new ArrayList<>();

        //correct results
        correctEngineList.add(new EngineType(
                "images/parts/engine1.png", 220, 160, 350, 106, 6, 270));
        correctEngineList.add(new EngineType(
                "images/parts/engine2.png", 208, 222, 500, 107, 7, 360));
        if (!myEngineList.get(1).toString().equals(correctEngineList.get(1).toString())) {
            fail("dosnt match.");
        }
        if (!myEngineList.get(0).toString().equals(correctEngineList.get(0).toString())) {
            fail("dosnt match.");
        }

        return;
    }

    public void testReadMainBody() throws IOException {
        dataImporter.importData(new InputStreamReader(
                new BufferedInputStream(am.open("gamedata.json"))));
        List<MainBodyType> myMainBodyList = DAO.readMainBody();
        List<MainBodyType> correctMainBodyList = new ArrayList<>();

        //correct results
        correctMainBodyList.add(new MainBodyType(
                "images/parts/mainbody1.png", 200, 400, 190, 227, 102, 392, 6, 253));
        correctMainBodyList.add(new MainBodyType(
                "images/parts/mainbody2.png", 156, 459, 143, 323, 85, 459, 26, 323));

        if (!myMainBodyList.equals(correctMainBodyList)) {
            fail("The main body list does not match.");
        }
        return;
    }

    public void testReadExtraPart() throws IOException {
        dataImporter.importData(new InputStreamReader(
                new BufferedInputStream(am.open("gamedata.json"))));
        List<ExtraPartType> myExtraPartList = DAO.readExtraPart();
        List<ExtraPartType> correctExtraPartList = new ArrayList<>();

        //correct results
        correctExtraPartList.add(new ExtraPartType(
                "images/parts/extrapart1.png", 320, 240, 312, 94));
        correctExtraPartList.add(new ExtraPartType(
                "images/parts/extrapart2.png", 331, 309, 310, 124));

        if (!myExtraPartList.equals(correctExtraPartList)) {
            fail("The extra parts list does not match.");
        }
    }

    public void testReadPowerCore() throws IOException {
        dataImporter.importData(new InputStreamReader(
                new BufferedInputStream(am.open("gamedata.json"))));
        List<PowerCoreType> myPowerCoreList = DAO.readPowerCore();
        List<PowerCoreType> correctPowerCoreList = new ArrayList<>();

        //correct results
        correctPowerCoreList.add(new PowerCoreType(
                "images/Ellipse.png", -1, -1, 10, 10));
        correctPowerCoreList.add(new PowerCoreType(
                "images/Ellipse.png", -1, -1, 10, 10));

        if (!myPowerCoreList.equals(correctPowerCoreList)) {
            fail("The power core list does not match.");
        }
    }

    public void testReadLevel() throws IOException {
        dataImporter.importData(new InputStreamReader(
                new BufferedInputStream(am.open("gamedata.json"))));
        Map<Integer, LevelType> myLevelMap = DAO.readLevel();
        Map<Integer, LevelType> correctLevelMap = new HashMap<>();

        //correct results
        correctLevelMap.put(1,
                new LevelType(1, "Level 1", "Destroy 1 Asteroid", 3000, 3000, "sounds/SpyHunter.ogg",
                        new ArrayList<LevelObjectsType>(), new ArrayList<LevelAsteroidsType>()));
        correctLevelMap.put(2,
                new LevelType(2, "Level 2", "Destroy 5 Asteroids", 3000, 3000, "sounds/SpyHunter.ogg",
                        new ArrayList<LevelObjectsType>(), new ArrayList<LevelAsteroidsType>()));
        correctLevelMap.put(3,
                new LevelType(3, "Level 3", "Destroy 10 Asteroids", 4056, 4056, "sounds/SpyHunter.ogg",
                        new ArrayList<LevelObjectsType>(), new ArrayList<LevelAsteroidsType>()));
        correctLevelMap.put(4,
                new LevelType(4, "Level 4", "Destroy 20 Asteroids", 4056, 4056, "sounds/SpyHunter.ogg",
                        new ArrayList<LevelObjectsType>(), new ArrayList<LevelAsteroidsType>()));
        correctLevelMap.put(5,
                new LevelType(5, "Level 5", "Destroy 20 Asteroids", 3000, 3000, "sounds/SpyHunter.ogg",
                        new ArrayList<LevelObjectsType>(), new ArrayList<LevelAsteroidsType>()));

        //Level 1
        correctLevelMap.get(1).getLevelBgObjects().add(new LevelObjectsType(1000, 1000, 1, 1.5));
        correctLevelMap.get(1).getLevelAsteroids().add(new LevelAsteroidsType(1, 4, 1));
        correctLevelMap.get(1).getLevelAsteroids().add(new LevelAsteroidsType(2, 4, 1));
        //Level 2
        correctLevelMap.get(2).getLevelAsteroids().add(new LevelAsteroidsType(1, 5, 2));
        //Level 3
        correctLevelMap.get(3).getLevelAsteroids().add(new LevelAsteroidsType(1, 10, 3));
        //Level 4
        correctLevelMap.get(4).getLevelAsteroids().add(new LevelAsteroidsType(1, 20, 4));
        //Level 5
        correctLevelMap.get(5).getLevelAsteroids().add(new LevelAsteroidsType(1, 20, 5));

        if (!myLevelMap.equals(correctLevelMap)) {
            fail("The levels dont match.");
        }
        return;
    }

}
