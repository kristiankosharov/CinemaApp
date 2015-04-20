package test.database;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import database.MovieCinemaProjectionsDataSource;
import models.MovieCinemaProjections;

/**
 * Created by kristian on 15-4-20.
 */
public class TestDatabaseMovieCinemaProjections extends ListActivity {
    private MovieCinemaProjectionsDataSource movieCinemaProjections;
    private ArrayAdapter<MovieCinemaProjections> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieCinemaProjections = new MovieCinemaProjectionsDataSource(this);
        movieCinemaProjections.open();

        ArrayList<MovieCinemaProjections> values = movieCinemaProjections.getAllMovieCinemaProjections();

        adapter = new ArrayAdapter<MovieCinemaProjections>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
