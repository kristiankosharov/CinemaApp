package models;

/**
 * Created by kristian on 15-4-16.
 */
public class Genre {
    long id;
    long movie_id;
    String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ID: " + id + " ,MOVIE ID : " + movie_id + " ,TITLE: " + title;
    }
}
