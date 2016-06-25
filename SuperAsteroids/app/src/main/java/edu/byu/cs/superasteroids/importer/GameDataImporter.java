package edu.byu.cs.superasteroids.importer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.byu.cs.superasteroids.androids_game_definitions.AsteroidType;
import edu.byu.cs.superasteroids.androids_game_definitions.BgObjectType;
import edu.byu.cs.superasteroids.androids_game_definitions.CannonType;
import edu.byu.cs.superasteroids.androids_game_definitions.EngineType;
import edu.byu.cs.superasteroids.androids_game_definitions.ExtraPartType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelAsteroidsType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelObjectsType;
import edu.byu.cs.superasteroids.androids_game_definitions.LevelType;
import edu.byu.cs.superasteroids.androids_game_definitions.MainBodyType;
import edu.byu.cs.superasteroids.androids_game_definitions.PowerCoreType;
import edu.byu.cs.superasteroids.database.DataAccessObject;
import edu.byu.cs.superasteroids.database.DatabaseManager;
import edu.byu.cs.superasteroids.game_info.AsteroidsProgram;

/**
 * This class is how to import data from a JSON to the database.
 */
public class GameDataImporter implements IGameDataImporter {

    private Context context;

    public GameDataImporter(Context c) {
        context = c;
    }

    /** Helper function for importData(), makes a string from a Reader. */
    private static String makeString(Reader reader) throws IOException {

        StringBuilder sb = new StringBuilder();
        char[] buf = new char[512];

        int n = 0;
        while ((n = reader.read(buf)) > 0) {
            sb.append(buf, 0, n);
        }

        return sb.toString();
    }

    /** Helper function for importData(), converts string seperated coordinates to arrays. */
    private int[] cordinateStringToInts(String toConvert) {
        String[] strArray = toConvert.split(",");
        int[] ints = new int[strArray.length];
        for (int i=0; i < strArray.length; i++) {
            ints[i] = Integer.parseInt(strArray[i]);
        }
        return ints;
    }

