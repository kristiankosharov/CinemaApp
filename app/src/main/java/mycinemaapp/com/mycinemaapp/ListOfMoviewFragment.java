package mycinemaapp.com.mycinemaapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Adapters.MovieAdapter;
import Models.AddMovies;
import origamilabs.library.views.StaggeredGridView;

/**
 * Created by kristian on 15-2-26.
 */
public class ListOfMoviewFragment extends Fragment {


    private MovieAdapter adapter;
    private StaggeredGridView mGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_tab_fragment, container, false);

        adapter = new MovieAdapter(getActivity(), R.layout.movie_layout, AddMovies.getAddMovie(), true, false, false);
        mGridView = (StaggeredGridView) view.findViewById(R.id.scroll);
//        mGridView.setExpanded(true);
        mGridView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
