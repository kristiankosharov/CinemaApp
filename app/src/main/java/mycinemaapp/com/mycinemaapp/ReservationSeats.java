package mycinemaapp.com.mycinemaapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import Helpers.SeatView;
import Models.Seat;

/**
 * Created by kristian on 15-3-16.
 */
public class ReservationSeats extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_layout);
        List<Seat> seatList = new ArrayList<Seat>();

        for (int i = 1; i <= 20; i++) {
            for (int j = 1; j <= 20; j++) {
                Seat s = new Seat();

                s.setRow(i);
                s.setColumn(j);

                if (i > 10 && i < 18) {
                    s.setState(2);
                }

                if (j > 8 && j < 11) {
                    s.setState(1);
                }

                seatList.add(s);
            }
        }
        SeatView sv = new SeatView(this, seatList);

        RelativeLayout seatContainer = (RelativeLayout) findViewById(R.id.rl_SeatContainer);

        seatContainer.addView(sv);

    }
}
