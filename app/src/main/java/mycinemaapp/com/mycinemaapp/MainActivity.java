package mycinemaapp.com.mycinemaapp;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
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

import java.util.ArrayList;

import Adapters.MovieAdapter;
import Helpers.CustomGridView;
import Helpers.RequestManager;
import Models.Movie;

public class MainActivity extends Activity implements View.OnClickListener {

    private ArrayList<Movie> list = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private CustomGridView gridView;
    private ImageView myProfile, location, filter;

    private Button soon, onCinema;
    private VideoView mImageView;
    private RelativeLayout main;

    private Animation animationIn, animationOut;
//    private static boolean isClick;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        movieRequest();
    }

    public void initialize() {
        gridView = (CustomGridView) findViewById(R.id.scroll);
        gridView.setExpanded(true);

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
        //mImageView.measure(mVideoLayout.getWidth(),mVideoLayout.getHeight());

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

//        mImageView.setMediaController(new MediaController(this));
//        mImageView.requestFocus();

        mImageView.start();
    }

//    @Override
//    protected int getLayoutResId() {
//        return R.layout.activity_main;
//    }

//    @Override
//    protected ObservableGridView createScrollable() {
//        ObservableGridView gridView = (ObservableGridView) findViewById(R.id.scroll);
//        gridView.setScrollViewCallbacks(this);
//
//        gridView.setAdapter(movieAdapter);
//        return gridView;
//    }

    public void movieRequest() {

        String url = "http://www.json-generator.com/api/json/get/coGSGsomCq?indent=2";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                Movie movie = new Movie();
                                movie.setImageUrl(obj.getString("image_url"));
                                movie.setMovieProgress(Integer.parseInt(obj.getString("rating")));
                                movie.setMovieTitle(obj.getString("title"));
                                movie.setNewForWeek(obj.getString("new_for_week"));

                                list.add(movie);
                            }
                            movieAdapter = new MovieAdapter(MainActivity.this, R.layout.movie_layout, list);
                            movieAdapter.notifyDataSetChanged();
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

//    public void setIsClick(boolean isClick) {
//        this.isClick = isClick;
//    }
//
//    public boolean getIsClick() {
//        return isClick;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mImageView.start();
    }
}