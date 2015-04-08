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
import database.AllDaysDataSource;
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

    AllDaysFragment(Button button) {
        this.button = button;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_days_fragment, container, false);
        allDaysDataSource = new AllDaysDataSource(getActivity());
        allDaysDataSource.open();

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
        ArrayList<Filters> list = allDaysDataSource.getAllFilters();
//        Toast.makeText(getActivity(), list.toString(), Toast.LENGTH_LONG).show();
//        for (int i = 0; i < 100; i++) {
//            if (i == 0) {
//                Filter item = new Filter();
//                item.setFilter("ALL DAYS");
//                list.add(item);
//            } else {
//                Filter item = new Filter();
//                item.setFilter("FILTER" + i);
//                list.add(item);
//            }
//        }
//        AllDaysFilters.setAllDays(list);
        adapter = new FilterAdapter(getActivity(), list, button, "all days");
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
