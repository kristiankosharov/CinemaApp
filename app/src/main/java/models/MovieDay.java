package models;

/**
 * Created by kristian on 15-4-16.
 */
public class MovieDay {
    long id;
    long day_id;
    long movie_id;

    public long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDay_id() {
        return day_id;
    }

    public void setDay_id(long day_id) {
        this.day_id = day_id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " DAY ID : " + day_id + " MOVIE ID: " + movie_id;
    }
}