    @Override
    public boolean importData(InputStreamReader dataInputReader) {
        try {
            DatabaseManager dbManager = new DatabaseManager(context);
            DataAccessObject DAO = dbManager.DAO;
            DAO.deleteAll();

            JSONObject rootObject = new JSONObject(makeString(dataInputReader));
            JSONObject asteroidsGame = null;
            try {
                asteroidsGame = rootObject.getJSONObject("asteroidsGame");
                JSONArray objects = asteroidsGame.getJSONArray("objects");
                JSONArray asteroids = asteroidsGame.getJSONArray("asteroids");
                JSONArray levels = asteroidsGame.getJSONArray("levels");
                JSONArray mainBodies = asteroidsGame.getJSONArray("mainBodies");
                JSONArray cannons = asteroidsGame.getJSONArray("cannons");
                JSONArray extraParts = asteroidsGame.getJSONArray("extraParts");
                JSONArray engines = asteroidsGame.getJSONArray("engines");
                JSONArray powerCores = asteroidsGame.getJSONArray("powerCores");

                //BACK GROUND OBJECTS
                for (int i = 0; i < objects.length(); i++) {
                    String bgString = objects.getString(i);
                    DAO.createBgObject(new BgObjectType(bgString, -1, -1));
                }
                // ASTEROIDS
                for (int i = 0; i < asteroids.length(); i++) {
                    String asteroidType = asteroids.getJSONObject(i).getString("name");
                    String imagePath = asteroids.getJSONObject(i).getString("image");
                    int width = asteroids.getJSONObject(i).getInt("imageWidth");
                    int height = asteroids.getJSONObject(i).getInt("imageHeight");
                    if (asteroidType.equals("regular")) {
                        DAO.createAsteroid(new AsteroidType(imagePath, width, height, "regular"));
                    }
                    else if (asteroidType.equals("growing")) {
                        DAO.createAsteroid(new AsteroidType(imagePath, width, height, "growing"));
                    }
                    else {
                        DAO.createAsteroid(new AsteroidType(imagePath, width, height, "octeroid"));
                    }
                }
            // LEVELS
            for (int i = 0; i < levels.length(); i++) {
                JSONObject level = levels.getJSONObject(i);
                int number = level.getInt("number");
                String title = level.getString("title");
                String hint = level.getString("hint");
                int width = level.getInt("width");
                int height = level.getInt("height");
                String music = level.getString("music");
                JSONArray levelObjects = level.getJSONArray("levelObjects");
                List<LevelObjectsType> levelBgObjects = new ArrayList<>();
                for (int j = 0; j < levelObjects.length(); j++) {
                    JSONObject lobject = levelObjects.getJSONObject(j);
                    int[] cordinateArray = cordinateStringToInts(lobject.getString("position"));
                    int objectId = lobject.getInt("objectId");
                    double scale = lobject.getDouble("scale");
                    levelBgObjects.add(new LevelObjectsType(cordinateArray[0], cordinateArray[1],
                            objectId, scale));
                }
                JSONArray levelAsteroids = level.getJSONArray("levelAsteroids");
                List<LevelAsteroidsType> asteroidsArray = new ArrayList<>();
                for (int j = 0; j < levelAsteroids.length(); j++) {
                    JSONObject asteroid = levelAsteroids.getJSONObject(j);
                    int asteroidNumber = asteroid.getInt("number");
                    int asteroidId = asteroid.getInt("asteroidId");
                    asteroidsArray.add(new LevelAsteroidsType(asteroidId, asteroidNumber, level.getInt("number")));
                }
                DAO.createLevel(new LevelType(number, title, hint, width, height, music, levelBgObjects, asteroidsArray));
            }
                //  MAIN BODIES
                for (int i = 0; i < mainBodies.length(); i++) {
                    JSONObject body = mainBodies.getJSONObject(i);
                    String cannonAttach = body.getString("cannonAttach");
                    int[] cannaonAttachArray = cordinateStringToInts(cannonAttach);
                    String engineAttach = body.getString("engineAttach");
                    int[] engineAttachArray = cordinateStringToInts(engineAttach);
                    String extraAttach = body.getString("extraAttach");
                    int[] extraAttachArray = cordinateStringToInts(extraAttach);
                    String image = body.getString("image");
                    int imageWidth = body.getInt("imageWidth");
                    int imageHeight = body.getInt("imageHeight");
                    DAO.createMainBody(new MainBodyType(image, imageWidth, imageHeight, cannaonAttachArray[0],
                            cannaonAttachArray[1],engineAttachArray[0], engineAttachArray[1], extraAttachArray[0],
                            extraAttachArray[1]));
                }
                // CANNONS
                for (int i = 0; i < cannons.length(); i++) {
                    JSONObject can = cannons.getJSONObject(i);
                    String attachPoint = can.getString("attachPoint");
                    int[] attachPointArray = cordinateStringToInts(attachPoint);
                    String emitPoint = can.getString("emitPoint");
                    int[] emitPointArray = cordinateStringToInts(emitPoint);
                    String image = can.getString("image");
                    int imageWidth = can.getInt("imageWidth");
                    int imageHeight = can.getInt("imageHeight");
                    String attackImage = can.getString("attackImage");
                    int attackImageWidth = can.getInt("attackImageWidth");
                    int attackImageHeight = can.getInt("attackImageHeight");
                    String attackSound = can.getString("attackSound");
                    int damage = can.getInt("damage");
                    DAO.createCannon(new CannonType(image, imageWidth, imageHeight, attachPointArray[0],
                            attachPointArray[1], emitPointArray[0], emitPointArray[1], attackImage,
                            attackImageWidth, attackImageHeight, attackSound, damage));
                }
                // Extra Parts
                for (int i = 0; i < extraParts.length(); i++) {
                    JSONObject ePart = extraParts.getJSONObject(i);
                    String attachPoint = ePart.getString("attachPoint");
                    int[] attachPointArray = cordinateStringToInts(attachPoint);
                    String image = ePart.getString("image");
                    int imageWidth = ePart.getInt("imageWidth");
                    int imageHeight = ePart.getInt("imageHeight");
                    DAO.createExtraPart(new ExtraPartType(image, imageWidth, imageHeight,
                            attachPointArray[0], attachPointArray[1]));
                }
                // ENGINES
                for (int i = 0; i < engines.length(); i++) {
                    JSONObject eng = engines.getJSONObject(i);
                    int baseSpeed = eng.getInt("baseSpeed");
                    int baseTurnRate = eng.getInt("baseTurnRate");
                    String attachPoint = eng.getString("attachPoint");
                    int[] attachPointArray = cordinateStringToInts(attachPoint);
                    String image = eng.getString("image");
                    int imageWidth = eng.getInt("imageWidth");
                    int imageHeight = eng.getInt("imageHeight");
                    DAO.createEngine(new EngineType(image, imageWidth, imageHeight, baseSpeed,
                            attachPointArray[0], attachPointArray[1], baseTurnRate));
                }
                // POWER CORES
                for (int i = 0; i < powerCores.length(); i++) {
                    JSONObject pCore = powerCores.getJSONObject(i);
                    int cannonBoost = pCore.getInt("cannonBoost");
                    int engineBoost = pCore.getInt("engineBoost");
                    String image = pCore.getString("image");
                    DAO.createPowerCore(new PowerCoreType(image, -1, -1, cannonBoost, engineBoost));
                }
                AsteroidsProgram asteroidsProgram = AsteroidsProgram.getInstance();
                asteroidsProgram.loadInFromDatabase(DAO);
                asteroidsProgram.unloadContent();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
