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

import Adapters.MovieDetailAdapter;
import Models.SaveTempMovieModel;

/**
 * Created by kristian on 15-3-4.
 */
public class MovieDetailActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private MovieDetailAdapter adapter;
    private static final String TAG = "MovieDetailActivity";
    HorizontalScrollView mHorizontalScrollView;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    private ImageView back, share;

    private View v;

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
        int position = intent.getIntExtra("POSITION", 0);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new MovieDetailAdapter(this, SaveTempMovieModel.getMovies());

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
}
