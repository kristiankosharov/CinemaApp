package Models;

/**
 * Created by kristian on 15-2-25.
 */
public class Movie {

    String newForWeek;
    String movieTitle;
    String imageUrl;
    int movieProgress;

    public String getNewForWeek() {
        return newForWeek;
    }

    public void setNewForWeek(String newForWeek) {
        this.newForWeek = newForWeek;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMovieProgress() {
        return movieProgress;
    }

    public void setMovieProgress(int movieProgress) {
        this.movieProgress = movieProgress;
    }
}
