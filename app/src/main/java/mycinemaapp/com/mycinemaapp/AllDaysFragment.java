package mycinemaapp.com.mycinemaapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import Adapters.FilterAdapter;
import Models.Filter;

/**
 * Created by kristian on 15-3-31.
 */
public class AllDaysFragment extends Fragment implements View.OnClickListener {

    FilterAdapter adapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_days_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.list_view);

        ArrayList<Filter> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Filter item = new Filter();
            item.setFilter("FILTER" + i);
            list.add(item);
        }
        adapter = new FilterAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.master:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
