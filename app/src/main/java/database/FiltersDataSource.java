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
 * Created by kristian on 15-4-7.
 */
public class FiltersDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_DAYS_FILTER, MySQLiteHelper.COLUMN_CINEMAS_FILTER, MySQLiteHelper.COLUMN_GENRES_FILTER};
    private Context con;

    public FiltersDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.con = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Filters createFilter(String dayFilter, String cinemaFilter, String genreFilter) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DAYS_FILTER, dayFilter);
        values.put(MySQLiteHelper.COLUMN_CINEMAS_FILTER, cinemaFilter);
        values.put(MySQLiteHelper.COLUMN_GENRES_FILTER, genreFilter);
        long insertId = database.insert(MySQLiteHelper.TABLE_FILTERS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILTERS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Filters newUser = cursorToUser(cursor);
        cursor.close();
        return newUser;
    }

    public void deleteMovie(Movie movie) {
        long id = movie.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FILTERS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<Filters> getAllFilters() {
        ArrayList<Filters> userList = new ArrayList<Filters>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILTERS,
                allColumns, null, null, null, null, null);

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
        filters.setDayFilter(cursor.getString(1));
        filters.setCinemaFilter(cursor.getString(2));
        filters.setGenreFilter(cursor.getString(3));
        return filters;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
//        SQLiteDatabase db = helper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        database.delete(MySQLiteHelper.TABLE_FILTERS, null, null);
    }
}
