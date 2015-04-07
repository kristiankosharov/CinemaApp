package models;

import java.util.ArrayList;

/**
 * Created by kristian on 15-4-1.
 */
public class AllGenresFilters {
    public static ArrayList<Filters> allGenres = new ArrayList<>();

    public static ArrayList<Filters> getAllGenres() {
        return allGenres;
    }

    public static void setAllGenres(ArrayList<Filters> addDay) {
        for (int i = 0; i < addDay.size(); i++) {
            allGenres.add(addDay.get(i));
        }
    }

    public static void setNewItem(Filters movie) {
        allGenres.add(movie);
    }

    public static Filters getItem(int position) {
        return allGenres.get(position);
    }
}
