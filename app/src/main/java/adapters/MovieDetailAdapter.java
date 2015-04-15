package adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import database.AllCinemasDataSource;
import database.AllDaysDataSource;
import database.AllGenresDataSource;
import helpers.CustomHorizontalScrollView;
import helpers.SessionManager;
import models.AddMovies;
import models.Filters;
import models.Movie;
import mycinemaapp.com.mycinemaapp.BaseActivity;
import mycinemaapp.com.mycinemaapp.MovieTrailerLandscape;
import mycinemaapp.com.mycinemaapp.R;
import mycinemaapp.com.mycinemaapp.RateActivity;
import mycinemaapp.com.mycinemaapp.WebViewActivity;

/**
 * Created by kristian on 15-3-5.
 */
public class MovieDetailAdapter extends PagerAdapter {

    private Activity context;
    private ArrayList<Movie> list = new ArrayList<>();
    private String day;
    private String place;

    private float density;
    private int viewWidth;
    private SessionManager sm;
    private boolean isList, isRated, isBought;
    private AllDaysDataSource daysDataSource;
    private AllCinemasDataSource cinemasDataSource;
    private AllGenresDataSource genresDataSource;


    public MovieDetailAdapter(Activity context, ArrayList<Movie> list, boolean isList, boolean isRated, boolean isBought) {
        this.context = context;
        this.list = list;
        this.isList = isList;
        this.isRated = isRated;
        this.isBought = isBought;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        density = ((BaseActivity) context).getDensity();
        viewWidth = ((BaseActivity) context).getViewWidth();
        sm = new SessionManager(context);
        daysDataSource = new AllDaysDataSource(context);
        daysDataSource.open();
        cinemasDataSource = new AllCinemasDataSource(context);
        cinemasDataSource.open();
        genresDataSource = new AllGenresDataSource(context);
        genresDataSource.open();

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.movie_detail_item, null, false);

        final PagerHolder viewHolder = new PagerHolder();
        viewHolder.ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        viewHolder.rateBtn = (Button) view.findViewById(R.id.rate_button);
        viewHolder.movieImage = (ImageView) view.findViewById(R.id.image);
        viewHolder.title = (TextView) view.findViewById(R.id.title);
        viewHolder.genre = (TextView) view.findViewById(R.id.genre);
        viewHolder.director = (TextView) view.findViewById(R.id.names_director);
        viewHolder.actors = (TextView) view.findViewById(R.id.names_actors);
        viewHolder.duration = (TextView) view.findViewById(R.id.duration);
        viewHolder.addButton = (ImageView) view.findViewById(R.id.add_button);
        viewHolder.userRatingIcon = (TextView) view.findViewById(R.id.rating);

        viewHolder.titleDescription = (TextView) view.findViewById(R.id.title_details);
        viewHolder.imdb = (Button) view.findViewById(R.id.imdb);
        viewHolder.fullDescription = (TextView) view.findViewById(R.id.full_desription);
        viewHolder.movieTrailer = (VideoView) view.findViewById(R.id.movie_trailer);
        viewHolder.playTrailer = (ImageButton) view.findViewById(R.id.play_trailer);
        RelativeLayout l = (RelativeLayout) view.findViewById(R.id.rating_layout);
        l.requestFocus();

        view.setTag(viewHolder);

        //PagerHolder holder = (PagerHolder) view.getTag();

        final Movie item = list.get(position);

        viewHolder.ratingBar.setMax(5);
        viewHolder.ratingBar.setStepSize(0.5f);
        viewHolder.ratingBar.setRating(item.getMovieProgress());
        //viewHolder.movieImage.setImageUrl("", null);
        Picasso.with(context)
                .load(item.getImageUrl())
                .noPlaceholder()
                .into(viewHolder.movieImage);
        viewHolder.title.setText(item.getMovieTitle());

        String genresString = "";
        ArrayList<String> genresArray = item.getMovieGenre();
        if (item.getMovieGenre() != null) {
            for (int i = 0; i < item.getCountGenre(); i++) {
                if (genresArray.get(i) == null) {
                    genresArray.remove(i);
                    genresArray.add(i, "");
                }
                genresString = genresString + genresArray.get(i) + ".";
            }
        }
        viewHolder.genre.setText(genresString);

