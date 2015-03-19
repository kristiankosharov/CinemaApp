package Adapters;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import Helpers.CustomHorizontalScrollView;
import Helpers.ImageCacheManager;
import Models.MovieDetail;
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
    private ArrayList<MovieDetail> list;
    private String day;
    private String place;

    private float density;
    private int viewWidth;

    public MovieDetailAdapter(Activity context, ArrayList<MovieDetail> list) {
        this.context = context;
        this.list = list;
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
    public Object instantiateItem(ViewGroup container, int position) {

        density = ((BaseActivity) context).getDensity();
        viewWidth = ((BaseActivity) context).getViewWidth();

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.movie_detail_item, null, false);

        final PagerHolder viewHolder = new PagerHolder();
        viewHolder.ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        viewHolder.rateBtn = (Button) view.findViewById(R.id.rate_button);
        viewHolder.movieImage = (NetworkImageView) view.findViewById(R.id.image);
        viewHolder.title = (TextView) view.findViewById(R.id.title);
        viewHolder.genre = (TextView) view.findViewById(R.id.genre);
        viewHolder.director = (TextView) view.findViewById(R.id.names_director);
        viewHolder.actors = (TextView) view.findViewById(R.id.names_actors);
        viewHolder.duration = (TextView) view.findViewById(R.id.duration);

        viewHolder.titleDescription = (TextView) view.findViewById(R.id.title_details);
        viewHolder.imdb = (Button) view.findViewById(R.id.imdb);
        viewHolder.fullDescription = (TextView) view.findViewById(R.id.full_desription);
        viewHolder.movieTrailer = (VideoView) view.findViewById(R.id.movie_trailer);
        viewHolder.playTrailer = (ImageButton) view.findViewById(R.id.play_trailer);
        RelativeLayout l = (RelativeLayout) view.findViewById(R.id.rating_layout);
        l.requestFocus();

        view.setTag(viewHolder);

        //PagerHolder holder = (PagerHolder) view.getTag();


        final MovieDetail item = list.get(position);

        viewHolder.ratingBar.setMax(100);
        viewHolder.ratingBar.setProgress(item.getRating());
        //viewHolder.movieImage.setImageUrl("", null);
        viewHolder.movieImage.setImageUrl(item.getImageUrl(), ImageCacheManager.getInstance().getImageLoader());
        viewHolder.movieImage.setDefaultImageResId(R.drawable.example);
        viewHolder.title.setText(item.getMovieTitle());

        String genresString = "";
        String[] genresArray = item.getMovieGenre();
        for (int i = 0; i < item.getCountGenre(); i++) {
            genresString = genresString + genresArray[i] + ".";
            viewHolder.genre.setText(genresString);
        }

        String directorsString = "";
        String[] directorsArray = item.getMovieDirectors();
        for (int i = 0; i < item.getCountDeirectors(); i++) {
            directorsString = directorsString + directorsArray[i] + "\n";
            viewHolder.director.setText(directorsString);
        }

        String actorsString = "";
        String[] actorsArray = item.getMovieActors();
        for (int i = 0; i < item.getCountActors(); i++) {
            actorsString = actorsString + actorsArray[i] + "\n";
            viewHolder.actors.setText(actorsString);
        }

        viewHolder.duration.setText(item.getDuration());
        viewHolder.titleDescription.setText(item.getMovieTitle());
        viewHolder.fullDescription.setText(item.getFullDescription());


        viewHolder.movieTrailer.setVideoURI(Uri.parse(item.getMovieTrailerUrl()));
        viewHolder.movieTrailer.setBackground(context.getResources().getDrawable(R.drawable.trailer_tumbnail));

        viewHolder.movieTrailer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
            }
        });

        viewHolder.movieTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieTrailerLandscape.class);
                intent.putExtra("url", item.getMovieTrailerUrl());
                context.startActivity(intent);
            }
        });

        viewHolder.playTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieTrailerLandscape.class);
                intent.putExtra("url", item.getMovieTrailerUrl());
                context.startActivity(intent);
            }
        });

        viewHolder.movieTrailer.clearFocus();

