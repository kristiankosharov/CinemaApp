package models;

import java.util.ArrayList;

/**
 * Created by kristian on 15-4-1.
 */
public class AllDaysFilters {
    public static ArrayList<Filters> allDays = new ArrayList<>();

    public static ArrayList<Filters> getAllDays() {
        return allDays;
    }

    public static void setAllDays(ArrayList<Filters> addDay) {
        for (int i = 0; i < addDay.size(); i++) {
            allDays.add(addDay.get(i));
        }
    }

    public static void setNewItem(Filters movie) {
        allDays.add(movie);
    }

    public static Filters getItem(int position) {
        return allDays.get(position);
    }
}
