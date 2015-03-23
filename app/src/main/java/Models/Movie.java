package Models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kristian on 15-2-25.
 */
public class Movie {

    String newForWeek;
    float movieProgress;


    int rating;
    int userRating;
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
    ArrayList<String> date;
    ArrayList<String> nameDayOfMonth;
    String[] timeOfProjection;

    ArrayList<String> nameOfPlace;

    HashMap<String, HashMap<String, String[]>> allProjections;

    private boolean isAdd;

    public int getHeightView() {
        return heightView;
    }

    public void setHeightView(int heightView) {
        this.heightView = heightView;
    }

    private int heightView;

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public ArrayList<String> getNameOfPlace() {
        return nameOfPlace;
    }

    public void setNameOfPlace(ArrayList<String> nameOfPlace) {
        this.nameOfPlace = nameOfPlace;
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

    public void setDate(ArrayList<String> date) {
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
}
