package mycinemaapp.com.mycinemaapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.facebook.Session;

import java.util.ArrayList;
import java.util.List;

import Adapters.TabsPagerAdapter;
import Helpers.SessionManager;
import Helpers.SlidingTabLayout;

/**
 * Created by kristian on 15-2-26.
 */
public class MyProfileActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private SlidingTabLayout mSlidingTabLayout;

    private String[] tabs;
    private Toolbar mToolbar;
    private ImageView activityButton, logoutButton;

    private NetworkImageView profileImage;
    private TextView profileName, profileEmail;
    private SessionManager sm;
    private RelativeLayout main;
    /**
     * List of {@link SamplePagerItem} which represent this sample's tabs.
     */
    private List<String> mTabs = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_fragment);
        sm = new SessionManager(this);
        Animation animationOut = AnimationUtils.loadAnimation(this, R.anim.rotate_out);

//        main = (RelativeLayout)findViewById(R.id.main);
//        main.clearAnimation();
//        main.setAnimation(animationOut);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        //actionBar = getActionBar();
        tabs = getResources().getStringArray(R.array.tabs);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), tabs, 3);


        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.pager_item, null, false);
        float density = getResources().getDisplayMetrics().density;

        activityButton = (ImageView) findViewById(R.id.activity_button);
        logoutButton = (ImageView) findViewById(R.id.logout);
        activityButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);


        mSlidingTabLayout.setCustomTabView(R.layout.pager_item, R.id.item_title);
        mSlidingTabLayout.setViewPager(viewPager);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        //actionBar = getSupportActionBar();
//        getSupportActionBar().setHomeButtonEnabled(false);
//        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //mSlidingTabLayout.setCustomTabView(R.layout.pager_item,R.id.item_title);

        //mTabs.add(new ListOfMoviewFragment());
//        mTabs.add(new TicketFragment());
//        mTabs.add(new MarkFragment());


    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), tabs, 3);
        viewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_button:
                onBackPressed();
                overridePendingTransition(R.anim.rotate_in, R.anim.rotate_out);
//                Intent intent = new Intent(this,MainActivity.class);
//                startActivity(intent);
                break;
            case R.id.logout:
                logoutDialog();
                break;
        }
    }

    /**
     * This class represents a tab to be displayed by {@link ViewPager} and it's associated
     * {@link SlidingTabLayout}.
     */
//    static class SamplePagerItem {
//        private final String mTitle;
//
//        SamplePagerItem(String title) {
//            mTitle = title;
//        }
//
//        /**
//         * @return A new {@link android.support.v4.app.Fragment} to be displayed by a {@link ViewPager}
//         */
//        Fragment createFragment() {
//            return ContentFragment.newInstance(mTitle);
//        }
//
//        /**
//         * @return the title which represents this tab. In this sample this is used directly by
//         * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
//         */
//        CharSequence getTitle() {
//            return mTitle;
//        }
//
//    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.rotate_in, R.anim.rotate_out);
    }

    private void logoutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sm.logOut();

                        Session session = Session.getActiveSession();
                        if (session != null) {

                            if (!session.isClosed()) {
                                session.closeAndClearTokenInformation();
                                //clear your preferences if saved
                            }
                        } else {

                            session = new Session(MyProfileActivity.this);
                            Session.setActiveSession(session);
                            session.closeAndClearTokenInformation();
                            //clear your preferences if saved

                        }

                        Intent intent = new Intent(MyProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setMessage("Do you want to log out?");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void resetAdapter(){
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), tabs, 3);
        viewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(viewPager);
    }
}
