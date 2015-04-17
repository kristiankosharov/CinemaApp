package models;

/**
 * Created by kristian on 15-4-17.
 */
public class MovieCinemaProjections {
    long id, movie_cinema_id;
    String dayOfWeek;
    String startingTime;

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMovie_cinema_id() {
        return movie_cinema_id;
    }

    public void setMovie_cinema_id(long movie_cinema_id) {
        this.movie_cinema_id = movie_cinema_id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
