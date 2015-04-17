package mycinemaapp.com.mycinemaapp;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import adapters.MovieAdapter;
import database.ActorsDataSource;
import database.AllCinemasDataSource;
import database.AllDaysDataSource;
import database.AllGenresDataSource;
import database.GenresDataSource;
import database.MovieDataSource;
import database.MovieDaysDataSource;
import helpers.RequestManager;
import helpers.SessionManager;
import models.Filters;
import models.Movie;
import models.SaveTempMovieModel;
import origamilabs.library.views.StaggeredGridView;

public class MainActivity extends Activity implements View.OnClickListener {

    private ArrayList<Movie> movieList = new ArrayList<>();
    private ArrayList<Filters> filtersList = new ArrayList<>();
    public MovieAdapter movieAdapter;

    private ImageView myProfile, location, filter;

    private Button soon, onCinema, allDays, allCinemas, allGenres;
    private VideoView mVideoView;
    private RelativeLayout main;

    private boolean isInFragment = false;
    private int countOfDays;
    private int dayOfMonth;
    private SessionManager sm;
    private RelativeLayout videoLayout, filterContainer, header;
    private LinearLayout topLayout;
    private ProgressBar progressBar;
    public StaggeredGridView gridView;

    private MovieDataSource movieDataSource;
    private AllDaysDataSource allDaysDataSource;
    private AllCinemasDataSource allCinemasDataSource;
    private AllGenresDataSource allGenresDataSource;
    private ActorsDataSource actorsDataSource;
    private GenresDataSource genresDataSource;
    private MovieDaysDataSource movieDaysDataSource;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initialize(savedInstanceState);
        filtersList = allDaysDataSource.getAllFilters();

        String clear = getIntent().getStringExtra("CLEAR");
        if (clear != null && clear.equals("clear")) {
            allDays.setText("ALL DAYS");
            allGenres.setText("ALL GENRES");
            allCinemas.setText("ALL CINEMAS");
            clearListOfSelected(filtersList);
        }

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "mycinemaapp.com.mycinemaapp",
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }

