package test.database;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import database.MoviesCinemasDataSource;
import models.MoviesCinemas;

/**
 * Created by kristian on 15-4-20.
 */
public class TestDatabaseMovieCinema extends ListActivity {

    private MoviesCinemasDataSource moviesCinemasDataSource;
    private ArrayAdapter<MoviesCinemas> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesCinemasDataSource = new MoviesCinemasDataSource(this);
        moviesCinemasDataSource.open();

        ArrayList<MoviesCinemas> values = moviesCinemasDataSource.getAllMoviesCinemas();

        adapter = new ArrayAdapter<MoviesCinemas>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
