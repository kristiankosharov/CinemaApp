package database.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import models.Genre;
import models.MovieGenre;

/**
 * Created by kristian on 15-4-20.
 */
public class MoviesGenresDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_MOVIE_ID, MySQLiteHelper.COLUMN_GENRES_ID};
    private Context con;

    public MoviesGenresDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.con = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public MovieGenre createMovieGenre(long movieId, int genreId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_MOVIE_ID, movieId);
        values.put(MySQLiteHelper.COLUMN_GENRES_ID, genreId);
        long insertId = database.insert(MySQLiteHelper.TABLE_MOVIES_GENRES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MOVIES_GENRES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        MovieGenre newGenre = cursorToUser(cursor);
        cursor.close();
        return newGenre;
    }

    public void deleteMovieGenre(MovieGenre movie) {
        long id = movie.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_MOVIES_GENRES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<MovieGenre> getAllMoviesGenres() {
        ArrayList<MovieGenre> genresList = new ArrayList<MovieGenre>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MOVIES_GENRES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MovieGenre genre = cursorToUser(cursor);
            genresList.add(genre);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return genresList;
    }

    public ArrayList<Genre> getAllGenreFromId(long movieId) {
        ArrayList<Genre> genres = new ArrayList<>();

        String args[] = {movieId + ""};

        Cursor cursor = database.rawQuery("SELECT " + MySQLiteHelper.TABLE_GENRES + "." + MySQLiteHelper.COLUMN_GENRES_TITLE
                + " FROM " + MySQLiteHelper.TABLE_MOVIES_GENRES + " JOIN " + MySQLiteHelper.TABLE_GENRES + " ON "
                + MySQLiteHelper.TABLE_MOVIES_GENRES + "." + MySQLiteHelper.COLUMN_GENRES_ID + " = " + MySQLiteHelper.TABLE_GENRES
                + "." + MySQLiteHelper.COLUMN_ID + " WHERE " + MySQLiteHelper.TABLE_MOVIES_GENRES + "." + MySQLiteHelper.COLUMN_MOVIE_ID
                + " = ?", args);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Genre genre = cursorToGenre(cursor);
            Log.d("OBJECT", genre.toString());
            genres.add(genre);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return genres;
    }

    private Genre cursorToGenre(Cursor cursor){
        Genre genre = new Genre();
        genre.setTitle(cursor.getString(0));
        return genre;
    }


    private MovieGenre cursorToUser(Cursor cursor) {
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setId(cursor.getLong(0));
        movieGenre.setMovie_id(cursor.getLong(1));
        movieGenre.setGenre_id(cursor.getLong(2));
        return movieGenre;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        database.delete(MySQLiteHelper.TABLE_MOVIES_GENRES, null, null);
    }
}
