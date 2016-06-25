package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import edu.byu.cs.superasteroids.androids_game_definitions.*;
import edu.byu.cs.superasteroids.androids_game_objects.*;
import edu.byu.cs.superasteroids.game_info.AsteroidsProgram;

/**
 * The DatabaseManager deals with all the operations that are involved with the database.  This includes
 * getters, setters, DAO, and clearing the database.
 */
public class DatabaseManager {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase database;
    private Context baseContext;
    public DataAccessObject DAO;

    public DatabaseManager(Context baseContext) {
        this.baseContext = baseContext;
        dbOpenHelper = AsteroidsProgram.getInstance().getDbOpenHelper();
        database = dbOpenHelper.getWritableDatabase();
        DAO = new DataAccessObject(database);
    }

    /** Clears the whole database/ */
    public void clearDatabase() {
        database.delete("bg_objects", null, null);
        database.delete("asteroids", null, null);
        database.delete("main_bodies", null, null);
        database.delete("extra_parts", null, null);
        database.delete("cannons", null, null);
        database.delete("engines", null, null);
        database.delete("power_cores", null, null);
        database.delete("levels", null, null);
        database.delete("level_objects", null, null);
        database.delete("level_asteroids", null, null);
    }

    public DbOpenHelper getDbOpenHelper() {
        return dbOpenHelper;
    }

    public void setDbOpenHelper(DbOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public Context getBaseContext() {
        return baseContext;
    }

    public void setBaseContext(Context baseContext) {
        this.baseContext = baseContext;
    }

    public DataAccessObject getDAO() {
        return DAO;
    }

    public void setDAO(DataAccessObject DAO) {
        this.DAO = DAO;
    }
}
