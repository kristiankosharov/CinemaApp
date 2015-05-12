package test.database;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import database.SQLite.User;
import database.SQLite.UsersDataSource;
import mycinemaapp.com.mycinemaapp.R;

/**
 * Created by kristian on 15-4-3.
 */
public class TestDatabaseUsers extends ListActivity {
    private UsersDataSource datasource;
    ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_database);
        datasource = new UsersDataSource(this);
        datasource.open();
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.removeAll();
                adapter.notifyDataSetChanged();
            }
        });
        List<User> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<User>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
