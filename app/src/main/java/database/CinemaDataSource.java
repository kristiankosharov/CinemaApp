package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Cinema;
import models.Movie;

/**
 * Created by kristian on 15-4-15.
 */
public class CinemaDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_MOVIE_ID, MySQLiteHelper.COLUMN_CINEMA_TITLE,
            MySQLiteHelper.COLUMN_CINEMA_LONGITUDE, MySQLiteHelper.COLUMN_CINEMA_LATITUDE};
    private Context con;

    public CinemaDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.con = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Cinema createCinema(int movieId, String cinemaTitle, float cinemaLongitude,
                               float cinemaLatitude) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CINEMA_TITLE, cinemaTitle);
        values.put(MySQLiteHelper.COLUMN_CINEMA_LONGITUDE, cinemaLongitude);
        values.put(MySQLiteHelper.COLUMN_CINEMA_LATITUDE, cinemaLatitude);

        long insertId = database.insert(MySQLiteHelper.TABLE_CINEMA, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CINEMA,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Cinema newCinema = cursorToUser(cursor);
        cursor.close();
        return newCinema;
    }

    public void deleteMovie(Movie movie) {
        long id = movie.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_CINEMA, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<Cinema> getAllCinemas() {
        ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CINEMA,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Cinema cinema = cursorToUser(cursor);
            cinemaList.add(cinema);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return cinemaList;
    }

//    public ArrayList<Movie> getAllMovieTitle() {
//        ArrayList<Movie> userList = new ArrayList<Movie>();
//
//        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_MOVIES + " GROUP BY " + MySQLiteHelper.COLUMN_MOVIE_TITLE, null);
////                database.query(MySQLiteHelper.TABLE_MOVIES,
////                allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Movie movie = cursorToUser(cursor);
//            userList.add(movie);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        return userList;
//    }

    private Cinema cursorToUser(Cursor cursor) {
        Cinema cinema = new Cinema();
        cinema.setCinemaId(cursor.getInt(0));
        cinema.setTitle(cursor.getString(1));
        cinema.setLongitude(cursor.getFloat(2));
        cinema.setLatitude(cursor.getFloat(3));
        return cinema;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        database.delete(MySQLiteHelper.TABLE_CINEMA, null, null);
    }
}
