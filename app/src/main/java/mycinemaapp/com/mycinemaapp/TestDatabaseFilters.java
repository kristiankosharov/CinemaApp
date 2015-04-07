package mycinemaapp.com.mycinemaapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import database.FiltersDataSource;
import models.Filters;

/**
 * Created by kristian on 15-4-7.
 */
public class TestDatabaseFilters extends ListActivity {
    private FiltersDataSource filtersDataSource;
    ArrayAdapter<Filters> adapter;
    List<Filters> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_database);
        filtersDataSource = new FiltersDataSource(this);
        filtersDataSource.open();
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtersDataSource.removeAll();
                adapter = new ArrayAdapter<Filters>(TestDatabaseFilters.this,
                        android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
            }
        });
        values = filtersDataSource.getAllFilters();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<Filters>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
