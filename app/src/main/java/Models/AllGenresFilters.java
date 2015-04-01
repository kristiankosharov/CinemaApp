package Models;

import java.util.ArrayList;

/**
 * Created by kristian on 15-4-1.
 */
public class AllGenresFilters {
    public static ArrayList<Filter> allGenres = new ArrayList<>();

    public static ArrayList<Filter> getAllGenres() {
        return allGenres;
    }

    public static void setAllGenres(ArrayList<Filter> addDay) {
        for (int i = 0; i < addDay.size(); i++) {
            allGenres.add(addDay.get(i));
        }
    }

    public static void setNewItem(Filter movie) {
        allGenres.add(movie);
    }

    public static Filter getItem(int position) {
        return allGenres.get(position);
    }
}