        String directorsString = "";
//        ArrayList<String> directorsArray = item.getMovieDirectors();
//        if (item.getMovieDirectors() != null) {
//            for (int i = 0; i < item.getCountDeirectors(); i++) {
//                if (directorsArray.get(i) == null) {
//                    directorsArray.remove(i);
//                    directorsArray.add(i, "");
//                }
//                directorsString = directorsString + directorsArray.get(i) + "\n";
//            }
//        }
        viewHolder.director.setText(item.getMovieDirectors());

        String actorsString = "";
        ArrayList<String> actorsArray = item.getMovieActors();
        if (item.getMovieActors() != null) {
            for (int i = 0; i < item.getCountActors(); i++) {
                if (actorsArray.get(i) == null) {
                    actorsArray.remove(i);
                    actorsArray.add("");
                }
                actorsString = actorsString + actorsArray.get(i) + "\n";
            }
        }
        viewHolder.actors.setText(actorsString);
        if (item.getDuration() != 0) {
            viewHolder.duration.setText(item.getDuration() + "min");
        }
        if (item.getMovieTitle() != null) {
            viewHolder.titleDescription.setText(item.getMovieTitle());
        }
        if (item.getFullDescription() != null) {
            viewHolder.fullDescription.setText(item.getFullDescription());
        }
        viewHolder.playTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieTrailerLandscape.class);
                intent.putExtra("url", item.getMovieTrailerUrl());
                intent.putExtra("POSITION", position);
                context.startActivityForResult(intent, 1);
            }
        });

        viewHolder.movieTrailer.clearFocus();
        viewHolder.imdb.setOnClickListener(imdbListener(item));


        RelativeLayout daysLayout = (RelativeLayout) view.findViewById(R.id.days_horizontal_scroll_view);
        RelativeLayout mallLayout = (RelativeLayout) view.findViewById(R.id.mall_horizontal_scroll_view);
        RelativeLayout projectionLayout = (RelativeLayout) view.findViewById(R.id.projection_horizontal_scroll_view);

        //New Thread maybe
        if (item.getNameOfPlace() != null || item.getDate() != null) {
            createDaysScroll(daysLayout, item, projectionLayout);
            createPlaceScroll(mallLayout, item, projectionLayout);
        }
        viewHolder.rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RateActivity.class);
                intent.putExtra("ISRATED", isRated);
                intent.putExtra("ISLIST", isList);
                intent.putExtra("POSITION", position);
                context.startActivityForResult(intent, 2);
            }
        });

        if (item.isAdd()) {
            viewHolder.addButton.setImageResource(R.drawable.check_icon);
            viewHolder.addButton.setPadding(10, 10, 10, 10);
            viewHolder.addButton.setOnClickListener(null);
        }
        if (item.getUserRating() != 0) {
            viewHolder.addButton.setVisibility(View.GONE);
            viewHolder.userRatingIcon.setText(String.valueOf(item.getUserRating()));
            viewHolder.userRatingIcon.setTextColor(Color.WHITE);
            viewHolder.userRatingIcon.setVisibility(View.VISIBLE);
        }
        if (!item.isAdd()) {
            viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sm.getRemember()) {
                        item.setAdd(true);
                        item.setPosition(position);
                        AddMovies.addMovie.add(item);

                        viewHolder.addButton.setImageResource(R.drawable.check_icon);
                        viewHolder.addButton.setPadding(10, 10, 10, 10);
                        viewHolder.addButton.setOnClickListener(null);
                        Toast.makeText(context, "Add movie to list.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "First you must log in!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        ((ViewPager) container).addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    static class PagerHolder {
        RatingBar ratingBar;
        Button rateBtn, imdb;
        ImageView movieImage;
        TextView title, genre, director, actors, duration, titleDescription, fullDescription, userRatingIcon;
        VideoView movieTrailer;
        ImageButton playTrailer;
        ImageView addButton;
    }

    public void createDaysScroll(RelativeLayout containerLayout, Movie item, RelativeLayout
            containerForProjects) {
        boolean fromAdapter = true;
        ArrayList<String> nameOfDays = new ArrayList<>();
        ArrayList<Filters> date = new ArrayList<>();
        ArrayList<Filters> places = new ArrayList<>();

        date = daysDataSource.getAllFilters();
        places = cinemasDataSource.getAllFilters();
        for (Filters s : date) {
            nameOfDays.add(s.getDayNameFilter());
        }

//        final ArrayList<String> nameOfPlace = item.getNameOfPlace();

        TextView dayView;
        TextView dateView;
        LinearLayout layout;

        LinearLayout masterLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                viewWidth,
                (int) (50 * density));

        LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(
                viewWidth,
                (int) (50 * density), Gravity.CENTER_HORIZONTAL);
        textViewParam.weight = 1;
        int halfViewWidth = viewWidth / 2;

        final CustomHorizontalScrollView scrollView = new CustomHorizontalScrollView(context, date.size(), viewWidth, viewWidth, containerForProjects);

        scrollView.setDayAndPlace(places, date, item.getAllProjections());
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.fromScroll(true, false, false);
        scrollView.createProjectionScroll(places.get(0).getCinemaFilter(), date.get(0).getDayFilter(), fromAdapter);

        for (int i = -1; i < date.size() + 1; i++) {

            dayView = new TextView(context);
            dateView = new TextView(context);
            layout = new LinearLayout(context);

            if (i == -1 || i == date.size()) {
                layout.setLayoutParams(layoutParams);
            } else {
                layout.setLayoutParams(layoutParams);
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                layout.setOrientation(LinearLayout.VERTICAL);

                dayView.setWidth(viewWidth);
                dayView.setLayoutParams(textViewParam);
                dayView.setGravity(Gravity.CENTER_HORIZONTAL);
                if (nameOfDays.size() > 0) {
                    dayView.setText(nameOfDays.get(i));
                }
                dayView.setTag(i);

                dateView.setWidth(viewWidth);
                dateView.setLayoutParams(textViewParam);
                dateView.setGravity(Gravity.CENTER_HORIZONTAL);
                if (date.size() > 0) {
                    dateView.setText(date.get(i).getDayFilter());
                }

                layout.addView(dayView);
                layout.addView(dateView);

                layout.setLayoutParams(layoutParams);
            }
            day = date.get(0).getDayFilter();

            layout.setBackground(context.getResources().getDrawable(R.drawable.scalloped_rectangle));
            masterLayout.addView(layout);
        }
        scrollView.addView(masterLayout);
        containerLayout.addView(scrollView);
    }

    public void createPlaceScroll(RelativeLayout containerLayout, Movie item, RelativeLayout
            containerForProjects) {

        final ArrayList<Filters> nameOfPlace = cinemasDataSource.getAllFilters();
        ArrayList<Filters> date = daysDataSource.getAllFilters();

        final CustomHorizontalScrollView scrollView = new CustomHorizontalScrollView(context, nameOfPlace.size() - 1, viewWidth, viewWidth, containerForProjects);
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.fromScroll(false, true, false);

        scrollView.setDayAndPlace(nameOfPlace, date, item.getAllProjections());
        scrollView.createProjectionScroll(nameOfPlace.get(0).getCinemaFilter(), date.get(0).getDayFilter(), true);

        LinearLayout masterLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                viewWidth,
                (int) (50 * density));

        LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(
                viewWidth,
                (int) (50 * density), Gravity.CENTER_HORIZONTAL);
        textViewParam.weight = 1;
        int halfViewWidth = viewWidth / 2;

        LinearLayout.LayoutParams emptyViewParam = new LinearLayout.LayoutParams(
                viewWidth + halfViewWidth,
                (int) (50 * density), Gravity.CENTER_HORIZONTAL);


        TextView placeView;
        LinearLayout layout;
        LinearLayout emptyLayout;

        for (int i = -1; i < nameOfPlace.size() + 1; i++) {

            placeView = new TextView(context);
            layout = new LinearLayout(context);
            if (i == -1 || i == nameOfPlace.size()) {
                layout.setLayoutParams(layoutParams);
            } else {

                layout.setLayoutParams(layoutParams);
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                layout.setOrientation(LinearLayout.VERTICAL);

                placeView.setWidth(viewWidth);
                placeView.setLayoutParams(textViewParam);
                placeView.setGravity(Gravity.CENTER);
                placeView.setText(nameOfPlace.get(i).getCinemaFilter());
                placeView.setTag(i);

                layout.addView(placeView);
                layout.setLayoutParams(layoutParams);
            }
            layout.setBackground(context.getResources().getDrawable(R.drawable.scalloped_rectangle));
            masterLayout.addView(layout);
        }
        scrollView.addView(masterLayout);
        containerLayout.addView(scrollView);
    }

    private View.OnClickListener imdbListener(final Movie item) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", item.getImdbUrl());
                context.startActivity(intent);
            }
        };

        return listener;
    }
}
