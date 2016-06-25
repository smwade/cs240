package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * The parent class for all type definitions from the database.
 */
public class TypeDefinitions extends GeneralDefinitionType{
    private int level_number;
    private String title;
    private String hint;
    private int width;
    private int height;
    private String musicPath;
    private List<LevelObjectsType> levelBgObjects;
    private List<LevelAsteroidsType> levelAsteroids;

    public TypeDefinitions(JSONObject levelJson) throws JSONException {
        title = levelJson.getString("title");
        hint = levelJson.getString("hint");
        width = levelJson.getInt("width");
        height = levelJson.getInt("height");
        musicPath = levelJson.getString("music");
        JSONObject levelObjects = levelJson.getJSONObject("levelObjects");
        int positionX = levelObjects.getInt("position");
        int positionY = levelObjects.getInt("position");
        int objectId = levelObjects.getInt("objectId");
        long scale = levelObjects.getLong("scale");
    }

    public TypeDefinitions(int level_number, String title, String hint, int width, int height,
                           String musicPath, List<LevelObjectsType> levelBgObjects, List<LevelAsteroidsType>
                                   levelAsteroids) {
        this.level_number = level_number;
        this.title = title;
        this.hint = hint;
        this.width = width;
        this.height = height;
        this.musicPath = musicPath;
        this.levelBgObjects = levelBgObjects;
        this.levelAsteroids = levelAsteroids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public List<LevelObjectsType> getLevelBgObjects() {
        return levelBgObjects;
    }

    public void setLevelBgObjects(List<LevelObjectsType> levelBgObjects) {
        this.levelBgObjects = levelBgObjects;
    }

    public List<LevelAsteroidsType> getLevelAsteroids() {
        return levelAsteroids;
    }

    public void setLevelAsteroids(List<LevelAsteroidsType> levelAsteroids) {
        this.levelAsteroids = levelAsteroids;
    }

    public int getLevel_number() {
        return level_number;
    }

    public void setLevel_number(int level_number) {
        this.level_number = level_number;
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
        LevelType that = (LevelType) o;

        if (this.getLevel_number() != that.getLevel_number()) return false;
        if (!this.getTitle().equals(that.getTitle())) return false;
        if (!this.getHint().equals(that.getHint())) return false;
        if (this.getWidth() != that.getWidth()) return false;
        if (this.getHeight() != that.getHeight()) return false;
        if (!this.getMusicPath().equals(that.getMusicPath())) return false;
        if (!this.getLevelBgObjects().equals(that.getLevelBgObjects())) return false;
        if (!this.getLevelAsteroids().equals(that.getLevelAsteroids())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return level_number * height * 17;
    }

    /**
     * This class is for what asteroids are in a level definition from database.
     */
    public class AsteroidLevelType {
        private int numberOfAsteroids;
        private int asteroidId;

        public AsteroidLevelType(int numberOfAsteroids, int asteroidId) {
            this.numberOfAsteroids = numberOfAsteroids;
            this.asteroidId = asteroidId;
        }

        public int getNumberOfAsteroids() {
            return numberOfAsteroids;
        }

        public void setNumberOfAsteroids(int numberOfAsteroids) {
            this.numberOfAsteroids = numberOfAsteroids;
        }

        public int getAsteroidId() {
            return asteroidId;
        }

        public void setAsteroidId(int asteroidId) {
            this.asteroidId = asteroidId;
        }
    }

    /**
     * This class is for what are the background objects in a level definition from database.
     */
    public class LevelBgObjectType {
        private int positionX;
        private int positionY;
        private int objectId;
        private long scale;

        public LevelBgObjectType(int positionX, int positionY, int objectId, long scale) {
            this.positionX = positionX;
            this.positionY = positionY;
            this.objectId = objectId;
            this.scale = scale;
        }

        public int getPositionX() {
            return positionX;
        }

        public void setPositionX(int positionX) {
            this.positionX = positionX;
        }

        public int getPositionY() {
            return positionY;
        }

        public void setPositionY(int positionY) {
            this.positionY = positionY;
        }

        public int getObjectId() {
            return objectId;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public float getScale() {
            return scale;
        }

        public void setScale(long scale) {
            this.scale = scale;
        }
    }
}
