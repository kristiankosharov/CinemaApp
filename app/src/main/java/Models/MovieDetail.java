package Models;

/**
 * Created by kristian on 15-3-5.
 */
public class MovieDetail {

    int rating;
    String duration;
    String imageUrl;
    String movieTitle;
    String[] movieGenre;
    String[] movieDirectors;
    String[] movieActors;

    String movieTrailerUrl;
    String fullDescription;
    String imdbUrl;

    String commentUserImageUrl;
    String commentUserName;
    int commentUserRating;
    String comment;

    int numberOfDays;
    int startDay;
    String[] date;
    String[] nameDayOfMonth;
    String[] timeOfProjection;



    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }


    public String[] getNameDayOfMonth() {
        return nameDayOfMonth;
    }

    public void setNameDayOfMonth(String[] nameDayOfMonth) {
        this.nameDayOfMonth = nameDayOfMonth;
    }

    public String[] getTimeOfProjection() {
        return timeOfProjection;
    }

    public void setTimeOfProjection(String[] timeOfProjection) {
        this.timeOfProjection = timeOfProjection;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }


    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getMovieTrailerUrl() {
        return movieTrailerUrl;
    }

    public void setMovieTrailerUrl(String movieTrailerUrl) {
        this.movieTrailerUrl = movieTrailerUrl;
    }

    public String getImdbUrl() {
        return imdbUrl;
    }

    public void setImdbUrl(String imdbUrl) {
        this.imdbUrl = imdbUrl;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String[] getMovieActors() {
        return movieActors;
    }

    public void setMovieActors(String[] movieActors) {
        this.movieActors = movieActors;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String[] getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String[] movieGenre) {
        this.movieGenre = movieGenre;
    }

    public int getCountGenre() {
        return movieGenre.length;
    }

    public String[] getMovieDirectors() {
        return movieDirectors;
    }

    public void setMovieDirectors(String[] movieDirectors) {
        this.movieDirectors = movieDirectors;
    }

    public int getCountDeirectors() {
        return movieDirectors.length;
    }

    public int getCountActors() {
        return movieActors.length;
    }


}
