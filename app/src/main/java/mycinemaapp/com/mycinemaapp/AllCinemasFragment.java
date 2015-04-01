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

import Adapters.FilterAdapter;
import Models.AllCinemasFilters;
import Models.Filter;

/**
 * Created by kristian on 15-3-31.
 */
public class AllCinemasFragment extends Fragment {

    private ListView listView;
    private FilterAdapter adapter;
    private Button button;
    private TextView clear;

    AllCinemasFragment(Button button) {
        this.button = button;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_cinemas_fragment, container, false);
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
        ArrayList<Filter> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            if (i == 0) {
                Filter item = new Filter();
                item.setFilter("ALL CINEMAS");
                list.add(item);
            } else {
                Filter item = new Filter();
                item.setFilter("FILTER" + i);
                list.add(item);
            }
        }
        AllCinemasFilters.setAllCinemas(list);
        adapter = new FilterAdapter(getActivity(), AllCinemasFilters.allCinemas, button, "all cinemas");
        listView.setAdapter(adapter);


        return view;
    }
}
