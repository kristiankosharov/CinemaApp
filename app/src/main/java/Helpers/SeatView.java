package Helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import Models.Seat;
import mycinemaapp.com.mycinemaapp.R;

public class SeatView extends View {

    private ShapeDrawable mSeatDrawable;
    private static int width = 15;
    private static int height = 15;
    private static int x = 20;
    private static int y = 20;

    List<Seat> seatList = new ArrayList<Seat>();

    public SeatView(Context context, List<Seat> seatList) {
        super(context);
        this.seatList = seatList;


    }

    protected void onDraw(Canvas canvas) {

        for (Seat seat : seatList) {

            switch (seat.getState()) {
                case 0:
                    drawCanvas(canvas, seat, getResources().getColor(R.color.seat_empty));
                    break;
                case 1:
                    drawCanvas(canvas, seat, getResources().getColor(R.color.seat_selected));
                    break;
                case 2:
                    break;
            }
        }
    }

    private void drawCanvas(Canvas canvas, Seat seat, int color) {
        int row = seat.getRow();
        int column = seat.getColumn();
        int state = seat.getState();

        int new_x = x * row;
        int new_y = y * column;

        mSeatDrawable = new ShapeDrawable(new RectShape());
        mSeatDrawable.getPaint().setColor(color);
        mSeatDrawable.setBounds(new_x, new_y, new_x + width, new_y + height);
        mSeatDrawable.draw(canvas);
    }
}