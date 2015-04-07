package models;

/**
 * Created by kristian on 15-3-31.
 */
public class Filters {

    String dayFilter;
    String cinemaFilter;
    String genreFilter;
    boolean isSelect;

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


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "Day filter: " + dayFilter + ", cinema filter: " + cinemaFilter + ", genre filter: " + genreFilter;
    }
}
