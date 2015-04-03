package mycinemaapp.com.mycinemaapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

import database.User;
import database.UsersDataSource;

/**
 * Created by kristian on 15-4-3.
 */
public class TestDatabase extends ListActivity {
    private UsersDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_database);
        datasource = new UsersDataSource(this);
        datasource.open();

        List<User> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
