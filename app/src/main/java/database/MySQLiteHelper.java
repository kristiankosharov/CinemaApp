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
    private static final int DATABASE_VERSION = 22;

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
    public static final String COLUMN_MOVIE_RATE = "progress";
    public static final String COLUMN_MOVIE_IMG_URL = "img_url";
    public static final String COLUMN_MOVIE_DURATION = "duration";
    public static final String COLUMN_MOVIE_IMDB = "imbd_url";
    public static final String COLUMN_MOVIE_DESCRIPTION = "description";
    public static final String COLUMN_MOVIE_DIRECTOR = "director";
    public static final String COLUMN_MOVIE_RELEASE_DATE = "date";
    public static final String COLUMN_MOVIE_PRICE = "price";
    public static final String COLUMN_MOVIE_NEW_THIS_WEEK = "new_this_week";

    // Table Actors
    public static final String TABLE_ACTORS = "actors";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_ACTORS_TITLE = "title";

    // Table Genres
    public static final String TABLE_GENRES = "genres";
    public static final String COLUMN_GENRES_TITLE = "title";

    // Table Cinema
    public static final String TABLE_CINEMA = "cinema";
    public static final String COLUMN_CINEMA_TITLE = "title";
    public static final String COLUMN_CINEMA_LONGITUDE = "longitude";
    public static final String COLUMN_CINEMA_LATITUDE = "latitude";

    // Table MOIVES-CINEMAS
    public static final String TABLE_MOVIES_CINEMAS = "moives_cinemas";
    public static final String COLUMN_CINEMA_ID = "cinema_id";
    public static final String COLUMN_IS_ACTIVE = "is_active";

    // Table MOVIE_CINEMA_PROJECTIONS
    public static final String TABLE_MOVIE_CINEMA_PROJECTIONS = "movie_cinema_projections";
    public static final String COLUMN_MOVIE_CINEMA_ID = "movie_cinema_id";
    public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
    public static final String COLUMN_STARTING_TIME = "strting_time";

    // Table MOVIES_GENRES
    public static final String TABLE_MOVIES_GENRES = "movies_genres";
    public static final String COLUMN_GENRES_ID = "genres_id";

    // Table All days
    public static final String TABLE_ALL_DAYS = "alldays";
    public static final String COLUMN_DAYS_FILTER = "daysfilter";
    public static final String COLUMN_DAYS_NAME = "days_name";

    // Table All Cinemas
    public static final String TABLE_ALL_CINEMAS = "allcinemas";
    public static final String COLUMN_CINEMAS_FILTER = "cinemasfilter";

    // Table All Genres
    public static final String TABLE_ALL_GENRES = "allgenres";
    public static final String COLUMN_GENRES_FILTER = "genresfilter";

    // Table MOVIE-DAYS
    public static final String TABLE_MOVIE_DAYS = "movie_days";
    public static final String COLUMN_MOVIE_DAYS_DAY_ID = "day_id";

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
                    + COLUMN_MOVIE_RATE + " REAL, "
                    + COLUMN_MOVIE_IMG_URL + " TEXT, "
                    + COLUMN_MOVIE_DURATION + " INTEGER, "
                    + COLUMN_MOVIE_IMDB + " TEXT, "
                    + COLUMN_MOVIE_DESCRIPTION + " TEXT, "
                    + COLUMN_MOVIE_DIRECTOR + " TEXT, "
                    + COLUMN_MOVIE_RELEASE_DATE + " TEXT, "
                    + COLUMN_MOVIE_PRICE + " TEXT, "
                    + COLUMN_MOVIE_NEW_THIS_WEEK + " TEXT);";

    private static final String CREATE_TABLE_MOVIES_CINEMAS =
            "CREATE TABLE " + TABLE_MOVIES_CINEMAS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MOVIE_ID + " INTEGER, "
                    + COLUMN_CINEMA_ID + " INTEGER, "
                    + COLUMN_IS_ACTIVE + " INTEGER ,"
                    + "FOREIGN KEY(" + COLUMN_MOVIE_ID + ") REFERENCES " + TABLE_MOVIES + "(" + COLUMN_ID + "), "
                    + "FOREIGN KEY(" + COLUMN_CINEMA_ID + ") REFERENCES " + TABLE_CINEMA + "(" + COLUMN_ID + "));";

    private static final String CREATE_TABLE_MOVIE_CINEMA_PROJECTIONS =
            "CREATE TABLE " + TABLE_MOVIE_CINEMA_PROJECTIONS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MOVIE_CINEMA_ID + " INTEGER, "
                    + COLUMN_DAY_OF_WEEK + " TEXT, "
                    + COLUMN_STARTING_TIME + " TEXT, "
                    + "FOREIGN KEY(" + COLUMN_MOVIE_CINEMA_ID + ") REFERENCES " + TABLE_MOVIES_CINEMAS + "(" + COLUMN_ID + ");";

    private static final String CREATE_TABLE_MOVIES_GENRES =
            "CREATE TABLE " + TABLE_MOVIES_GENRES + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MOVIE_ID + " INTEGER, "
                    + COLUMN_GENRES_ID + " INTEGER, "
                    + "FOREIGN KEY(" + COLUMN_MOVIE_ID + ") REFERENCES " + TABLE_MOVIES + "(" + COLUMN_ID + "),"
                    + "FOREIGN KEY(" + COLUMN_GENRES_ID + ") REFERENCES " + TABLE_GENRES + "(" + COLUMN_ID + ");";


    // Database creation sql statement
    private static final String CREATE_TABLE_ALL_DAYS =
            "CREATE TABLE " + TABLE_ALL_DAYS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_DAYS_FILTER + " TEXT, "
                    + COLUMN_DAYS_NAME + " TEXT);";

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
    // CREATE ACTORS TABLE
    private static final String CREATE_TABLE_ACTORS =
            "CREATE TABLE " + TABLE_ACTORS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MOVIE_ID + " INTEGER, "
                    + COLUMN_ACTORS_TITLE + " TEXT, "
                    + "FOREIGN KEY(" + COLUMN_MOVIE_ID + ") REFERENCES " + TABLE_MOVIES + "(" + COLUMN_ID + "));";
    // CREATE GENRES TABLE
    private static final String CREATE_TABLE_GENRES =
            "CREATE TABLE " + TABLE_GENRES + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_GENRES_TITLE + " TEXT);";

    private static final String CREATE_TABLE_CINEMA =
            "CREATE TABLE " + TABLE_CINEMA + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_CINEMA_TITLE + " TEXT, "
                    + COLUMN_CINEMA_LONGITUDE + " REAL, "
                    + COLUMN_CINEMA_LATITUDE + " REAL);";

    // Database creation sql statement
    private static final String CREATE_TABLE_MOVIE_DAYS =
            "CREATE TABLE " + TABLE_MOVIE_DAYS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MOVIE_DAYS_DAY_ID + " INTEGER, "
                    + COLUMN_MOVIE_ID + " INTEGER, "
                    + " FOREIGN KEY (" + COLUMN_MOVIE_DAYS_DAY_ID + ") REFERENCES " + TABLE_ALL_DAYS + "(" + COLUMN_ID + "),"
                    + " FOREIGN KEY (" + COLUMN_MOVIE_ID + ") REFERENCES " + TABLE_MOVIES + "(" + COLUMN_ID + "));";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_MOVIES);
        db.execSQL(CREATE_TABLE_CINEMA);
        db.execSQL(CREATE_TABLE_GENRES);
        db.execSQL(CREATE_TABLE_MOVIES_GENRES);
        db.execSQL(CREATE_TABLE_MOVIES_CINEMAS);
        db.execSQL(CREATE_TABLE_MOVIE_CINEMA_PROJECTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CINEMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENRES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES_GENRES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES_CINEMAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE_CINEMA_PROJECTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENRES);
        onCreate(db);
    }
}
