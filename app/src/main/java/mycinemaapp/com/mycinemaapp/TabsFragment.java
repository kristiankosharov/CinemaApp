package mycinemaapp.com.mycinemaapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by kristian on 15-3-31.
 */
public class TabsFragment extends Fragment implements View.OnClickListener {
    private FragmentTabHost mFragmentTabHost;
    private Context context;
    private Button allDays, allCinemas, allGenres;

    TabsFragment(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabs_fragment, container, false);

//        mFragmentTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        allDays = (Button) view.findViewById(R.id.all_days);
        allCinemas = (Button) view.findViewById(R.id.all_cinemas);
        allGenres = (Button) view.findViewById(R.id.all_genres);
        allDays.setOnClickListener(this);
        allCinemas.setOnClickListener(this);
        allGenres.setOnClickListener(this);
//        mFragmentTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

//        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec("ALL DAYS").setIndicator("ALL DAYS"),
//                AllDaysFragment.class, null);
//        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec("ALL CINEMAS").setIndicator("ALL CINEMAS"),
//                AllCinemasFragment.class, null);
//        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec("ALL GENRES").setIndicator("ALL GENRES"),
//                AllGenresFragment.class, null);
//        mFragmentTabHost.setCurrentTab(0);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mFragmentTabHost = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_days:
                AllDaysFragment allDaysFragment = new AllDaysFragment();
                FragmentTransaction allDaysTransaction = getFragmentManager().beginTransaction();
                allDaysTransaction.addToBackStack("All Days Fragment");
                allDaysTransaction.add(R.id.content, allDaysFragment);
                allDaysTransaction.commit();
                break;
            case R.id.all_cinemas:
                AllCinemasFragment allCinemasFragment = new AllCinemasFragment();
                FragmentTransaction allCinemasTransaction = getFragmentManager().beginTransaction();
                allCinemasTransaction.addToBackStack("All Days Fragment");
                allCinemasTransaction.add(R.id.content, allCinemasFragment);
                allCinemasTransaction.commit();
                break;
            case R.id.all_genres:
                AllGenresFragment allGenresFragment = new AllGenresFragment();
                FragmentTransaction allGenresTransaction = getFragmentManager().beginTransaction();
                allGenresTransaction.addToBackStack("All Days Fragment");
                allGenresTransaction.add(R.id.content, allGenresFragment);
                allGenresTransaction.commit();
                break;
        }
    }
}
