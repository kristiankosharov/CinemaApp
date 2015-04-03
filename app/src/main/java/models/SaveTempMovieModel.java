package models;

import java.util.ArrayList;

/**
 * Created by kristian on 15-3-23.
 */
public class SaveTempMovieModel {
    public static ArrayList<Movie> getMovies() {
        return movies;
    }

    public static void setMovies(ArrayList<Movie> movies) {
        SaveTempMovieModel.movies = movies;
    }

    public static Movie getItem(int position) {
        return movies.get(position);
    }

    public static ArrayList<Movie> movies;

    public static int getHeight() {
        int height = 0;
        for (int i = 0; i < movies.size(); i++) {
            height += movies.get(i).getHeightView();
        }
        return height;
    }
}
