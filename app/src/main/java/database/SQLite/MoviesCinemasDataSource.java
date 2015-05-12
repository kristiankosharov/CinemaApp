package database.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Cinema;
import models.Genre;
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

    public MoviesCinemas createMovieCinema(long movieId, long cinemaId, int isActive) {
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

    public ArrayList<MoviesCinemas> getAllMoviesCinemas() {
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

    public int getSizeMoviesCinemasWithoutRepeat() {
        ArrayList<Genre> genresList = new ArrayList<Genre>();

        Cursor cursor = database.rawQuery("SELECT DISTINCT " + MySQLiteHelper.COLUMN_MOVIE_ID + " FROM " + MySQLiteHelper.TABLE_MOVIES_CINEMAS, null);
//                query(MySQLiteHelper.TABLE_MOVIES_CINEMAS,
//                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Genre genre = cursorToUserFromId(cursor);
            genresList.add(genre);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return genresList.size();
    }

    public ArrayList<Cinema> getAllCinemasFromId(long movieId) {
        ArrayList<Cinema> allCinemas = new ArrayList<>();

        String args[] = {movieId + ""};
        Cursor cursor = database.rawQuery("SELECT " + MySQLiteHelper.TABLE_CINEMA + "." + MySQLiteHelper.COLUMN_CINEMA_TITLE
                + " FROM " + MySQLiteHelper.TABLE_MOVIES_CINEMAS + " JOIN " + MySQLiteHelper.TABLE_CINEMA + " ON "
                + MySQLiteHelper.TABLE_MOVIES_CINEMAS + "." + MySQLiteHelper.COLUMN_CINEMA_ID + " = " + MySQLiteHelper.TABLE_CINEMA
                + "." + MySQLiteHelper.COLUMN_ID + " WHERE " + MySQLiteHelper.TABLE_MOVIES_CINEMAS + "." + MySQLiteHelper.COLUMN_MOVIE_ID
                + " = ?", args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Cinema cinema = cursorToCinemaFromId(cursor);

            allCinemas.add(cinema);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return allCinemas;
    }

    private Genre cursorToUserFromId(Cursor cursor) {
        Genre genre = new Genre();
        genre.setTitle(cursor.getString(0));
        return genre;
    }

    private MoviesCinemas cursorToUser(Cursor cursor) {
        MoviesCinemas moviesCinemas = new MoviesCinemas();
        moviesCinemas.setId(cursor.getLong(0));
        moviesCinemas.setMovie_id(cursor.getLong(1));
        moviesCinemas.setCinema_id(cursor.getLong(2));
        moviesCinemas.setIsActive(cursor.getInt(3));
        return moviesCinemas;
    }

    private Cinema cursorToCinemaFromId(Cursor cursor) {
        Cinema cinema = new Cinema();
        cinema.setTitle(cursor.getString(0));
        return cinema;
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
