package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Actor;

/**
 * Created by kristian on 15-4-16.
 */
public class ActorsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_MOVIE_ID, MySQLiteHelper.COLUMN_ACTORS_TITLE};
    private Context con;

    public ActorsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.con = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Actor createActor(long movieId, String actorTitle) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_MOVIE_ID, movieId);
        values.put(MySQLiteHelper.COLUMN_ACTORS_TITLE, actorTitle);
        long insertId = database.insert(MySQLiteHelper.TABLE_ACTORS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ACTORS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Actor newActor = cursorToUser(cursor);
        cursor.close();
        return newActor;
    }

    public void deleteActor(Actor movie) {
        long id = movie.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ACTORS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<Actor> getAllActors(long movieId) {
        ArrayList<Actor> actorsList = new ArrayList<Actor>();
        String args[] = {movieId + ""};

        Cursor cursor = database.rawQuery("SELECT " + MySQLiteHelper.COLUMN_ACTORS_TITLE +
                " FROM " + MySQLiteHelper.TABLE_ACTORS + " WHERE " + MySQLiteHelper.COLUMN_MOVIE_ID + " = ?", args);
//        (MySQLiteHelper.TABLE_CINEMA,
//                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Actor actor = cursorToUserFromId(cursor);
            actorsList.add(actor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return actorsList;
    }

    public ArrayList<Actor> getAllActors() {
        ArrayList<Actor> actorsList = new ArrayList<Actor>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ACTORS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Actor actor = cursorToUser(cursor);
            actorsList.add(actor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return actorsList;
    }

    private Actor cursorToUserFromId(Cursor cursor) {
        Actor actor = new Actor();
        actor.setTitle(cursor.getString(0));
        return actor;
    }

    private Actor cursorToUser(Cursor cursor) {
        Actor actor = new Actor();
        actor.setId(cursor.getLong(0));
        actor.setMovie_id(cursor.getLong(1));
        actor.setTitle(cursor.getString(2));
        return actor;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        database.delete(MySQLiteHelper.TABLE_ACTORS, null, null);
    }
}
