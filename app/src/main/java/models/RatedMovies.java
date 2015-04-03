package models;

import java.util.ArrayList;

/**
 * Created by kristian on 15-3-24.
 */
public class RatedMovies {
    public static ArrayList<Movie> ratedMovies = new ArrayList<>();

    public static ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public static void setRatedMovies(ArrayList<Movie> movies) {
        RatedMovies.ratedMovies = movies;
    }

    public static Movie getItem(int position) {
        return ratedMovies.get(position);
    }


//    public static int getHeight() {
//        int height = 0;
//        for (int i = 0; i < ratedMovies.size(); i++) {
//            height += ratedMovies.get(i).getHeightView();
//        }
//        return height;
//    }
}
