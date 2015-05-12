package test.database;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import database.SQLite.MoviesGenresDataSource;
import models.MovieGenre;

/**
 * Created by kristian on 15-4-20.
 */
public class TestDatabaseMovieGenres extends ListActivity {

    private MoviesGenresDataSource moviesGenresDataSource;
    private ArrayAdapter<MovieGenre> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesGenresDataSource = new MoviesGenresDataSource(this);
        moviesGenresDataSource.open();

        ArrayList<MovieGenre> values = moviesGenresDataSource.getAllMoviesGenres();

        adapter = new ArrayAdapter<MovieGenre>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
