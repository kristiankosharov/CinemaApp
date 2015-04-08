package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Filters;
import models.Movie;

/**
 * Created by kristian on 15-4-8.
 */
public class AllCinemasDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_CINEMAS_FILTER};

    public AllCinemasDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Filters createFilter(String filter) {
        Filters newUser = new Filters();
        long insertId = 0;
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CINEMAS_FILTER, filter);
        insertId = database.insert(MySQLiteHelper.TABLE_ALL_CINEMAS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ALL_CINEMAS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        newUser = cursorToUser(cursor);
        cursor.close();

        return newUser;
    }


    public void deleteMovie(Movie movie) {
        long id = movie.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ALL_CINEMAS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<Filters> getAllFilters() {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ALL_CINEMAS,
                allColumns, null, null, null, null, null);

        ArrayList<Filters> userList = new ArrayList<Filters>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Filters filters = cursorToUser(cursor);
            userList.add(filters);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return userList;
    }

    private Filters cursorToUser(Cursor cursor) {
        Filters filters = new Filters();
        filters.setCinemaFilter(cursor.getString(1));
        return filters;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
//        SQLiteDatabase db = helper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        database.delete(MySQLiteHelper.TABLE_ALL_CINEMAS, null, null);
    }
}
