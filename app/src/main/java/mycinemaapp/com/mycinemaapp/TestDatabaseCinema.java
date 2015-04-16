package mycinemaapp.com.mycinemaapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import database.MovieDaysDataSource;
import models.MovieDay;

/**
 * Created by kristian on 15-4-16.
 */
public class TestDatabaseCinema extends ListActivity {
    private MovieDaysDataSource movieDaysDataSource;
    ArrayAdapter<MovieDay> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_database);
        movieDaysDataSource = new MovieDaysDataSource(this);
        movieDaysDataSource.open();
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDaysDataSource.removeAll();
                adapter.notifyDataSetChanged();
            }
        });
        List<MovieDay> values = movieDaysDataSource.getAllMovieDayyy();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<MovieDay>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}