//        viewHolder

        viewHolder.imdb.setOnClickListener(imdbListener(item));


        RelativeLayout daysLayout = (RelativeLayout) view.findViewById(R.id.days_horizontal_scroll_view);
        RelativeLayout mallLayout = (RelativeLayout) view.findViewById(R.id.mall_horizontal_scroll_view);
        RelativeLayout projectionLayout = (RelativeLayout) view.findViewById(R.id.projection_horizontal_scroll_view);

        //New Thread maybe
        createDaysScroll(daysLayout, item, projectionLayout);
        createPlaceScroll(mallLayout, item, projectionLayout);

        viewHolder.rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RateActivity.class);
                context.startActivity(intent);
            }
        });
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
        NetworkImageView movieImage;
        TextView title, genre, director, actors, duration, titleDescription, fullDescription;
        VideoView movieTrailer;
        ImageButton playTrailer;
    }

    public void createDaysScroll(RelativeLayout containerLayout, MovieDetail item, RelativeLayout containerForProjects) {
        boolean fromAdapter = true;
        ArrayList<String> nameOfDays = item.getNameDayOfMonth();
        ArrayList<String> date = item.getDate();
        ArrayList<String> places = item.getNameOfPlace();
        final ArrayList<String> nameOfPlace = item.getNameOfPlace();

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

        final CustomHorizontalScrollView scrollView = new CustomHorizontalScrollView(context, item.getNumberOfDays(), viewWidth, viewWidth, containerForProjects);

        scrollView.setDayAndPlace(nameOfPlace, date, item.getAllProjections());
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.fromScroll(true, false, false);
        scrollView.createProjectionScroll(places.get(0), date.get(0), fromAdapter);

        for (int i = -1; i < item.getNumberOfDays() + 2; i++) {

            dayView = new TextView(context);
            dateView = new TextView(context);
            layout = new LinearLayout(context);

            if (i == -1 || i == item.getNumberOfDays() + 1) {
                layout.setLayoutParams(layoutParams);
            } else {
                layout.setLayoutParams(layoutParams);
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                layout.setOrientation(LinearLayout.VERTICAL);

                dayView.setWidth(viewWidth);
                dayView.setLayoutParams(textViewParam);
                dayView.setGravity(Gravity.CENTER_HORIZONTAL);
                dayView.setText(nameOfDays.get(i));
                dayView.setTag(i);

                dateView.setWidth(viewWidth);
                dateView.setLayoutParams(textViewParam);
                dateView.setGravity(Gravity.CENTER_HORIZONTAL);
                dateView.setText(date.get(i));

                layout.addView(dayView);
                layout.addView(dateView);

                layout.setLayoutParams(layoutParams);
            }
            day = date.get(0);

            layout.setBackground(context.getResources().getDrawable(R.drawable.scalloped_rectangle));
            masterLayout.addView(layout);
        }
        scrollView.addView(masterLayout);
        containerLayout.addView(scrollView);
    }

    public void createPlaceScroll(RelativeLayout containerLayout, MovieDetail item, RelativeLayout containerForProjects) {

        final ArrayList<String> nameOfPlace = item.getNameOfPlace();
        ArrayList<String> date = item.getDate();

        final CustomHorizontalScrollView scrollView = new CustomHorizontalScrollView(context, nameOfPlace.size() - 1, viewWidth, viewWidth, containerForProjects);
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.fromScroll(false, true, false);

        scrollView.setDayAndPlace(nameOfPlace, date, item.getAllProjections());
        scrollView.createProjectionScroll(nameOfPlace.get(0), date.get(0), true);

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
                placeView.setText(nameOfPlace.get(i));
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

    private View.OnClickListener imdbListener(final MovieDetail item) {
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
