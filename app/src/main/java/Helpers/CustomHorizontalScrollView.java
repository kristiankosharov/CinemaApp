package Helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import mycinemaapp.com.mycinemaapp.R;

public class CustomHorizontalScrollView extends HorizontalScrollView implements
        View.OnTouchListener, GestureDetector.OnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 10;

    private static final int SWIPE_THRESHOLD_VELOCITY = 300;
    private static final int SWIPE_PAGE_ON_FACTOR = 10;
    private int overScrollX, scrollToX, scrollToY;

    private String day, place;
    private ArrayList<String> nameOfPlace;
    private ArrayList<String> days;
    private HashMap<String, HashMap<String, String[]>> map;

    private GestureDetector gestureDetector;
    private int scrollTo = 0;
    private int maxItem = 0;
    private int activeItem = 0;
    private float prevScrollX = 0;
    private boolean start = true;
    private int itemWidth = 0;
    private float currentScrollX;
    private boolean flingDisable = false;

    private Activity context;
    private RelativeLayout containerLayout;
    private boolean isDaysScroll, isPlaceScroll, isProjectionScroll;
    private static int activeDay = 0;
    private static int activePlace = 0;

    public CustomHorizontalScrollView(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    public CustomHorizontalScrollView(Activity context, int maxItem,
                                      int itemWidth, int overScrollX, RelativeLayout containerLayout) {
        this(context);
        this.maxItem = maxItem;
        this.itemWidth = itemWidth;
        gestureDetector = new GestureDetector(this);
        this.setOnTouchListener(this);
        this.overScrollX = overScrollX;
        this.context = context;
        this.containerLayout = containerLayout;
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if (gestureDetector.onTouchEvent(event)) {
//            return true;
//        }
//        Boolean returnValue = gestureDetector.onTouchEvent(event);
//
//        int x = (int) event.getRawX();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                if (start) {
//                    this.prevScrollX = x;
//                    start = false;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                start = true;
//                this.currentScrollX = x;
//                int minFactor = itemWidth
//                        / SWIPE_PAGE_ON_FACTOR;
//
//                if ((this.prevScrollX - this.currentScrollX) > minFactor) {
//                    if (activeItem < maxItem - 1)
//                        activeItem = activeItem + 1;
//
//                } else if ((this.currentScrollX - this.prevScrollX) > minFactor) {
//                    if (activeItem > 0)
//                        activeItem = activeItem - 1;
//                }
//                //System.out.println("horizontal : " + activeItem);
//                scrollTo = activeItem * itemWidth;
//                this.smoothScrollTo(scrollTo, 0);
//                returnValue = true;
//                break;
//        }
//
//
//        return returnValue;
//    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
//        if (flingDisable) {
//            Toast.makeText(getContext(),"fling",Toast.LENGTH_SHORT).show();
//            return false;
//        }

        Boolean returnValue;

//
        int x = (int) e1.getRawX();
        float ptx1 = 0, ptx2 = 0;

        ptx1 = e1.getX();
        ptx2 = e2.getX();

        returnValue = false;

        start = true;
        this.currentScrollX = x;
        int minFactor = itemWidth
                / 2;
        int minFactorMinus = -minFactor;

        if (ptx1 - ptx2 > 0 && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            //right to left slide
            //positive residue
            if ((ptx1 - ptx2) < minFactor
                    && activeItem != maxItem
                    ) {
                activeItem = activeItem + 1;

                isFromScroll(isDaysScroll, isPlaceScroll, isProjectionScroll, activeItem);
//                Toast.makeText(getContext(), activeItem + ":" + maxItem, Toast.LENGTH_SHORT).show();

            } else if (activeItem < maxItem - 2) {
                activeItem = activeItem + 3;
                isFromScroll(isDaysScroll, isPlaceScroll, isProjectionScroll, activeItem);
//                Toast.makeText(getContext(), activeItem + " +3 :" + maxItem, Toast.LENGTH_SHORT).show();
            }

            returnValue = true;

        } else if (ptx1 - ptx2 < 0 && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            //left to right
            // negative residue
            if ((ptx1 - ptx2) > minFactorMinus
                    && activeItem != 0
                    ) {
                activeItem = activeItem - 1;
                isFromScroll(isDaysScroll, isPlaceScroll, isProjectionScroll, activeItem);
            } else if (activeItem > 2) {
                activeItem = activeItem - 3;
                isFromScroll(isDaysScroll, isPlaceScroll, isProjectionScroll, activeItem);
            }

//            Toast.makeText(getContext(), activeItem + "", Toast.LENGTH_SHORT).show();
            returnValue = true;
        }
        scrollTo = activeItem * itemWidth;
        this.smoothScrollTo(scrollTo, 0);

        return returnValue;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, overScrollX / 2, maxOverScrollY, isTouchEvent);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(scrollToX, scrollToY);
    }

    public void setScrollTo(int x, int y) {
        scrollToX = x;
        scrollToY = y;

    }

    public void setDayAndPlace(ArrayList<String> nameOfPlace, ArrayList<String> days, HashMap<String, HashMap<String, String[]>> map) {
//        this.day = day;
        this.nameOfPlace = nameOfPlace;
        this.days = days;
        this.map = map;
//        place = this.nameOfPlace[position];
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private void isFromScroll(boolean isDaysScroll, boolean isPlaceScroll, boolean isProjectionScroll, int position) {
        if (isDaysScroll) {

            activeDay = position;
            createProjectionScroll(nameOfPlace.get(activePlace), days.get(activeDay), false);

        } else if (isPlaceScroll) {

            activePlace = position;
//            Toast.makeText(context,activePlace+"",Toast.LENGTH_SHORT).show();
            createProjectionScroll(nameOfPlace.get(activePlace), days.get(activeDay), false);

        } else if (isProjectionScroll) {

        }
    }

    public void createProjectionScroll(String place, String day, boolean fromAdapter) {

//        LayoutInflater inflater = context.getLayoutInflater();
//        View view = inflater.inflate(R.layout.movie_detail_item,null,false);

//        RelativeLayout containerLayout = (RelativeLayout) view.findViewById(R.id.projection_horizontal_scroll_view);

//        Button b = (Button)view.findViewById(R.id.rate_button);
//        b.setText("aa");

//        containerLayout.removeAllViews();
        float density = context.getResources().getDisplayMetrics().density;
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int viewWidth = screenWidth / 3;

        final CustomHorizontalScrollView scrollView = new CustomHorizontalScrollView(context, 3, viewWidth, viewWidth, containerLayout);
        scrollView.clearAnimation();
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.fromScroll(false, false, true);


        if (!fromAdapter) {
            Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top);
            final Animation animation2 = AnimationUtils.loadAnimation(context, R.anim.top_to_bottom);

            scrollView.clearAnimation();
            scrollView.startAnimation(animation1);


            animation1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    scrollView.startAnimation(animation2);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        LinearLayout masterLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                viewWidth,
                (int) (40 * density));
        layoutParams.setMargins(0, (int) (15 * density), 0, 0);

        LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(
                viewWidth,
                (int) (50 * density));
        textViewParam.weight = 1;
        int halfViewWidth = viewWidth / 2;

        TextView placeView;
        LinearLayout layout;
        LinearLayout emptyLayout;

        String[] projections;
//        Toast.makeText(context,place + day,Toast.LENGTH_SHORT).show();
        projections = map.get(place).get(day);

        if (projections != null) {
//        Toast.makeText(context,map.get(place).toString(),Toast.LENGTH_SHORT).show();

            for (int i = -1; i < projections.length + 1; i++) {

                placeView = new TextView(context);
                TextView emptyView = new TextView(context);
                layout = new LinearLayout(context);

//            Toast.makeText(context,""+item.getStartDay() +" "+ item.getNumberOfDays(),Toast.LENGTH_LONG).show();

                if (i == -1 || i == projections.length) {

//                emptyView.setLayoutParams(emptyViewParam);
//                emptyView.setGravity(Gravity.CENTER_HORIZONTAL);
//                emptyView.setBackgroundColor(context.getResources().getColor(R.color.gray_background_gridview));
                    //emptyLayout.addView(emptyView);
                    layout.setLayoutParams(layoutParams);
                    layout.setVisibility(View.INVISIBLE);

//                layout.addView(emptyView);
                } else if (projections[i] == "" || projections[i] == null) {
                    emptyView.setWidth(viewWidth);
                    emptyView.setText("NO SHOW");
                    emptyView.setTextColor(Color.WHITE);
                    emptyView.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                    layout.setLayoutParams(layoutParams);
                    layout.setBackgroundColor(getResources().getColor(R.color.primaryDark));
                    layout.addView(emptyView);
                } else {

                    layout.setLayoutParams(layoutParams);
//                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                    layout.setOrientation(LinearLayout.VERTICAL);

//                placeView.setWidth(viewWidth);
                    placeView.setLayoutParams(textViewParam);
                    placeView.setGravity(Gravity.CENTER);
                    placeView.setText(projections[i]);
//                placeView.setTag(i);
                    layout.addView(placeView);

                    layout.setLayoutParams(layoutParams);
                    layout.setBackground(context.getResources().getDrawable(R.drawable.ticket));

                }
                //layout.addView(emptyView);

                masterLayout.addView(layout);
            }
            scrollView.addView(masterLayout);
//        scrollView.setScrollTo(viewWidth/2, 0);
            containerLayout.removeAllViews();
            containerLayout.addView(scrollView);
        } else {
            Toast.makeText(context, "String == null", Toast.LENGTH_SHORT).show();
        }
    }

    public void fromScroll(boolean isDaysScroll, boolean isPlaceScroll, boolean isProjectionScroll) {
        this.isDaysScroll = isDaysScroll;
        this.isPlaceScroll = isPlaceScroll;
        this.isProjectionScroll = isProjectionScroll;
    }
}