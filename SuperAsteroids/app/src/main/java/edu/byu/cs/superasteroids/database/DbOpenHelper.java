package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This is a necessary class to use sqlite.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my_db.sqlite";
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BG_OBJECTS);
        db.execSQL(ASTEROIDS);
        db.execSQL(MAIN_BODIES);
        db.execSQL(EXTRA_PARTS);
        db.execSQL(CANNONS);
        db.execSQL(ENGINES);
        db.execSQL(POWER_CORES);
        db.execSQL(LEVELS);
        db.execSQL(LEVEL_OBJECTS);
        db.execSQL(LEVEL_ASTEROIDS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }

    //All SQL Create Statements
    String BG_OBJECTS =
            "create table bg_objects " +
            "(" +
            "id integer not null primary key autoincrement," +
            "file_path varchar(255) not null" +
            ");";

    String ASTEROIDS =
            "create table asteroids " +
            "(" +
            "id integer not null primary key autoincrement," +
            "asteroid_name varchar(255) not null," +
            "image_path varcahr(255) not null," +
            "image_width integer not null," +
            "image_height integer not null" +
            ");";

    String MAIN_BODIES =
            "create table main_bodies " +
            "(" +
            "id integer not null primary key autoincrement," +
            "cannon_attach_x integer not null," +
            "cannon_attach_y integer not null," +
            "engine_attach_x integer not null," +
            "engine_attach_y integer not null," +
            "extra_attach_x integer not null," +
            "extra_attach_y integer not null," +
            "image_path varchar(255) not null," +
            "image_width integer not null," +
            "image_height integer not null" +
            ");";

    String EXTRA_PARTS =
            "create table extra_parts " +
            "(" +
            "id integer not null primary key autoincrement," +
            "attach_point_x integer not null," +
            "attach_point_y integer not null," +
            "image_path varchar(255) not null," +
            "image_width integer not null," +
            "image_height integer not null" +
            ");";

    String CANNONS =
            "create table cannons " +
            "(" +
            "id integer not null primary key autoincrement," +
            "attach_point_x integer not null," +
            "attach_point_y integer not null," +
            "emit_point_x integer not null," +
            "emit_point_y integer not null," +
            "image_path varchar(255) not null," +
            "image_width integer not null," +
            "image_height integer not null," +
            "attack_image_path varchar(255) not null," +
            "attack_image_width integer not null," +
            "attack_image_height integer not null," +
            "attack_sound_path varchar(255) not null," +
            "damage integer not null" +
            ");";

    String ENGINES =
            "create table engines " +
            "(" +
            "id integer not null primary key autoincrement," +
            "base_speed integer not null," +
            "base_turn_rate integer not null," +
            "attach_point_x integer not null," +
            "attach_point_y integer not null," +
            "image_path varchar(255) not null," +
            "image_width integer not null," +
            "image_height integer not null" +
            ");";

    String POWER_CORES =
            "create table power_cores " +
            "(" +
            "id integer not null primary key autoincrement," +
            "cannon_boost integer not null," +
            "engine_boost integer not null," +
            "image_path varchar(255) not null" +
            ");";


    String LEVELS =
            "create table levels " +
            "(" +
            "level_number integer not null," +
            "title varchar(255) not null," +
            "hint varchar(255) not null," +
            "width integer not null," +
            "height integer not null," +
            "music_path varchar(255) not null" +
            ");";

    String LEVEL_OBJECTS =
            "create table level_objects " +
            "(" +
            "object_id integer not null," +
            "position_x integer not null," +
            "position_y integer not null, " +
            "scale double default '1.0'," +
            "level_number integer not null," +
            "foreign key(level_number) references levels(level_number)" +
            ");";


    String LEVEL_ASTEROIDS =
            "create table level_asteroids " +
            "(" +
            "asteroid_id integer not null," +
            "number integer not null," +
            "level_number integer not null," +
            "foreign key(level_number) references levels(level_number)" +
            ");";
}
