package models;

import java.util.ArrayList;

/**
 * Created by kristian on 15-4-1.
 */
public class AllCinemasFilters {
    public static ArrayList<Filters> allCinemas = new ArrayList<>();

    public static ArrayList<Filters> getAllCinemas() {
        return allCinemas;
    }

    public static void setAllCinemas(ArrayList<Filters> addCinemas) {
        for (int i = 0; i < addCinemas.size(); i++) {
            allCinemas.add(addCinemas.get(i));
        }
    }

    public static void setNewItem(Filters movie) {
        allCinemas.add(movie);
    }

    public static Filters getItem(int position) {
        return allCinemas.get(position);
    }
}
