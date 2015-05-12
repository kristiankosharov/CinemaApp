//package database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import java.util.ArrayList;
//
//import models.Movie;
//import models.MovieDay;
//
///**
// * Created by kristian on 15-4-16.
// */
//public class MovieDaysDataSource {
//    // Database fields
//    private SQLiteDatabase database;
//    private MySQLiteHelper dbHelper;
//    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
//            MySQLiteHelper.COLUMN_MOVIE_DAYS_DAY_ID, MySQLiteHelper.COLUMN_MOVIE_ID};
//    private Context con;
//
//    public MovieDaysDataSource(Context context) {
//        dbHelper = new MySQLiteHelper(context);
//        this.con = context;
//    }
//
//    public void open() throws SQLException {
//        database = dbHelper.getWritableDatabase();
//    }
//
//    public void close() {
//        dbHelper.close();
//    }
//
//    public void createMovieDay(long dayId, long movieId) {
//        ContentValues values = new ContentValues();
//        values.put(MySQLiteHelper.COLUMN_MOVIE_DAYS_DAY_ID, dayId);
//        values.put(MySQLiteHelper.COLUMN_MOVIE_ID, movieId);
//        long insertId = database.insert(MySQLiteHelper.TABLE_MOVIE_DAYS, null,
//                values);
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_MOVIE_DAYS,
//                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//                null, null, null);
////        cursor.moveToFirst();
////        MovieDay newMovieDay = cursorToUser(cursor);
//        cursor.close();
////        return newMovieDay;
//    }
//
//    public void deleteMovieDay(MovieDay movieDay) {
//        long id = movieDay.getId();
////        System.out.println("Comment deleted with id: " + id);
//        database.delete(MySQLiteHelper.TABLE_MOVIE_DAYS, MySQLiteHelper.COLUMN_ID
//                + " = " + id, null);
//    }
//
//    public ArrayList<Movie> getAllMovieDay(String day) {
//        ArrayList<Movie> genresList = new ArrayList<Movie>();
//        String args[] = {day};
//
//        Cursor cursor = database.rawQuery(
////                rawQuery("SELECT * FROM movie_days JOIN movies ON " +
////                        "movie_days.movie_id=movies._id JOIN alldays ON movie_days.day_id=alldays._id WHERE alldays.daysfilter = \"01.04\";", null
//                "SELECT * FROM " + MySQLiteHelper.TABLE_MOVIE_DAYS + " JOIN "
//                        + MySQLiteHelper.TABLE_MOVIES + " ON " + MySQLiteHelper.TABLE_MOVIE_DAYS + "."
//                        + MySQLiteHelper.COLUMN_MOVIE_ID + " = " + MySQLiteHelper.TABLE_MOVIES + "." + MySQLiteHelper.COLUMN_ID
//                        + " JOIN " + MySQLiteHelper.TABLE_ALL_DAYS + " ON " + MySQLiteHelper.TABLE_MOVIE_DAYS + "."
//                        + MySQLiteHelper.COLUMN_MOVIE_DAYS_DAY_ID + " = " + MySQLiteHelper.TABLE_ALL_DAYS + "."
//                        + MySQLiteHelper.COLUMN_ID +
//                        " JOIN " + MySQLiteHelper.TABLE_CINEMA + " ON " + MySQLiteHelper.TABLE_CINEMA+"."+MySQLiteHelper
//
//
//                        + " WHERE " + MySQLiteHelper.TABLE_ALL_DAYS + "."
//                        + MySQLiteHelper.COLUMN_DAYS_FILTER + " = ?;", args
//        );
////                query(MySQLiteHelper.TABLE_MOVIE_DAYS,
////                allColumns, null, null, null, null, null);
//        Log.d("CURSOR", cursor.getCount() + "");
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Movie movieDay = cursorToUser(cursor);
//            genresList.add(movieDay);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        return genresList;
//    }
//
//    public ArrayList<MovieDay> getAllMovieDayyy() {
//        ArrayList<MovieDay> genresList = new ArrayList<MovieDay>();
//
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_MOVIE_DAYS,
//                allColumns, null, null, null, null, null);
//        Log.d("CURSOR", cursor.getCount() + "");
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            MovieDay movieDay = cursorToUserrr(cursor);
//            genresList.add(movieDay);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        return genresList;
//    }
//
////    private Genre cursorToUserFromId(Cursor cursor) {
////        Genre genre = new Genre();
////        genre.setTitle(cursor.getString(0));
////        return genre;
////    }
//
//    private Movie cursorToUser(Cursor cursor) {
//        Movie movie = new Movie();
//        movie.setMovieTitle(cursor.getString(4));
//        movie.setMovieProgress(cursor.getFloat(5));
//        movie.setImageUrl(cursor.getString(6));
//        movie.setDuration(cursor.getInt(7));
//        movie.setImdbUrl(cursor.getString(8));
//        movie.setFullDescription(cursor.getString(9));
//        movie.setMovieDirectors(cursor.getString(10));
//        movie.setReleaseDate(cursor.getString(11));
//        movie.setNewForWeek(cursor.getString(12));
//
//        return movie;
//    }
//
//    private MovieDay cursorToUserrr(Cursor cursor) {
//        MovieDay movie = new MovieDay();
//        movie.setId(cursor.getLong(0));
//        movie.setDay_id(cursor.getLong(1));
//        movie.setMovie_id(cursor.getLong(2));
//
//        return movie;
//    }
//
//    /**
//     * Remove all users and groups from database.
//     */
//    public void removeAll() {
//        // db.delete(String tableName, String whereClause, String[] whereArgs);
//        // If whereClause is null, it will delete all rows.
//        database.delete(MySQLiteHelper.TABLE_MOVIE_DAYS, null, null);
//    }
//}
