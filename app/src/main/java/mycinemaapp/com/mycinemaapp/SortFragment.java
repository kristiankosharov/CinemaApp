package mycinemaapp.com.mycinemaapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import test.database.TestDatabaseCinema;
import test.database.TestDatabaseMovieCinema;
import test.database.TestDatabaseMovieCinemaProjections;
import test.database.TestDatabaseMovieGenres;

/**
 * Created by kristian on 15-3-19.
 */
public class SortFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout master;
    private ImageButton alphabeticSort, popularitySort, releaseSort, ratingSort;
    private static String clickedButton = "alphabetic";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_layout, container, false);
        master = (RelativeLayout) view.findViewById(R.id.container);
        master.setOnClickListener(this);
        alphabeticSort = (ImageButton) view.findViewById(R.id.alphabetic_sort);
        alphabeticSort.setOnClickListener(this);
        popularitySort = (ImageButton) view.findViewById(R.id.popularity_sort);
        popularitySort.setOnClickListener(this);
        releaseSort = (ImageButton) view.findViewById(R.id.release_date);
        releaseSort.setOnClickListener(this);
        ratingSort = (ImageButton) view.findViewById(R.id.rating_sort);
        ratingSort.setOnClickListener(this);

        if (clickedButton != null && clickedButton.equals("alphabetic")) {
            alphabeticSort.setBackgroundResource(R.drawable.circul_border_sort_yellow);
        } else if (clickedButton != null && clickedButton.equals("popularity")) {
            popularitySort.setBackgroundResource(R.drawable.circul_border_sort_yellow);
        } else if (clickedButton != null && clickedButton.equals("release")) {
            releaseSort.setBackgroundResource(R.drawable.circul_border_sort_yellow);
        } else {
            ratingSort.setBackgroundResource(R.drawable.circul_border_sort_yellow);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.container:
                getFragmentManager().popBackStack();
                break;
            case R.id.alphabetic_sort:
                clickedButton = "alphabetic";
                Intent intent = new Intent(getActivity(), TestDatabaseCinema.class);
                startActivity(intent);
//                getFragmentManager().popBackStack();
                break;
            case R.id.popularity_sort:
                clickedButton = "popularity";
                Intent intent1 = new Intent(getActivity(), TestDatabaseMovieCinemaProjections.class);
                startActivity(intent1);
//                getFragmentManager().popBackStack();
                break;
            case R.id.release_date:
                clickedButton = "release";
                Intent intent2 = new Intent(getActivity(), TestDatabaseMovieGenres.class);
                startActivity(intent2);
//                getFragmentManager().popBackStack();
                break;
            case R.id.rating_sort:
                clickedButton = "rating";
                Intent intent3 = new Intent(getActivity(), TestDatabaseMovieCinema.class);
                startActivity(intent3);
//                getFragmentManager().popBackStack();
                break;
        }
    }
}
