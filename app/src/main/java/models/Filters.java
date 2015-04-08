package models;

/**
 * Created by kristian on 15-3-31.
 */
public class Filters {

    String dayFilter;
    String cinemaFilter;
    String genreFilter;
    boolean isDaySelected;
    boolean isCinemaSelected;
    boolean isGenreSelected;

    public boolean isGenreSelected() {
        return isGenreSelected;
    }

    public void setGenreSelected(boolean isGenreSelected) {
        this.isGenreSelected = isGenreSelected;
    }

    public boolean isCinemaSelected() {
        return isCinemaSelected;
    }

    public void setCinemaSelected(boolean isCinemaSelected) {
        this.isCinemaSelected = isCinemaSelected;
    }

    public boolean isDaySelected() {
        return isDaySelected;
    }

    public void setDaySelected(boolean isDaySelected) {
        this.isDaySelected = isDaySelected;
    }

    public String getCinemaFilter() {
        return cinemaFilter;
    }

    public void setCinemaFilter(String cinemaFilter) {
        this.cinemaFilter = cinemaFilter;
    }

    public String getGenreFilter() {
        return genreFilter;
    }

    public void setGenreFilter(String genreFilter) {
        this.genreFilter = genreFilter;
    }

    public String getDayFilter() {
        return dayFilter;
    }

    public void setDayFilter(String dayFilter) {
        this.dayFilter = dayFilter;
    }


    @Override
    public String toString() {
        return "Day filter: " + dayFilter + ", cinema filter: " + cinemaFilter + ", genre filter: " + genreFilter;
    }
}
