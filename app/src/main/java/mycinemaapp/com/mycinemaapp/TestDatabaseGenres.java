package mycinemaapp.com.mycinemaapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import database.GenresDataSource;
import models.Genre;

/**
 * Created by kristian on 15-4-16.
 */
public class TestDatabaseGenres extends ListActivity {
    private GenresDataSource genresDataSource;
    ArrayAdapter<Genre> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_database);
        genresDataSource = new GenresDataSource(this);
        genresDataSource.open();
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genresDataSource.removeAll();
                adapter.notifyDataSetChanged();
            }
        });
        List<Genre> values = genresDataSource.getAllGenres();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<Genre>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
