package test.database;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import database.SQLite.ActorsDataSource;
import models.Actor;
import mycinemaapp.com.mycinemaapp.R;

/**
 * Created by kristian on 15-4-7.
 */
public class TestDatabaseFilters extends ListActivity {
    //    private AllDaysDataSource allDaysDataSource;
    private ActorsDataSource actorsDataSource;
    ArrayAdapter<Actor> adapter;
    List<Actor> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_database);
        actorsDataSource = new ActorsDataSource(this);
        actorsDataSource.open();
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actorsDataSource.removeAll();
                adapter = new ArrayAdapter<Actor>(TestDatabaseFilters.this,
                        android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
            }
        });
        values = actorsDataSource.getAllActors();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<Actor>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
