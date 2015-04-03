package models;

/**
 * Created by kristian on 15-3-31.
 */
public class Filter {

    String filter;
    boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