//
//        HashMap<String, String[]> onlyProjections = new HashMap<>();
//        HashMap<String, String[]> onlyProjections1 = new HashMap<>();
//        HashMap<String, String[]> onlyProjections2 = new HashMap<>();
//        HashMap<String, String[]> onlyProjections3 = new HashMap<>();
//        HashMap<String, String[]> onlyProjections4 = new HashMap<>();
//
//        String[] projections = {"14:00", "15:00", "20:00"};
//        String[] projections1 = {"16:00", "17:00", "22:00"};
//        String[] projections2 = {""};
//        String[] projections3 = {"13:30", "15:22", "11:11"};
//        String[] projections4 = {"16:30", "22:22", "24:24"};
//
//        allProjections.put("Mall Varna", onlyProjections);
//        allProjections.put("Grand", onlyProjections1);
//        allProjections.put("Kino", onlyProjections2);
//        allProjections.put("Kino2", onlyProjections3);
//        allProjections.put("Kino3", onlyProjections4);
//
//        Filter item = new Filter();
//        item.setFilter("ALL CINEMAS");
//
//        Filter item1 = new Filter();
//        item1.setFilter("Mall Varna");
//
//        Filter item2 = new Filter();
//        item2.setFilter("Grand");
//
//        Filter item3 = new Filter();
//        item3.setFilter("Kino");
//        AllCinemasFilters.allCinemas.clear();
//        AllCinemasFilters.allCinemas.add(item);
//        AllCinemasFilters.allCinemas.add(item1);
//        AllCinemasFilters.allCinemas.add(item2);
//        AllCinemasFilters.allCinemas.add(item3);
//
//        nameOfPlaces.add("Mall Varna");
//        nameOfPlaces.add("Grand");
//        nameOfPlaces.add("Kino");
//        nameOfPlaces.add("Kino2");
//        nameOfPlaces.add("Kino3");
//
//        Calendar calendar = new GregorianCalendar();
//        countOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int year = calendar.get(Calendar.YEAR);
//        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
//
//        int allDay = dayOfMonth;
//        String day;
//        AllDaysFilters.allDays.clear();
//        for (int i = dayOfMonth; i < countOfDays + 1; i++) {
//
//            day = allDay + "." + month + "." + year;
//            if (i == dayOfMonth) {
//                Filter filter = new Filter();
//                filter.setFilter("ALL DAYS");
//                AllDaysFilters.allDays.add(filter);
//            } else {
//                Filter filter = new Filter();
//                filter.setFilter(day);
//                AllDaysFilters.allDays.add(filter);
//            }
//
//            days.add(day);
//            onlyProjections.put(day, projections);
//            onlyProjections1.put(day, projections1);
//            onlyProjections2.put(day, projections2);
//            onlyProjections3.put(day, projections3);
//            onlyProjections4.put(day, projections4);
//            allDay++;
//            nameOfDays.add(symbols.getWeekdays()[2]);
//        }
//        countOfDays = countOfDays - dayOfMonth;
        movieRequest();
    }

    public void initialize(Bundle savedInstanceState) {
        sm = new SessionManager(this);
        movieDataSource = new MovieDataSource(this);
        allDaysDataSource = new AllDaysDataSource(this);
        allCinemasDataSource = new AllCinemasDataSource(this);
        allGenresDataSource = new AllGenresDataSource(this);
        actorsDataSource = new ActorsDataSource(this);
        genresDataSource = new GenresDataSource(this);
        movieDaysDataSource = new MovieDaysDataSource(this);
        movieDaysDataSource.open();
        genresDataSource.open();
        actorsDataSource.open();
        allCinemasDataSource.open();
        allGenresDataSource.open();
        allDaysDataSource.open();
        movieDataSource.open();

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
        mVideoView = (VideoView) findViewById(R.id.image);
        allDays = (Button) findViewById(R.id.all_days);
        allDays.setOnClickListener(this);
        allCinemas = (Button) findViewById(R.id.all_cinemas);
        allCinemas.setOnClickListener(this);
        allGenres = (Button) findViewById(R.id.all_genres);
        allGenres.setOnClickListener(this);

        videoLayout = (RelativeLayout) findViewById(R.id.video_layout);
        topLayout = (LinearLayout) findViewById(R.id.top_layout);
        filterContainer = (RelativeLayout) findViewById(R.id.filter_container);
        filterContainer.setOnClickListener(this);
        header = (RelativeLayout) findViewById(R.id.header);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        gridView = (StaggeredGridView) findViewById(R.id.scroll);

        String videoUrl = "rtsp://r7---sn-4g57kuel.c.youtube.com/CiILENy73wIaGQnnkJV25G5KShMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp";
        try {
            mVideoView.setVideoPath(videoUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
            }
        });
        if (isOnline()) {
            mVideoView.start();
        } else {
            Toast.makeText(this, "Please re - connect your connection!", Toast.LENGTH_LONG).show();
            movieList = movieDataSource.getAllMovieTitle();
            SaveTempMovieModel.setMovies(movieDataSource.getAllMovie());
            movieAdapter = new MovieAdapter(MainActivity.this, R.layout.movie_layout, movieList, false, false, false);
            movieAdapter.notifyDataSetChanged();
            gridView.setAdapter(movieAdapter);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void movieRequest() {

        String url = "http://www.json-generator.com/api/json/get/bHAxRbXHzC?indent=2";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*
                        MESS!!!!!!!!!!!!!!
                        ONE OBJECT OF RESPONSE
                        [
                          {
                            title: 'American Sniper',
                            image_url: 'http://notmytribe.com/wp-content/uploads/2015/03/AMERICAN-SNIPER-POSTER.jpg',
                            rating: '1.5',
                            new_for_week:'',
                            user_rating:'',
                            movie_genre:['Action'],
                            movie_directors:['Klint Istuud'],
                            movie_actors:['Bdradli Kypur','Siena Milur','Dyk Graims'],
                            movie_duration:'135',
                            imdb_url:'http://www.imdb.com/title/tt2179136/?ref_=nv_sr_1',
                            imdb_rating:'4.5',
                            movie_description:'Navy SEAL sniper Chris Kyle\'s pinpoint accuracy saves countless lives on
                            the battlefield and turns him into a legend. Back home to his wife and kids after four tours
                            of duty, however, Chris finds that it is the war he can\'t leave behind.',
                            trailer_url:'rtsp://r3---sn-4g57kues.c.youtube.com/CiILENy73wIaGQkL1rLWuzfZ9xMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp',
                            cinemas:['Mall Varna','Grand Mall'],
                            days:[
                              ['01.04','Monday'],
                              ['02.04','Thuesday'],['03.04','Wednesday'],['04.04','Thursday'],['05.04','Friday'],['06.04','Saturday'],['07.04','Sunday'],
                              ['08.04','Monday'],['09.04','Thuesday'],['10.04','Wednesday'],['11.04','Thursday'],['12.04','Friday'],['13.04','Saturday'],
                              ['14.04','Sunday'],['15.04','Monday'],['16.04','Thuesday'],['17.04','Wednesday'],['18.04','Thursday'],['19.04','Friday'],
                              ['20.04','Saturday'],['21.04','Sunday'],['22.04','Monday'],['23.04','Thuesday'],['24.04','Wednesday'],['25.04','Thursday'],
                              ['26.04','Friday'],['27.04','Saturday'],['28.04','Sunday'],['29.04','Monday'],['30.04','Thuesday'],['31.04','Wednesday']],
                            hours:['13:00','12:02','21:11','22:56']
                          }
                         */

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("movies");

                            movieDataSource.removeAll();
                            allDaysDataSource.removeAll();
                            allCinemasDataSource.removeAll();
                            allGenresDataSource.removeAll();

                            LinkedHashSet<String> days = new LinkedHashSet<>();
                            ArrayList<String> nameOfDays = new ArrayList<>();
                            HashSet<String> nameOfPlaces = new HashSet<>();
                            HashMap<String, String[]> onlyProjections;
                            String[] projections;
                            HashMap<String, HashMap<String, String[]>> allProjections;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

//                                ArrayList<String> directors = new ArrayList<>();
                                ArrayList<String> actors = new ArrayList<>();


                                Movie movie = new Movie();
                                movie.setImageUrl(obj.getString("image_url"));
                                movie.setMovieProgress(Float.parseFloat(obj.getString("rating")));
                                movie.setMovieTitle(obj.getString("title"));
                                movie.setNewForWeek(obj.getString("new_for_week"));
                                movie.setMovieDirectors(obj.getString("movie_directors"));
                                movie.setReleaseDate(obj.getString("release_date"));
//                                for (int j = 0; j < obj.getJSONArray("movie_directors").length(); j++) {
//                                    directors.add(obj.getJSONArray("movie_directors").getString(j));
//                                }
//                                movie.setMovieDirectors(directors);
                                for (int k = 0; k < obj.getJSONArray("movie_genre").length(); k++) {
                                    movie.setMovieGenre(obj.getJSONArray("movie_genre").getString(k));
                                }

                                for (int l = 0; l < obj.getJSONArray("movie_actors").length(); l++) {
                                    actors.add(obj.getJSONArray("movie_actors").getString(l));
                                }
                                movie.setMovieActors(actors);
                                if (!obj.getString("user_rating").equals("")) {
                                    movie.setUserRating(Integer.parseInt(obj.getString("user_rating")));
                                }
                                movie.setImdbUrl(obj.getString("imdb_url"));
                                movie.setDuration(Integer.parseInt(obj.getString("movie_duration")));
                                movie.setFullDescription(obj.getString("movie_description"));
                                movie.setTimeOfProjection(new String[]{});
                                movie.setNumberOfDays(countOfDays);
                                movie.setStartDay(dayOfMonth);
                                movie.setMovieTrailerUrl(obj.getString("trailer_url"));
                                movie.setImdbRating(obj.getString("imdb_rating"));

                                projections = new String[obj.getJSONArray("hours").length()];
                                for (int m = 0; m < obj.getJSONArray("hours").length(); m++) {
                                    projections[m] = obj.getJSONArray("hours").getString(m);
                                }
                                String temp;
                                onlyProjections = new HashMap<>();
//                                days = new ArrayList<>();
                                for (int z = 0; z < obj.getJSONArray("days").length(); z++) {
                                    for (int y = 0; y < obj.getJSONArray("days").getJSONArray(z).length(); y++) {
                                        if (y == 0) {
                                            temp = obj.getJSONArray("days").getJSONArray(z).getString(y);
                                            movie.setDate(temp);
                                            days.add(temp);
                                            onlyProjections.put(temp, projections);
                                        } else {
                                            nameOfDays.add(obj.getJSONArray("days").getJSONArray(z).getString(y));
                                        }
                                    }
                                }
                                String tempCinema;
                                for (int l = 0; l < obj.getJSONArray("cinemas").length(); l++) {
                                    tempCinema = obj.getJSONArray("cinemas").getString(l);
                                    movie.setNameOfPlace(tempCinema);
                                    nameOfPlaces.add(tempCinema);
                                }

                                allProjections = new HashMap<>();
                                for (String s : nameOfPlaces) {
                                    allProjections.put(s, onlyProjections);
                                }

                                movie.setNameDayOfMonth(nameOfDays);
                                movie.setAllProjections(allProjections);

                                movieList.add(movie);
                            }

                            allDaysDataSource.removeAll();
                            actorsDataSource.removeAll();
                            genresDataSource.removeAll();
                            movieDaysDataSource.removeAll();

                            Iterator iterator = days.iterator();

                            for (int i = 0; i < days.size(); i++) {
                                if (iterator.hasNext()) {
                                    allDaysDataSource.createFilter(iterator.next().toString(), nameOfDays.get(i));
                                }
                            }
                            for (String s : nameOfPlaces) {
                                allCinemasDataSource.createFilter(s);
                            }

                            ArrayList<Movie> allMovies = movieDataSource.getAllMovie();
                            ArrayList<Filters> allDays = allDaysDataSource.getAllFilters();
                            for (int i = 0; i < movieList.size(); i++) {
//                                Toast.makeText(MainActivity.this, movieList.get(i).getDate().toString(), Toast.LENGTH_LONG).show();
                                movieDataSource.createMovie(movieList.get(i).getMovieTitle(), movieList.get(i).getMovieProgress(),
                                        movieList.get(i).getImageUrl(), movieList.get(i).getDuration(), movieList.get(i).getImdbUrl(),
                                        movieList.get(i).getFullDescription(), movieList.get(i).getMovieDirectors(), movieList.get(i).getReleaseDate(),
                                        movieList.get(i).getNewForWeek());
                                allMovies = movieDataSource.getAllMovie();
                                for (int j = 0; j < movieList.get(i).getMovieActors().size(); j++) {
                                    actorsDataSource.createActor(allMovies.get(i).getId(), movieList.get(i).getMovieActors().get(j));
                                }
                                for (int k = 0; k < movieList.get(i).getMovieGenre().size(); k++) {
                                    genresDataSource.createGenre(allMovies.get(i).getId(), movieList.get(i).getMovieGenre().get(k));
                                }
                                for (int q = 0; q < movieList.get(i).getDate().size(); q++) {
//                                    Log.d("MOVIE", movieList.get(i).getDate().get(q) + " : " + allDays.get(q).getDayFilter());
                                    for (int z = 0; z < allDays.size(); z++) {
                                        if ((movieList.get(i).getDate().get(q)).equals(allDays.get(z).getDayFilter())) {
                                            movieDaysDataSource.createMovieDay(allDays.get(z).getDayId(), allMovies.get(i).getId());
                                        }
                                    }
                                }
                            }

//                            Toast.makeText(MainActivity.this, movieDataSource.getAllMovie().toString(), Toast.LENGTH_SHORT).show();
                            SaveTempMovieModel.setMovies(movieDataSource.getAllMovie());


                            movieAdapter = new MovieAdapter(MainActivity.this, R.layout.movie_layout, movieList, false, false, false);
                            movieAdapter.notifyDataSetChanged();
                            gridView.setAdapter(movieAdapter);
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Some problem!", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Please re-connect your connection!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        }

        );
        // Add the request to the RequestQueue.
        RequestManager.getRequestQueue().add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon:

                if (sm.getRemember()) {
                    Intent intent = new Intent(this, MyProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rotate_in, R.anim.rotate_out);

                } else {
                    LoginFragment loginFragment = new LoginFragment();
                    FragmentTransaction loginTransaction = getFragmentManager().beginTransaction();
                    loginTransaction.addToBackStack("Login Fragment");
                    loginTransaction.replace(R.id.fragment_container, loginFragment);
                    loginTransaction.commit();
                }
//                Intent intentMyProfile = new Intent(this, LoginActivity.class);
//                startActivity(intentMyProfile);
//                overridePendingTransition(R.anim.rotate_in, R.anim.rotate_out);
                break;
            case R.id.soon:
                videoLayout.setVisibility(View.GONE);
                header.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.on_cinema:
                videoLayout.setVisibility(View.VISIBLE);
                header.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.filter_icon:

                SortFragment sortFragment = new SortFragment();
                FragmentTransaction sortTransaction = getFragmentManager().beginTransaction();
                sortTransaction.addToBackStack("Sort Fragment");
                sortTransaction.add(R.id.fragment_container, sortFragment);
                sortTransaction.commit();
                break;
            case R.id.location_icon:
                Intent intentLocation = new Intent(this, LocationActivity.class);
                startActivity(intentLocation);
                break;
            case R.id.all_days:
                videoLayout.setVisibility(View.GONE);
                topLayout.setVisibility(View.GONE);
                filterContainer.setVisibility(View.VISIBLE);


                isInFragment = true;
                AllDaysFragment allDaysFragment = new AllDaysFragment(allDays, movieAdapter);
                loadFragment(allDaysFragment, R.id.filter_container);
                break;
            case R.id.all_cinemas:
                videoLayout.setVisibility(View.GONE);
                topLayout.setVisibility(View.GONE);
                filterContainer.setVisibility(View.VISIBLE);

                isInFragment = true;
                AllCinemasFragment allCinemasFragment = new AllCinemasFragment(allCinemas);
                loadFragment(allCinemasFragment, R.id.filter_container);
                break;
            case R.id.all_genres:
                videoLayout.setVisibility(View.GONE);
                topLayout.setVisibility(View.GONE);
                filterContainer.setVisibility(View.VISIBLE);

                isInFragment = true;
                AllGenresFragment allGenresFragment = new AllGenresFragment(allGenres);
                loadFragment(allGenresFragment, R.id.filter_container);
                break;
            case R.id.filter_container:
                videoLayout.setVisibility(View.VISIBLE);
                topLayout.setVisibility(View.VISIBLE);
                filterContainer.removeAllViews();
                filterContainer.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isInFragment) {

            getFragmentManager().popBackStack();
            videoLayout.setVisibility(View.VISIBLE);
            topLayout.setVisibility(View.VISIBLE);
            filterContainer.removeAllViews();
            filterContainer.setVisibility(View.GONE);
            isInFragment = false;
        } else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }

    public void loadFragment(Fragment fragment, int container) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("");
        transaction.replace(container, fragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieDataSource.close();
        allDaysDataSource.close();
    }

    public void clearListOfSelected(ArrayList<Filters> filterses) {
        for (int i = 0; i < filterses.size(); i++) {
            if (filterses.get(i).isDaySelected()) {
                filterses.get(i).setDaySelected(false);
            }
            if (filterses.get(i).isCinemaSelected()) {
                filterses.get(i).setCinemaSelected(false);
            }
            if (filterses.get(i).isGenreSelected()) {
                filterses.get(i).setGenreSelected(false);
            }
        }
    }
}