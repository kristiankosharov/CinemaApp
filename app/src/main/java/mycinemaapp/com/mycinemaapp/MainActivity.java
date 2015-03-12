package mycinemaapp.com.mycinemaapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

import Adapters.MovieAdapter;
import Models.Movie;
import observablescrollview.ObservableGridView;
import observablescrollview.ObservableScrollViewCallbacks;

public class MainActivity extends SlidingUpBaseActivity<ObservableGridView> implements ObservableScrollViewCallbacks, View.OnClickListener {

    private ArrayList<Movie> list = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private ObservableGridView gridView;
    private ImageView myProfile;

    private Button soon, onCinema;
    private ImageView location;
    private VideoView mImageView;
    private RelativeLayout main;

    private Animation animationIn, animationOut;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();


        Movie el = new Movie();
        el.setImageUrl("http://cdn.collider.com/wp-content/uploads/american-sniper-poster-international.jpg");
        el.setMovieProgress(150);
        el.setMovieTitle("American Sniper");
//        el.setAllProjections(allProjections);
        el.setNewForWeek("NOVO TAZI SEDMICA");

        list.add(el);


        Movie newItem = new Movie();
        newItem.setImageUrl("http://img3.wikia.nocookie.net/__cb20140811182139/spongebob/images/d/d6/Spongebob_2.jpg");
        newItem.setMovieProgress(150);
        newItem.setMovieTitle("Sponge Bob 2015");
        newItem.setNewForWeek("NOVO TAZI SEDMICA");

        list.add(newItem);

        for (int i = 0; i <= 10; i++) {
            Movie item = new Movie();
            item.setImageUrl("http://www.logostage.com/logos/New_Line_Cinema.png");
            item.setMovieProgress(30 * i);
            //item.setNewForWeek("");
            item.setMovieTitle("Titles:" + i);
            list.add(item);
        }

        movieAdapter = new MovieAdapter(this, R.layout.movie_layout, list);
        movieAdapter.notifyDataSetChanged();
        gridView.setAdapter(movieAdapter);


    }

    public void initialize() {
        gridView = (ObservableGridView) findViewById(R.id.scroll);
        soon = (Button) findViewById(R.id.soon);
        soon.setOnClickListener(this);

        myProfile = (ImageView) findViewById(R.id.user_icon);
        myProfile.setOnClickListener(this);

        onCinema = (Button) findViewById(R.id.on_cinema);
        onCinema.setOnClickListener(this);
        main = (RelativeLayout) findViewById(R.id.main);
        location = (ImageView) findViewById(R.id.location_icon);
        location.setOnClickListener(this);

        mImageView = (VideoView) findViewById(R.id.image);
        //mImageView.measure(mVideoLayout.getWidth(),mVideoLayout.getHeight());

        String videoUrl = "rtsp://r3---sn-4g57kuek.c.youtube.com/CiILENy73wIaGQk6-2j9f_Wz5RMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp";
        try {
            mImageView.setVideoPath(videoUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        mImageView.setMediaController(new MediaController(this));
//        mImageView.requestFocus();

        mImageView.start();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected ObservableGridView createScrollable() {
        ObservableGridView gridView = (ObservableGridView) findViewById(R.id.scroll);
        gridView.setScrollViewCallbacks(this);

        gridView.setAdapter(movieAdapter);
//        setDummyData(gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Item " + (position + 1) + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return gridView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon:
//                main.clearAnimation();
//                main.setAnimation(animationIn);
//                main.startAnimation(animationIn);
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