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
    private static final int DATABASE_VERSION = 5;

    // Datebase Name
    private static final String DATABASE_NAME = "mycinemaapp.db";

    // Table Users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_USER_EMAIL = "useremail";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_IMAGE_PATH = "path";

    // Database creation sql statement
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_USER_NAME + " TEXT NOT NULL, "
                    + COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
                    + COLUMN_USER_EMAIL + " TEXT NOT NULL, "
                    + COLUMN_USER_IMAGE_PATH + " TEXT NOT NULL);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
