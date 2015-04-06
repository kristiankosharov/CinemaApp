package mycinemaapp.com.mycinemaapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import java.util.concurrent.atomic.AtomicInteger;

import adapters.MovieDetailAdapter;
import models.AddMovies;
import models.RatedMovies;
import models.SaveTempMovieModel;

/**
 * Created by kristian on 15-3-4.
 */
public class MovieDetailActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private MovieDetailAdapter adapter;
    private static final String TAG = "MovieDetailActivity";
    private static final int RESULT_CODE = 1;
    HorizontalScrollView mHorizontalScrollView;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    private ImageView back, share;

    private View v;
    private boolean isList;
    private boolean isRated;
    private boolean isBought;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);

        LayoutInflater inflater = getLayoutInflater();

        v = inflater.inflate(R.layout.movie_detail_item, null);

        back = (ImageView) findViewById(R.id.back);
        share = (ImageView) findViewById(R.id.share);

        back.setOnClickListener(this);
        share.setOnClickListener(this);

        Intent intent = getIntent();
        position = intent.getIntExtra("POSITION", 0);
        isList = intent.getBooleanExtra("ISLIST", false);
        isRated = intent.getBooleanExtra("ISRATED", false);
        isBought = intent.getBooleanExtra("ISBOUGHT", false);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        if (isList) {
            adapter = new MovieDetailAdapter(this, AddMovies.getAddMovie(), isList, isRated, isBought);
        } else if (isRated) {
            adapter = new MovieDetailAdapter(this, RatedMovies.getRatedMovies(), isList, isRated, isBought);
        } else if (isBought) {
        } else {
            adapter = new MovieDetailAdapter(this, SaveTempMovieModel.getMovies(), isList, isRated, isBought);
        }
        adapter.notifyDataSetChanged();
        mViewPager.setPageMargin(20);
        mViewPager.setBackgroundColor(this.getResources().getColor(R.color.gray_background_gridview));
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (isList) {
            adapter = new MovieDetailAdapter(this, AddMovies.getAddMovie(), isList, isRated, isBought);
        } else if (isRated) {
            adapter = new MovieDetailAdapter(this, RatedMovies.getRatedMovies(), isList, isRated, isBought);
        } else if (isBought) {
        } else {
            adapter = new MovieDetailAdapter(this, SaveTempMovieModel.getMovies(), isList, isRated, isBought);
        }
        adapter.notifyDataSetChanged();
        if (adapter.getCount() == 0) {
            onBackPressed();
        }

        mViewPager.setPageMargin(20);
        mViewPager.setBackgroundColor(this.getResources().getColor(R.color.gray_background_gridview));
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_CODE) {
                position = data.getIntExtra("POSITION", 0);
            }
        } else if(requestCode == 2){
            if(resultCode == 2){
                position = data.getIntExtra("POSITION", 0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.share:
                ShareFragment shareFragment = new ShareFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack("Share Fragment");
                transaction.add(R.id.fragment_container, shareFragment);
                transaction.commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("ISLIST", position);
        intent.putExtra("ISRATED", position);
        intent.putExtra("ISBOUGHT", position);
        super.onBackPressed();
    }
}
