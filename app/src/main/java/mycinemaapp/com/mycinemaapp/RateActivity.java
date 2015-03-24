package mycinemaapp.com.mycinemaapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Helpers.CustomEditText;
import Models.Movie;
import Models.RatedMovies;
import Models.SaveTempMovieModel;

/**
 * Created by kristian on 15-3-16.
 */
public class RateActivity extends Activity implements View.OnClickListener {

    private ImageView back;
    private RatingBar ratingBar;
    private TextView yourRating;
    private CustomEditText comments;
    private Button confirm;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_movie_layout);

        position = getIntent().getIntExtra("POSITION", 0);
        final ArrayList<Movie> movies = SaveTempMovieModel.getMovies();

        comments = (CustomEditText) findViewById(R.id.comments);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        yourRating = (TextView) findViewById(R.id.your_rating);

        if (movies.get(position).getUserRating() != 0) {
            ratingBar.setRating(movies.get(position).getUserRating());
        }
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                yourRating.setText(ratingBar.getRating() + "");
                movies.get(position).setUserRating(ratingBar.getRating());
            }
        });


//        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
//
//        stars.getDrawable(2).setColorFilter(this.getResources().getColor(R.color.starFullySelected), PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(1).setColorFilter(this.getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(0).setColorFilter(this.getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.rating_bar:

                break;
            case R.id.confirm:
                Toast.makeText(this, "Give rate successfully!", Toast.LENGTH_LONG).show();
                RatedMovies.ratedMovies.add(SaveTempMovieModel.getItem(position));
                onBackPressed();
                break;
        }
    }
}
