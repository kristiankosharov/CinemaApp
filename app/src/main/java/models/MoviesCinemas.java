package models;

/**
 * Created by kristian on 15-4-17.
 */
public class MoviesCinemas {
    long id, movie_id, cinema_id;
    int isActive;

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    public long getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(long cinema_id) {
        this.cinema_id = cinema_id;
    }

    @Override
    public String toString() {
        return "MoviesCinemas{" +
                "id=" + id +
                ", movie_id=" + movie_id +
                ", cinema_id=" + cinema_id +
                ", isActive=" + isActive +
                '}';
    }
}
