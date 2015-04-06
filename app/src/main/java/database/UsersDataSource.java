package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kristian on 15-4-3.
 */
public class UsersDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_USER_NAME, MySQLiteHelper.COLUMN_USER_EMAIL, MySQLiteHelper.COLUMN_USER_PASSWORD, MySQLiteHelper.COLUMN_USER_IMAGE_PATH};
    private Context con;

    public UsersDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.con = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public User createUsers(String userName, String userEmail, String pass, String imagePath) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USER_NAME, userName);
        values.put(MySQLiteHelper.COLUMN_USER_EMAIL, userEmail);
        values.put(MySQLiteHelper.COLUMN_USER_PASSWORD, pass);
        values.put(MySQLiteHelper.COLUMN_USER_IMAGE_PATH, imagePath);
        long insertId = database.insert(MySQLiteHelper.TABLE_USERS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        User newUser = cursorToUser(cursor);
        cursor.close();
        return newUser;
    }

    public void deleteUser(User user) {
        long id = user.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_USERS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<User> getAllComments() {
        List<User> userList = new ArrayList<User>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            userList.add(user);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return userList;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setUserName(cursor.getString(1));
        user.setUserEmail(cursor.getString(2));
        user.setPassword(cursor.getString(3));
        user.setImagePath(cursor.getString(4));
        return user;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
//        SQLiteDatabase db = helper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        database.delete(MySQLiteHelper.TABLE_USERS, null, null);
    }

}
