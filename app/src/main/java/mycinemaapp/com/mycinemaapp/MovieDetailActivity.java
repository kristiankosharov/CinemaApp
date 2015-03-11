package mycinemaapp.com.mycinemaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import Adapters.MovieDetailAdapter;
import Models.MovieDetail;

/**
 * Created by kristian on 15-3-4.
 */
public class MovieDetailActivity extends BaseActivity {

    private ViewPager mViewPager;
    private MovieDetailAdapter adapter;
    private ArrayList<MovieDetail> list = new ArrayList<>();
    private static final String TAG = "MovieDetailActivity";
    HorizontalScrollView mHorizontalScrollView;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);

        LayoutInflater inflater = getLayoutInflater();

        v = inflater.inflate(R.layout.movie_detail_item, null);

        //mHorizontalScrollView = (HorizontalScrollView) v.findViewById(R.id.horizontal_scroll_view);
        //mHorizontalScrollView.setNextFocusRightId(R.id.time4);

//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int screenWidth = size.x;
//        int viewWidth = screenWidth / 3;


//        TextView txt = new TextView(this);
//        txt.setId(generateViewId());
//        txt.setText("2222");
//        mHorizontalScrollView.setBackgroundColor(getResources().getColor(R.color.gray_background_gridview));
//        mHorizontalScrollView.addView(txt);

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        String title = intent.getStringExtra("TITLE");
        int progress = intent.getIntExtra("PROGRESS", 0);
//        HashMap<String, HashMap<String, String[]>> projections = (HashMap<String, HashMap<String, String[]>>) intent.getSerializableExtra("PROJECTIONS");

//        Toast.makeText(this, "projections" + projections.toString(), Toast.LENGTH_SHORT).show();

        HashMap<String, HashMap<String, String[]>> allProjections = new HashMap<>();
        HashMap<String, String[]> onlyProjections = new HashMap<>();
        HashMap<String, String[]> onlyProjections1 = new HashMap<>();
        String[] projections = {"14:00", "15:00", "20:00"};
        String[] projections1 = {"16:00", "17:00", "22:00"};

        allProjections.put("Mall Varna", onlyProjections);
        allProjections.put("Grand", onlyProjections1);


        String[] directors = {"az", "ti"};
        String[] genres = {"az", "ti"};
        String[] actors = {"az", "ti"};

        ArrayList<String> days = new ArrayList<>();
        ArrayList<String> nameOfDays = new ArrayList<>();
        ArrayList<String> nameOfPlaces = new ArrayList<>();
        nameOfPlaces.add("Mall Varna");
        nameOfPlaces.add("Grand");

        //Toast.makeText(this,url + "," + title + "," + progress,Toast.LENGTH_LONG).show();

        Calendar calendar = new GregorianCalendar();
        int countOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());


        int allDay = dayOfMonth;
        String day;
        for (int i = dayOfMonth; i < countOfDays + 1; i++) {

            day = allDay + "." + month + "." + year;
            days.add(day);
            onlyProjections.put(day, projections);
            onlyProjections1.put(day,projections1);
            allDay++;
            nameOfDays.add(symbols.getWeekdays()[2]);
        }

//        for (int i =0;i<days.size();i++){
//            Toast.makeText(this, days.get(i), Toast.LENGTH_SHORT).show();
//        }

        countOfDays = countOfDays - dayOfMonth;


        for (int i = 0; i < 10; i++) {
            MovieDetail detail = new MovieDetail();
            detail.setImageUrl(url);
            detail.setMovieDirectors(directors);
            detail.setMovieActors(actors);
            detail.setMovieGenre(genres);
            detail.setMovieTitle(title);
            detail.setRating(progress);
            detail.setAllProjections(allProjections);

            detail.setDuration("110min . 30.01.15");
            detail.setDate(days);
            detail.setFullDescription("ldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjfldjflj" +
                    "sdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjf" +
                    "sdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjf" +
                    "sdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjf" +
                    "sdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjf" +
                    "sdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjf" +
                    "sdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjf" +
                    "sdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjf" +
                    "sdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjf" +
                    "sdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjfldjfljsdfhlkjfldksjf");
            detail.setNameOfPlace(nameOfPlaces);
            detail.setNameDayOfMonth(nameOfDays);
            detail.setTimeOfProjection(new String[]{});
            detail.setNumberOfDays(countOfDays);
            detail.setStartDay(dayOfMonth);
            detail.setMovieTrailerUrl("rtsp://r1---sn-4g57kues.c.youtube.com/CiILENy73wIaGQnVYkLyUyJ2kRMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
            list.add(detail);
        }


        //Toast.makeText(this,list.toString(),Toast.LENGTH_LONG).show();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new MovieDetailAdapter(this, list);
        adapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(3);
        mViewPager.setAdapter(adapter);


    }

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public void changePagerStatus() {

    }
}
