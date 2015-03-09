package Helpers;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class CustomHorizontalScrollView extends HorizontalScrollView implements
        View.OnTouchListener, GestureDetector.OnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 10;

    private static final int SWIPE_THRESHOLD_VELOCITY = 300;
    private static final int SWIPE_PAGE_ON_FACTOR = 10;
    private int overScrollX, scrollToX, scrollToY;


    private GestureDetector gestureDetector;
    private int scrollTo = 0;
    private int maxItem = 0;
    private int activeItem = 0;
    private float prevScrollX = 0;
    private boolean start = true;
    private int itemWidth = 0;
    private float currentScrollX;
    private boolean flingDisable = false;

    public CustomHorizontalScrollView(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    public CustomHorizontalScrollView(Context context, int maxItem,
                                      int itemWidth, int overScrollX) {
        this(context);
        this.maxItem = maxItem;
        this.itemWidth = itemWidth;
        gestureDetector = new GestureDetector(this);
        this.setOnTouchListener(this);
        this.overScrollX = overScrollX;
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
//                    ||  activeItem < maxItem - 1 || activeItem > 0
                    ) {
                activeItem = activeItem + 1;
            } else {
                activeItem = activeItem + 3;
            }

            returnValue = true;

        } else if (ptx1 - ptx2 < 0 && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            //left to right
            // negative residue
            if ((ptx1 - ptx2) > minFactorMinus
//                    || activeItem < maxItem - 1 || activeItem > 0
                    ) {
                activeItem = activeItem - 1;
            } else {
                activeItem = activeItem - 3;
            }

            returnValue = true;
        }

        scrollTo = activeItem * itemWidth;
        this.smoothScrollTo(scrollTo, 0);

        return returnValue;

//        boolean returnValue = false;
//        float ptx1 = 0, ptx2 = 0;
//        if (e1 == null || e2 == null)
//            return false;
//        ptx1 = e1.getX();
//        ptx2 = e2.getX();
//        // right to left
//
//        if (ptx1 - ptx2 > SWIPE_MIN_DISTANCE
//                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//            if (activeItem < maxItem - 1)
//                activeItem = activeItem + 1;
//
//            returnValue = true;
//
//        } else if (ptx2 - ptx1 > SWIPE_MIN_DISTANCE
//                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//            if (activeItem > 0)
//                activeItem = activeItem - 1;
//
//            returnValue = true;
//        }
//        Toast.makeText(getContext(), "fling", Toast.LENGTH_SHORT).show();
//        scrollTo = activeItem * itemWidth;
//        this.smoothScrollTo(0, scrollTo);
//        return returnValue;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}