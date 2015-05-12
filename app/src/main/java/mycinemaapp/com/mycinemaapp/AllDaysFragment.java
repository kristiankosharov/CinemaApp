package mycinemaapp.com.mycinemaapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.FilterAdapter;
import adapters.MovieAdapter;
import database.SQLite.AllDaysDataSource;
import models.Filters;

/**
 * Created by kristian on 15-3-31.
 */
public class AllDaysFragment extends Fragment implements View.OnClickListener {

    private FilterAdapter adapter;
    private ListView listView;
    private Button button;
    private TextView clear;
    private AllDaysDataSource allDaysDataSource;
    private static final int NUMBER_OF_COLUMN = 0;
    private MovieAdapter mainActivityArray;

    AllDaysFragment(Button button, MovieAdapter movieArrayList) {
        this.button = button;
        mainActivityArray = movieArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_days_fragment, container, false);


        clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("CLEAR", "clear");
                startActivity(intent);
            }
        });
        listView = (ListView) view.findViewById(R.id.list_view);
        ArrayList<Filters> allDays = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            Filters filters = new Filters();
            filters.setDayFilter(i + "");
            allDays.add(filters);
        }
        adapter = new FilterAdapter(getActivity(), allDays, button, "all days", mainActivityArray);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.master:
                break;
        }
    }
}
