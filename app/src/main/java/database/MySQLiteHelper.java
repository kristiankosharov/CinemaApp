package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kristian on 15-4-3.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Datebase Version
    private static final int DATABASE_VERSION = 13;

    // Datebase Name
    private static final String DATABASE_NAME = "mycinemaapp.db";

    // Table Users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_USER_EMAIL = "useremail";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_IMAGE_PATH = "path";


    // Table Movies
    public static final String TABLE_MOVIES = "movies";
    public static final String COLUMN_MOVIE_TITLE = "title";
    public static final String COLUMN_MOVIE_PROGRESS = "progress";
    public static final String COLUMN_MOVIE_DAYS = "days";
    public static final String COLUMN_MOVIE_CINEMAS = "cinemas";
    public static final String COLUMN_MOVIE_GENRES = "genres";

    // Table All days
    public static final String TABLE_ALL_DAYS = "alldays";
    public static final String COLUMN_DAYS_FILTER = "daysfilter";

    // Table All Cinemas
    public static final String TABLE_ALL_CINEMAS = "allcinemas";
    public static final String COLUMN_CINEMAS_FILTER = "cinemasfilter";

    // Table All Genres
    public static final String TABLE_ALL_GENRES = "allgenres";
    public static final String COLUMN_GENRES_FILTER = "genresfilter";

    // Database creation sql statement
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_USER_NAME + " TEXT NOT NULL, "
                    + COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
                    + COLUMN_USER_EMAIL + " TEXT NOT NULL, "
                    + COLUMN_USER_IMAGE_PATH + " TEXT NOT NULL);";


    // Database creation sql statement
    private static final String CREATE_TABLE_MOVIES =
            "CREATE TABLE " + TABLE_MOVIES + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MOVIE_TITLE + " TEXT NOT NULL, "
                    + COLUMN_MOVIE_PROGRESS + " TEXT NOT NULL, "
                    + COLUMN_MOVIE_DAYS + " TEXT, "
                    + COLUMN_MOVIE_CINEMAS + " TEXT, "
                    + COLUMN_MOVIE_GENRES + " TEXT);";

    // Database creation sql statement
    private static final String CREATE_TABLE_ALL_DAYS =
            "CREATE TABLE " + TABLE_ALL_DAYS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_DAYS_FILTER + " TEXT);";

    // Database creation sql statement
    private static final String CREATE_TABLE_ALL_CINEMAS =
            "CREATE TABLE " + TABLE_ALL_CINEMAS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_CINEMAS_FILTER + " TEXT);";

    // Database creation sql statement
    private static final String CREATE_TABLE_ALL_GENRES =
            "CREATE TABLE " + TABLE_ALL_GENRES + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_GENRES_FILTER + " TEXT);";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_MOVIES);
        db.execSQL(CREATE_TABLE_ALL_DAYS);
        db.execSQL(CREATE_TABLE_ALL_CINEMAS);
        db.execSQL(CREATE_TABLE_ALL_GENRES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_DAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_CINEMAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_GENRES);
        onCreate(db);
    }
}
