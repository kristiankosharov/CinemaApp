package test.database;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

import database.SQLite.GenresDataSource;
import models.Genre;

/**
 * Created by kristian on 15-4-16.
 */
public class TestDatabaseGenres extends ListActivity {

    private GenresDataSource genresDataSource;
    private ArrayAdapter<Genre> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        genresDataSource = new GenresDataSource(this);
        genresDataSource.open();
        List<Genre> values = genresDataSource.getAllGenres();

        adapter = new ArrayAdapter<Genre>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);


    }
}
