package test.database;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import database.CinemaDataSource;
import models.Cinema;
import mycinemaapp.com.mycinemaapp.R;

/**
 * Created by kristian on 15-4-16.
 */
public class TestDatabaseCinema extends ListActivity {
    ArrayAdapter<Cinema> adapter;
    private CinemaDataSource cinemaDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_database);
        cinemaDataSource = new CinemaDataSource(this);
        cinemaDataSource.open();
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cinemaDataSource.removeAll();
                adapter.notifyDataSetChanged();
            }
        });
        List<Cinema> values = cinemaDataSource.getAllCinemas();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<Cinema>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}