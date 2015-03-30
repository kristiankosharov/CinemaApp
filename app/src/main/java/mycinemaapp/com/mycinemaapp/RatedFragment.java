package mycinemaapp.com.mycinemaapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Adapters.MovieAdapter;
import Models.RatedMovies;
import origamilabs.library.views.StaggeredGridView;

/**
 * Created by kristian on 15-2-26.
 */
public class RatedFragment extends Fragment {

    private MovieAdapter adapter;
    private StaggeredGridView mGridView;
    private TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rated_tab_fragment, container, false);

        adapter = new MovieAdapter(getActivity(), R.layout.movie_layout, RatedMovies.getRatedMovies(), false, true, false);
        mGridView = (StaggeredGridView) view.findViewById(R.id.scroll);
        emptyView = (TextView) view.findViewById(R.id.empty_list_view);
//        mGridView.setExpanded(true);

        if (adapter.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
        mGridView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
