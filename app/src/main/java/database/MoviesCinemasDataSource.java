package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.MoviesCinemas;

/**
 * Created by kristian on 15-4-17.
 */
public class MoviesCinemasDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_MOVIE_ID, MySQLiteHelper.COLUMN_CINEMA_ID, MySQLiteHelper.COLUMN_IS_ACTIVE};
    private Context con;

    public MoviesCinemasDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.con = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public MoviesCinemas createGenre(long movieId, long cinemaId, int isActive) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_MOVIE_ID, movieId);
        values.put(MySQLiteHelper.COLUMN_CINEMA_ID, cinemaId);
        values.put(MySQLiteHelper.COLUMN_IS_ACTIVE, isActive);
        long insertId = database.insert(MySQLiteHelper.TABLE_MOVIES_CINEMAS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MOVIES_CINEMAS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        MoviesCinemas moviesCinemas = cursorToUser(cursor);
        cursor.close();
        return moviesCinemas;
    }

    public void deleteActor(MoviesCinemas moviesCinemas) {
        long id = moviesCinemas.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_MOVIES_CINEMAS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<MoviesCinemas> getAllGenres() {
        ArrayList<MoviesCinemas> moviesCinemasArrayList = new ArrayList<MoviesCinemas>();

        Cursor cursor = database.query
//                rawQuery("SELECT " + MySQLiteHelper.COLUMN_GENRES_TITLE +
//                " FROM " + MySQLiteHelper.TABLE_GENRES + " WHERE " + MySQLiteHelper.COLUMN_MOVIE_ID + " = ?", args);
                (MySQLiteHelper.TABLE_MOVIES_CINEMAS,
                        allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MoviesCinemas moviesCinemas = cursorToUser(cursor);
            moviesCinemasArrayList.add(moviesCinemas);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return moviesCinemasArrayList;
    }

//    public ArrayList<MoviesCinemas> getAllGenres() {
//        ArrayList<MoviesCinemas> genresList = new ArrayList<MoviesCinemas>();
//
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_MOVIES_CINEMAS,
//                allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Genre genre = cursorToUser(cursor);
//            genresList.add(genre);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        return genresList;
//    }
//
//    private Genre cursorToUserFromId(Cursor cursor) {
//        Genre genre = new Genre();
//        genre.setTitle(cursor.getString(0));
//        return genre;
//    }

    private MoviesCinemas cursorToUser(Cursor cursor) {
        MoviesCinemas moviesCinemas = new MoviesCinemas();
        moviesCinemas.setId(cursor.getLong(0));
        moviesCinemas.setMovie_id(cursor.getLong(1));
        moviesCinemas.setCinema_id(cursor.getLong(2));
        moviesCinemas.setIsActive(cursor.getInt(3));
        return moviesCinemas;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        database.delete(MySQLiteHelper.TABLE_MOVIES_CINEMAS, null, null);
    }
}
