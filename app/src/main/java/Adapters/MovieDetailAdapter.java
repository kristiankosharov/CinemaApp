package Adapters;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import mycinemaapp.com.mycinemaapp.R;

/**
 * Created by kristian on 15-3-5.
 */
public class MovieDetailAdapter extends PagerAdapter {

    private Activity context;
    private ArrayList<MovieDetail> list;
    private String day;
    private String place;
//    RatingBar ratingBar;
//    Button rateBtn;
//    NetworkImageView movieImage;
//    TextView title,genre,director,actors,duration;

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

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.movie_detail_item, null, false);

        PagerHolder viewHolder = new PagerHolder();
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
        //viewHolder.movieTrailer = (VideoView) view.findViewById(R.id.movie_trailer);

        view.setTag(viewHolder);

        //PagerHolder holder = (PagerHolder) view.getTag();


        MovieDetail item = list.get(position);

        viewHolder.ratingBar.setMax(300);
        viewHolder.ratingBar.setProgress(item.getRating());
//        Toast.makeText(context, "progress" + item.getRating(), Toast.LENGTH_SHORT).show();

        LayerDrawable stars = (LayerDrawable) viewHolder.ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.starFullySelected), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);

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
        //viewHolder.movieTrailer.setVideoURI(Uri.parse(item.getMovieTrailerUrl()));
//        viewHolder.movieTrailer.setMediaController(new android.widget.MediaController(context));
//        viewHolder.movieTrailer.pause();


        RelativeLayout daysLayout = (RelativeLayout) view.findViewById(R.id.days_horizontal_scroll_view);
        RelativeLayout mallLayout = (RelativeLayout) view.findViewById(R.id.mall_horizontal_scroll_view);
        RelativeLayout projectionLayout = (RelativeLayout) view.findViewById(R.id.projection_horizontal_scroll_view);

        //New Thread maybe
        createDaysScroll(daysLayout, item,projectionLayout);
        createPlaceScroll(mallLayout, item,projectionLayout);

//        createDaysScroll(projectionLayout, item);

//        final ScrollView scroll = (ScrollView)view.findViewById(R.id.movie_detail_item_scroll);
//
//        scroll.post(new Runnable() {
//            @Override
//            public void run() {
//                scroll.fullScroll(ScrollView.FOCUS_UP);
//            }
//        });

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
    }

    public void createDaysScroll(RelativeLayout containerLayout, MovieDetail item,RelativeLayout containerForProjects) {

        float density = context.getResources().getDisplayMetrics().density;

        int viewWidth = getScreenWidth() / 3;
        boolean fromAdapter = true;

        LinearLayout masterLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                viewWidth,
                (int) (50 * density));
        //layoutParams.setMargins(1,0,1,0);

        LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(
                viewWidth,
                (int) (50 * density), Gravity.CENTER_HORIZONTAL);
        textViewParam.weight = 1;
        int halfViewWidth = viewWidth / 2;

        LinearLayout.LayoutParams emptyViewParam = new LinearLayout.LayoutParams(
                viewWidth + halfViewWidth,
                (int) (50 * density), Gravity.CENTER_HORIZONTAL);

        ArrayList<String> nameOfDays = item.getNameDayOfMonth();
        ArrayList<String> date = item.getDate();
        ArrayList<String> places = item.getNameOfPlace();
        final ArrayList<String> nameOfPlace = item.getNameOfPlace();


        final CustomHorizontalScrollView scrollView = new CustomHorizontalScrollView(context, item.getNumberOfDays(), viewWidth, viewWidth,containerForProjects);

        scrollView.setDayAndPlace(nameOfPlace,date,item.getAllProjections());
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.fromScroll(true, false, false);
        scrollView.createProjectionScroll(places.get(0),date.get(0),fromAdapter);

        TextView dayView;
        TextView dateView;
        LinearLayout layout;
        LinearLayout emptyLayout;

