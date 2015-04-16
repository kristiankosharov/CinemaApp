package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Genre;

/**
 * Created by kristian on 15-4-16.
 */
public class GenresDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_MOVIE_ID, MySQLiteHelper.COLUMN_GENRES_TITLE};
    private Context con;

    public GenresDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.con = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Genre createGenre(long movieId, String genreTitle) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_MOVIE_ID, movieId);
        values.put(MySQLiteHelper.COLUMN_GENRES_TITLE, genreTitle);
        long insertId = database.insert(MySQLiteHelper.TABLE_GENRES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_GENRES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Genre newGenre = cursorToUser(cursor);
        cursor.close();
        return newGenre;
    }

    public void deleteActor(Genre movie) {
        long id = movie.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_GENRES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<Genre> getAllGenres(long movieId) {
        ArrayList<Genre> genresList = new ArrayList<Genre>();
        String args[] = {movieId + ""};

        Cursor cursor = database.rawQuery("SELECT " + MySQLiteHelper.COLUMN_GENRES_TITLE +
                " FROM " + MySQLiteHelper.TABLE_GENRES + " WHERE " + MySQLiteHelper.COLUMN_MOVIE_ID + " = ?", args);
//        (MySQLiteHelper.TABLE_CINEMA,
//                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Genre genre = cursorToUserFromId(cursor);
            genresList.add(genre);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return genresList;
    }

    public ArrayList<Genre> getAllGenres() {
        ArrayList<Genre> genresList = new ArrayList<Genre>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_GENRES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Genre genre = cursorToUser(cursor);
            genresList.add(genre);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return genresList;
    }

    private Genre cursorToUserFromId(Cursor cursor) {
        Genre genre = new Genre();
        genre.setTitle(cursor.getString(0));
        return genre;
    }

    private Genre cursorToUser(Cursor cursor) {
        Genre genre = new Genre();
        genre.setId(cursor.getLong(0));
        genre.setMovie_id(cursor.getLong(1));
        genre.setTitle(cursor.getString(2));
        return genre;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        database.delete(MySQLiteHelper.TABLE_GENRES, null, null);
    }
}
