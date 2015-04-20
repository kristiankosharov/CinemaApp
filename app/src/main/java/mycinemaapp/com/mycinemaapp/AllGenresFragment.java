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
import database.GenresDataSource;
import models.Filters;
import models.Genre;

/**
 * Created by kristian on 15-3-31.
 */
public class AllGenresFragment extends Fragment {

    private ListView listView;
    private FilterAdapter adapter;
    private Button button;
    private TextView clear;
    private static final int NUMBER_OF_COLUMN = 2;
    private GenresDataSource genresDataSource;

    AllGenresFragment(Button button) {
        this.button = button;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_genres_fragment, container, false);
        clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("CLEAR", "clear");
                startActivity(intent);
            }
        });
        genresDataSource = new GenresDataSource(getActivity());
        genresDataSource.open();
        listView = (ListView) view.findViewById(R.id.list_view);
        ArrayList<Genre> list = new ArrayList<>();
        list = genresDataSource.getAllGenres();
        ArrayList<Filters> allGenres = new ArrayList<>();
         for(int i=0;i<list.size();i++){
             Filters filters = new Filters();
             filters.setGenreFilter(list.get(i).getTitle());
             allGenres.add(filters);
         }

        adapter = new FilterAdapter(getActivity(), allGenres, button, "all genres", null);
        listView.setAdapter(adapter);

        return view;
    }
}
