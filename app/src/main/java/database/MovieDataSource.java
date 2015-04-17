package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Movie;

/**
 * Created by kristian on 15-4-7.
 */
public class MovieDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_MOVIE_TITLE, MySQLiteHelper.COLUMN_MOVIE_RATE,
            MySQLiteHelper.COLUMN_MOVIE_IMG_URL, MySQLiteHelper.COLUMN_MOVIE_DURATION,
            MySQLiteHelper.COLUMN_MOVIE_IMDB, MySQLiteHelper.COLUMN_MOVIE_DESCRIPTION,
            MySQLiteHelper.COLUMN_MOVIE_DIRECTOR, MySQLiteHelper.COLUMN_MOVIE_RELEASE_DATE,
            MySQLiteHelper.COLUMN_MOVIE_NEW_THIS_WEEK};
    private Context con;

    public MovieDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.con = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Movie createMovie(String movieTitle, float movieProgress, String movieImgUrl,
                             int movieDuration, String movieImdbUrl, String movieDescription,
                             String movieDirector, String price, String movieReleaseDate, String movieNewThisWeek) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_MOVIE_TITLE, movieTitle);
        values.put(MySQLiteHelper.COLUMN_MOVIE_RATE, movieProgress);
        values.put(MySQLiteHelper.COLUMN_MOVIE_IMG_URL, movieImgUrl);
        values.put(MySQLiteHelper.COLUMN_MOVIE_DURATION, movieDuration);
        values.put(MySQLiteHelper.COLUMN_MOVIE_IMDB, movieImdbUrl);
        values.put(MySQLiteHelper.COLUMN_MOVIE_DESCRIPTION, movieDescription);
        values.put(MySQLiteHelper.COLUMN_MOVIE_DIRECTOR, movieDirector);
        values.put(MySQLiteHelper.COLUMN_MOVIE_PRICE, price);
        values.put(MySQLiteHelper.COLUMN_MOVIE_RELEASE_DATE, movieReleaseDate);
        values.put(MySQLiteHelper.COLUMN_MOVIE_NEW_THIS_WEEK, movieNewThisWeek);
        long insertId = database.insert(MySQLiteHelper.TABLE_MOVIES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MOVIES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Movie newUser = cursorToUser(cursor);
        cursor.close();
        return newUser;
    }

    public void deleteMovie(Movie movie) {
        long id = movie.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_MOVIES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<Movie> getAllMovie() {
        ArrayList<Movie> userList = new ArrayList<Movie>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MOVIES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Movie movie = cursorToUser(cursor);
            userList.add(movie);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return userList;
    }

    public ArrayList<Movie> getAllMovieTitle() {
        ArrayList<Movie> userList = new ArrayList<Movie>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_MOVIES + " GROUP BY " + MySQLiteHelper.COLUMN_MOVIE_TITLE, null);
//                database.query(MySQLiteHelper.TABLE_MOVIES,
//                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Movie movie = cursorToUser(cursor);
            userList.add(movie);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return userList;
    }

    private Movie cursorToUser(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getLong(0));
        movie.setMovieTitle(cursor.getString(1));
        movie.setMovieProgress(cursor.getFloat(2));
        movie.setImageUrl(cursor.getString(3));
        movie.setDuration(cursor.getInt(4));
        movie.setImdbUrl(cursor.getString(5));
        movie.setFullDescription(cursor.getString(6));
        movie.setMovieDirectors(cursor.getString(7));
        movie.setPrice(cursor.getString(8));
        movie.setReleaseDate(cursor.getString(9));
        movie.setNewForWeek(cursor.getString(10));
        return movie;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        database.delete(MySQLiteHelper.TABLE_MOVIES, null, null);
    }
}
