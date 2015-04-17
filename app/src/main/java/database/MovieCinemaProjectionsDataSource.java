package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.MovieCinemaProjections;

/**
 * Created by kristian on 15-4-17.
 */
public class MovieCinemaProjectionsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_MOVIE_CINEMA_ID, MySQLiteHelper.COLUMN_DAY_OF_WEEK, MySQLiteHelper.COLUMN_STARTING_TIME};
    private Context con;

    public MovieCinemaProjectionsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.con = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public MovieCinemaProjections createMovieCinemaProjection(long movieCinemaId, String dayOfWeek, String startingTime) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_MOVIE_CINEMA_ID, movieCinemaId);
        values.put(MySQLiteHelper.COLUMN_DAY_OF_WEEK, dayOfWeek);
        values.put(MySQLiteHelper.COLUMN_STARTING_TIME, startingTime);
        long insertId = database.insert(MySQLiteHelper.TABLE_MOVIE_CINEMA_PROJECTIONS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MOVIE_CINEMA_PROJECTIONS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        MovieCinemaProjections movieCinemaProjections = cursorToUser(cursor);
        cursor.close();
        return movieCinemaProjections;
    }

    public void deleteActor(MovieCinemaProjections moviesCinemas) {
        long id = moviesCinemas.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_MOVIE_CINEMA_PROJECTIONS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<MovieCinemaProjections> getAllMovieCinemaProjections() {
        ArrayList<MovieCinemaProjections> movieCinemaProjectionses = new ArrayList<MovieCinemaProjections>();

        Cursor cursor = database.query
//                rawQuery("SELECT " + MySQLiteHelper.COLUMN_GENRES_TITLE +
//                " FROM " + MySQLiteHelper.TABLE_GENRES + " WHERE " + MySQLiteHelper.COLUMN_MOVIE_ID + " = ?", args);
                (MySQLiteHelper.TABLE_MOVIE_CINEMA_PROJECTIONS,
                        allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MovieCinemaProjections movieCinemaProjections = cursorToUser(cursor);
            movieCinemaProjectionses.add(movieCinemaProjections);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return movieCinemaProjectionses;
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

    private MovieCinemaProjections cursorToUser(Cursor cursor) {
        MovieCinemaProjections movieCinemaProjections = new MovieCinemaProjections();
        movieCinemaProjections.setId(cursor.getLong(0));
        movieCinemaProjections.setMovie_cinema_id(cursor.getLong(1));
        movieCinemaProjections.setDayOfWeek(cursor.getString(2));
        movieCinemaProjections.setStartingTime(cursor.getString(3));
        return movieCinemaProjections;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        database.delete(MySQLiteHelper.TABLE_MOVIE_CINEMA_PROJECTIONS, null, null);
    }
}
