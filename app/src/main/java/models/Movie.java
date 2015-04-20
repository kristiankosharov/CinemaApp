package models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kristian on 15-2-25.
 */
public class Movie {


    long id;
    int position;

    String newForWeek;
    float movieProgress;

    int rating;
    float userRating;


    int duration;
    String imageUrl;
    String movieTitle;
    //    ArrayList<String> movieGenre = new ArrayList<>();
    ArrayList<Integer> genreIds = new ArrayList<>();


    ArrayList<String> startingHours = new ArrayList<>();

    String movieDirectors;
    ArrayList<String> movieActors;

    String movieTrailerUrl;
    String fullDescription;
    String imdbUrl;
    String imdbRating;

    String commentUserImageUrl;
    String commentUserName;
    int commentUserRating;
    String comment;

    int numberOfDays;
    int startDay;
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> nameDayOfMonth;
    String[] timeOfProjection;

    String releaseDate;
    String price;

    ArrayList<Integer> cinemaIds = new ArrayList<>();

    HashMap<String, HashMap<String, String[]>> allProjections;

    private boolean isAdd;
    int isActive;

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public ArrayList<String> getStartingHours() {
        return startingHours;
    }

    public void setStartingHours(String startingHours) {
        this.startingHours.add(startingHours);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getHeightView() {
        return heightView;
    }

    public void setHeightView(int heightView) {
        this.heightView = heightView;
    }

    private int heightView;

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public ArrayList<Integer> getCinemaIds() {
        return cinemaIds;
    }

    public void setCinemaIds(int places) {
        cinemaIds.add(places);
    }


    public HashMap<String, HashMap<String, String[]>> getAllProjections() {
        return allProjections;
    }

    public void setAllProjections(HashMap<String, HashMap<String, String[]>> allProjections) {
        this.allProjections = allProjections;
    }


    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }


    public ArrayList<String> getNameDayOfMonth() {
        return nameDayOfMonth;
    }

    public void setNameDayOfMonth(ArrayList<String> nameDayOfMonth) {
        this.nameDayOfMonth = nameDayOfMonth;
    }

    public String[] getTimeOfProjection() {
        return timeOfProjection;
    }

    public void setTimeOfProjection(String[] timeOfProjection) {
        this.timeOfProjection = timeOfProjection;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public void setDate(String dateParam) {
        date.add(dateParam);
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


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getMovieActors() {
        return movieActors;
    }

    public void setMovieActors(ArrayList<String> movieActors) {
        this.movieActors = movieActors;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<Integer> getMovieGenreIds() {
        return genreIds;
    }

    public void setMovieGenreId(int movieGenreString) {
        genreIds.add(movieGenreString);
    }

    public int getCountGenreIds() {
        return genreIds.size();
    }

    public String getMovieDirectors() {
        return movieDirectors;
    }

    public void setMovieDirectors(String movieDirectors) {
        this.movieDirectors = movieDirectors;
    }

//    public int getCountDeirectors() {
//        return movieDirectors.size();
//    }

    public int getCountActors() {
        return movieActors.size();
    }

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

    public float getMovieProgress() {
        return movieProgress;
    }

    public void setMovieProgress(float movieProgress) {
        this.movieProgress = movieProgress;
    }

    @Override
    public String toString() {
        return "MOVIE ID :" + id + "Movie: " + movieTitle + ",Progress: " + movieProgress + ",days" + date.toString() + ",cinemas" + cinemaIds.toString() + ",genres" + genreIds.toString();
    }
}