//        Toast.makeText(context,item.getStartDay() + "",Toast.LENGTH_SHORT).show();

        for (int i = -1; i < item.getNumberOfDays() + 2; i++) {

            dayView = new TextView(context);
            dateView = new TextView(context);
            //TextView emptyView = new TextView(context);
            layout = new LinearLayout(context);
            emptyLayout = new LinearLayout(context);
            emptyLayout.setLayoutParams(emptyViewParam);

//            Toast.makeText(context,""+item.getStartDay() +" "+ item.getNumberOfDays(),Toast.LENGTH_LONG).show();

            if (i == -1 || i == item.getNumberOfDays() + 1) {
                //emptyView.setWidth(viewWidth + halfViewWidth);
//                emptyView.setLayoutParams(emptyViewParam);
//                emptyView.setGravity(Gravity.CENTER_HORIZONTAL);
//                emptyView.setBackgroundColor(context.getResources().getColor(R.color.gray_background_gridview));
                //emptyLayout.addView(emptyView);
                layout.setLayoutParams(layoutParams);

//                layout.addView(emptyView);
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
            //layout.addView(emptyView);
            day = date.get(item.getStartDay());

            layout.setBackground(context.getResources().getDrawable(R.drawable.scalloped_rectangle));
            masterLayout.addView(layout);


        }
        scrollView.addView(masterLayout);
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//            }
//        });
//        scrollView.setScrollTo(viewWidth/2, 0);
        containerLayout.addView(scrollView);
    }

    public void createPlaceScroll(RelativeLayout containerLayout, MovieDetail item,RelativeLayout containerForProjects) {
        float density = context.getResources().getDisplayMetrics().density;
        int viewWidth = getScreenWidth() / 3;


        final ArrayList<String> nameOfPlace = item.getNameOfPlace();
        ArrayList<String> date = item.getDate();

        final CustomHorizontalScrollView scrollView = new CustomHorizontalScrollView(context, nameOfPlace.size()-1, viewWidth, viewWidth,containerForProjects);
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.fromScroll(false,true,false);

//   Toast.makeText(context,item.getAllProjections().toString(),Toast.LENGTH_SHORT).show();
        scrollView.setDayAndPlace(nameOfPlace,date,item.getAllProjections());
        scrollView.createProjectionScroll(nameOfPlace.get(0),date.get(0),true);

        LinearLayout masterLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                viewWidth,
                (int) (50 * density));
        //layoutParams.setMargins(1,0,1,0);

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
            //TextView emptyView = new TextView(context);
            layout = new LinearLayout(context);
            emptyLayout = new LinearLayout(context);
            emptyLayout.setLayoutParams(emptyViewParam);

//            Toast.makeText(context,""+item.getStartDay() +" "+ item.getNumberOfDays(),Toast.LENGTH_LONG).show();

            if (i == -1 || i == nameOfPlace.size()) {
                //emptyView.setWidth(viewWidth + halfViewWidth);
//                emptyView.setLayoutParams(emptyViewParam);
//                emptyView.setGravity(Gravity.CENTER_HORIZONTAL);
//                emptyView.setBackgroundColor(context.getResources().getColor(R.color.gray_background_gridview));
                //emptyLayout.addView(emptyView);
                layout.setLayoutParams(layoutParams);

//                layout.addView(emptyView);
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
            //layout.addView(emptyView);
//            place = nameOfPlace[position];

            layout.setBackground(context.getResources().getDrawable(R.drawable.scalloped_rectangle));
            masterLayout.addView(layout);


        }
//        scrollView.setDayAndPlace(nameOfPlace, position);
        scrollView.addView(masterLayout);
//        scrollView.setScrollTo(viewWidth/2, 0);
        containerLayout.addView(scrollView);
    }


    // funkciq za vsqka projekciq prez parametur dannite koito shte se setvat sprqmo cikula za dnite (dannite moje bi ot modela)

//    private String[] searchProjections(int position, String day, String place, HashMap<String, HashMap<String, String[]>> map) {
//        HashMap<String, String[]> days = map.get(place);
//        String[] projections = days.get(day);
//        return projections;
//    }


    public int getScreenWidth() {

        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        return screenWidth;
    }

}
