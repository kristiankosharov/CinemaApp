package models;

public class Seat {

    int row;
    int column;
    int state;
    int position;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}