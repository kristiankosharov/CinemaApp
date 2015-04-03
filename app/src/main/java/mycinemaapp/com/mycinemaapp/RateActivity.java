package mycinemaapp.com.mycinemaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import helpers.CustomEditText;
import helpers.SessionManager;
import models.AddMovies;
import models.Movie;
import models.RatedMovies;
import models.SaveTempMovieModel;

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
    private boolean isRated, isList;
    private ArrayList<Movie> movies = SaveTempMovieModel.getMovies();
    private ArrayList<Movie> ratedMovie = RatedMovies.getRatedMovies();
    private ArrayList<Movie> addMovie = AddMovies.getAddMovie();

    private TextView imdbRating;
    private SessionManager sm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_movie_layout);
        sm = new SessionManager(this);

        position = getIntent().getIntExtra("POSITION", 0);
        isRated = getIntent().getBooleanExtra("ISRATED", false);
        isList = getIntent().getBooleanExtra("ISLIST", false);

        comments = (CustomEditText) findViewById(R.id.comments);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        yourRating = (TextView) findViewById(R.id.your_rating);
        imdbRating = (TextView) findViewById(R.id.imdb_rating);

        if (isRated) {
            ratingBar.setRating(ratedMovie.get(position).getUserRating());
            yourRating.setText(String.valueOf(ratedMovie.get(position).getUserRating()));
            if (ratedMovie.get(position).getImdbRating() == null || ratedMovie.get(position).getImdbRating().equals("")) {
                imdbRating.setText("0.0");
            } else {
                imdbRating.setText(ratedMovie.get(position).getImdbRating());
            }
        } else if (isList) {
            ratingBar.setRating(addMovie.get(position).getUserRating());
            yourRating.setText(String.valueOf(addMovie.get(position).getUserRating()));
            if (addMovie.get(position).getImdbRating() == null || addMovie.get(position).getImdbRating().equals("")) {
                imdbRating.setText("0.0");
            } else {
                imdbRating.setText(addMovie.get(position).getImdbRating());
            }
        } else {
            ratingBar.setRating(movies.get(position).getUserRating());
            yourRating.setText(String.valueOf(movies.get(position).getUserRating()));
            if (movies.get(position).getImdbRating() == null || movies.get(position).getImdbRating().equals("")) {
                imdbRating.setText("0.0");
            } else {
                imdbRating.setText(movies.get(position).getImdbRating());
            }
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                yourRating.setText(ratingBar.getRating() + "");
                if (isRated) {
                    ratedMovie.get(position).setUserRating(ratingBar.getRating());
                } else if (isList) {
                    addMovie.get(position).setUserRating(ratingBar.getRating());
                } else {
                    movies.get(position).setUserRating(ratingBar.getRating());
                }
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
                if (sm.getRemember()) {
                    Toast.makeText(this, "Give rate successfully!", Toast.LENGTH_LONG).show();

                    // Rate from List in MyProfile
                    if (isList) {

                        ratedMovie.add(addMovie.get(position));
                        addMovie.remove(position);

//                    movies.get(position).setAdd(false);
//                    for (int i = 0; i < addMovie.size(); i++) {
//                        if (movies.get(position).getMovieTitle().equals(addMovie.get(i).getMovieTitle())) {
//                            addMovie.remove(i);
//                        }
//                    }
                    } else if (isRated) {
                        // Rate from Rated in MyProfile
                        ratedMovie.get(position).setUserRating(ratingBar.getRating());
                    } else if (movies.get(position).isAdd()) {
                        // First add item and then rate
                        movies.get(position).setAdd(false);
                        for (int i = 0; i < addMovie.size(); i++) {
                            if (movies.get(position).getMovieTitle().equals(addMovie.get(i).getMovieTitle())) {
                                ratedMovie.add(addMovie.get(i));
                                addMovie.remove(i);
                            }
                        }
                    } else {
                        // Rate without add
                        ratedMovie.add(SaveTempMovieModel.getItem(position));
                    }
//                    Toast.makeText(this, position + "", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } else {
                    Toast.makeText(this, "First you must log in!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("POSITION", position);
        super.onBackPressed();
    }
}
