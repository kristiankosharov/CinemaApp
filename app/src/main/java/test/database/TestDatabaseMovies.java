package test.database;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import database.SQLite.MovieDataSource;
import models.Movie;
import mycinemaapp.com.mycinemaapp.R;

/**
 * Created by kristian on 15-4-7.
 */
public class TestDatabaseMovies extends ListActivity {
    private MovieDataSource datasource;
    ArrayAdapter<Movie> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_database);
        datasource = new MovieDataSource(this);
        datasource.open();
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.removeAll();
                adapter.notifyDataSetChanged();
            }
        });
        List<Movie> values = datasource.getAllMovie();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<Movie>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
