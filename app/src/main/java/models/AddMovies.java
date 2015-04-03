package models;

import java.util.ArrayList;

/**
 * Created by kristian on 15-3-23.
 */
public class AddMovies {

    public static ArrayList<Movie> addMovie = new ArrayList<>();

    public static ArrayList<Movie> getAddMovie() {
        return addMovie;
    }

    public static void setAddMovie(ArrayList<Movie> addMovie) {
        for (int i = 0; i < addMovie.size(); i++) {
            if (addMovie.get(i).isAdd()) {
                AddMovies.addMovie.add(addMovie.get(i));
            }
        }
    }

    public static void setNewItem(Movie movie) {
        addMovie.add(movie);
    }

    public static Movie getItem(int position) {
        return addMovie.get(position);
    }

//    public ArrayList<Movie> getMovie() {
//        return movie;
//    }
//
//    public void setMovie(ArrayList<Movie> movie) {
//        this.movie = movie;
//    }
}
