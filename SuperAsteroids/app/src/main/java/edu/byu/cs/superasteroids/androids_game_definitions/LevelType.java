package edu.byu.cs.superasteroids.androids_game_definitions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.androids_game_objects.Asteroid;
import edu.byu.cs.superasteroids.androids_game_objects.Projectile;

/**
 * The type definitons for what it means to be a level.
 */
public class LevelType extends TypeDefinitions {

    private Set<Asteroid> levelAsteroidSet;
    private Set<Projectile> levelProjectileSet;
    //private Set<>

    public LevelType(int level_number, String title, String hint, int width, int height,
                     String musicPath, List<LevelObjectsType> levelBgObjects, List<LevelAsteroidsType> levelAsteroids) {
        super(level_number, title, hint, width, height, musicPath, levelBgObjects, levelAsteroids);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
