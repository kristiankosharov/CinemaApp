package adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mycinemaapp.com.mycinemaapp.ListOfMoviewFragment;
import mycinemaapp.com.mycinemaapp.RatedFragment;
import mycinemaapp.com.mycinemaapp.TicketFragment;

/**
 * Created by kristian on 15-2-26.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private int movieCount;

    public TabsPagerAdapter(FragmentManager fm,String[] title,int movieCount){
        super(fm);
        this.titles = title;
        this.movieCount = movieCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ListOfMoviewFragment();
            case 1:
                return new TicketFragment();
            case 2:
                return new RatedFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return movieCount;
    }

    @Override
    public String getPageTitle(int position) {
        return titles[position];
    }
}
