package models;

/**
 * Created by kristian on 15-4-20.
 */
public class MovieGenre {
    long id;
    long movie_id;
    long genre_id;

    public long getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(long genre_id) {
        this.genre_id = genre_id;
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

    @Override
    public String toString() {
        return "MovieGenre{" +
                "id=" + id +
                ", movie_id=" + movie_id +
                ", genre_id=" + genre_id +
                '}';
    }
}
