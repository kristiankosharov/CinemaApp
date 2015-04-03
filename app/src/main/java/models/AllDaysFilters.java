package models;

import java.util.ArrayList;

/**
 * Created by kristian on 15-4-1.
 */
public class AllDaysFilters {
    public static ArrayList<Filter> allDays = new ArrayList<>();

    public static ArrayList<Filter> getAllDays() {
        return allDays;
    }

    public static void setAllDays(ArrayList<Filter> addDay) {
        for (int i = 0; i < addDay.size(); i++) {
            allDays.add(addDay.get(i));
        }
    }

    public static void setNewItem(Filter movie) {
        allDays.add(movie);
    }

    public static Filter getItem(int position) {
        return allDays.get(position);
    }
}
