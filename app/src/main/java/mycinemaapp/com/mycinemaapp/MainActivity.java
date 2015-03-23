package mycinemaapp.com.mycinemaapp;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import Adapters.MovieAdapter;
import Helpers.CustomGridView;
import Helpers.RequestManager;
import Models.Movie;
import Models.SaveTempMovieModel;

public class MainActivity extends Activity implements View.OnClickListener {

    private ArrayList<Movie> list = new ArrayList<>();
    private MovieAdapter movieAdapter;

    private ImageView myProfile, location, filter;

    private Button soon, onCinema;
    private VideoView mImageView;
    private RelativeLayout main;
    private HashMap<String, HashMap<String, String[]>> allProjections = new HashMap<>();
    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<String> nameOfDays = new ArrayList<>();
    private ArrayList<String> nameOfPlaces = new ArrayList<>();
    private int countOfDays;
    private int dayOfMonth;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize(savedInstanceState);

        HashMap<String, String[]> onlyProjections = new HashMap<>();
        HashMap<String, String[]> onlyProjections1 = new HashMap<>();
        HashMap<String, String[]> onlyProjections2 = new HashMap<>();
        String[] projections = {"14:00", "15:00", "20:00"};
        String[] projections1 = {"16:00", "17:00", "22:00"};
        String[] projections2 = {""};

        allProjections.put("Mall Varna", onlyProjections);
        allProjections.put("Grand", onlyProjections1);
        allProjections.put("Kino", onlyProjections2);

        nameOfPlaces.add("Mall Varna");
        nameOfPlaces.add("Grand");
        nameOfPlaces.add("Kino");

        Calendar calendar = new GregorianCalendar();
        countOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
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
            onlyProjections1.put(day, projections1);
            onlyProjections2.put(day, projections2);
            allDay++;
            nameOfDays.add(symbols.getWeekdays()[2]);
        }
        countOfDays = countOfDays - dayOfMonth;

        movieRequest();
    }

    public void initialize(Bundle savedInstanceState) {
        soon = (Button) findViewById(R.id.soon);
        soon.setOnClickListener(this);

        myProfile = (ImageView) findViewById(R.id.user_icon);
        myProfile.setOnClickListener(this);

        onCinema = (Button) findViewById(R.id.on_cinema);
        onCinema.setOnClickListener(this);
        main = (RelativeLayout) findViewById(R.id.main);
        location = (ImageView) findViewById(R.id.location_icon);
        location.setOnClickListener(this);
        filter = (ImageView) findViewById(R.id.filter_icon);
        filter.setOnClickListener(this);
        mImageView = (VideoView) findViewById(R.id.image);

        String videoUrl = "rtsp://r3---sn-4g57kuek.c.youtube.com/CiILENy73wIaGQk6-2j9f_Wz5RMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp";
        try {
            mImageView.setVideoPath(videoUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mImageView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
            }
        });
        mImageView.start();
    }

    public void movieRequest() {

        String url = "http://www.json-generator.com/api/json/get/bZQedNXOzS?indent=2";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] genre = new String[3];
                        String[] directors = new String[3];
                        String[] actors = new String[3];
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                Movie movie = new Movie();
                                movie.setImageUrl(obj.getString("image_url"));
                                movie.setMovieProgress(Float.parseFloat(obj.getString("rating")));
                                movie.setMovieTitle(obj.getString("title"));
                                movie.setNewForWeek(obj.getString("new_for_week"));
                                for (int j = 0; j < obj.getJSONArray("movie_directors").length(); j++) {
                                    directors[j] = obj.getJSONArray("movie_directors").getString(j);
                                }
                                movie.setMovieDirectors(directors);
                                for (int k = 0; k < obj.getJSONArray("movie_genre").length(); k++) {
                                    genre[k] = obj.getJSONArray("movie_genre").getString(k);
                                }
                                movie.setMovieGenre(genre);
                                for (int l = 0; l < obj.getJSONArray("movie_actors").length(); l++) {
                                    actors[l] = obj.getJSONArray("movie_actors").getString(l);
                                }
                                movie.setMovieActors(actors);
                                if (!obj.getString("user_rating").equals("")) {
                                    movie.setUserRating(Integer.parseInt(obj.getString("user_rating")));
                                }
                                movie.setImdbUrl(obj.getString("imdb_url"));
                                movie.setDuration(obj.getString("movie_duration"));
                                movie.setFullDescription(obj.getString("movie_description"));
                                movie.setAllProjections(allProjections);
                                movie.setDate(days);
                                movie.setNameOfPlace(nameOfPlaces);
                                movie.setNameDayOfMonth(nameOfDays);
                                movie.setTimeOfProjection(new String[]{});
                                movie.setNumberOfDays(countOfDays);
                                movie.setStartDay(dayOfMonth);
                                movie.setMovieTrailerUrl(obj.getString("trailer_url"));
                                list.add(movie);
                            }

                            movieAdapter = new MovieAdapter(MainActivity.this, R.layout.movie_layout, list);
                            movieAdapter.notifyDataSetChanged();
                            SaveTempMovieModel.setMovies(list);

                            CustomGridView gridView = (CustomGridView) findViewById(R.id.scroll);
                            gridView.setExpanded(true);
                            gridView.setAdapter(movieAdapter);
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        RequestManager.getRequestQueue().add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon:
                Intent intentMyProfile = new Intent(this, MyProfileActivity.class);
                startActivity(intentMyProfile);
                overridePendingTransition(R.anim.rotate_in, R.anim.rotate_out);
                break;
            case R.id.soon:
                Toast.makeText(getBaseContext(), "Click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.on_cinema:
                Toast.makeText(getBaseContext(), "Click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.filter_icon:
                SortFragment sortFragment = new SortFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack("Sort Fragment");
                transaction.add(R.id.fragment_container, sortFragment);
                transaction.commit();
                break;
            case R.id.location_icon:
                Intent intentLocation = new Intent(this, LocationActivity.class);
                startActivity(intentLocation);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mImageView.start();
    }
}