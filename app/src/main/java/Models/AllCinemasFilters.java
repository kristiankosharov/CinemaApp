package Models;

import java.util.ArrayList;

/**
 * Created by kristian on 15-4-1.
 */
public class AllCinemasFilters {
    public static ArrayList<Filter> allCinemas = new ArrayList<>();

    public static ArrayList<Filter> getAllCinemas() {
        return allCinemas;
    }

    public static void setAllCinemas(ArrayList<Filter> addCinemas) {
        for (int i = 0; i < addCinemas.size(); i++) {
            allCinemas.add(addCinemas.get(i));
        }
    }

    public static void setNewItem(Filter movie) {
        allCinemas.add(movie);
    }

    public static Filter getItem(int position) {
        return allCinemas.get(position);
    }
}
