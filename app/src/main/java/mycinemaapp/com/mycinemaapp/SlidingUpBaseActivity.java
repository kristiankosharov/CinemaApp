/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mycinemaapp.com.mycinemaapp;


import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import observablescrollview.ObservableScrollViewCallbacks;
import observablescrollview.ScrollState;
import observablescrollview.ScrollUtils;
import observablescrollview.Scrollable;
import observablescrollview.TouchInterceptionFrameLayout;

public abstract class SlidingUpBaseActivity<S extends Scrollable> extends BaseActivity implements ObservableScrollViewCallbacks,View.OnClickListener {

    private static final String STATE_SLIDING_STATE = "slidingState";
    private static final int SLIDING_STATE_TOP = 0;
    private static final int SLIDING_STATE_BELLOW_TOP = 1;
    private static final int SLIDING_STATE_MIDDLE = 2;
    private static final int SLIDING_STATE_BOTTOM = 3;

    private View mHeader;
    private View mHeaderBar;
    private View mHeaderOverlay;
    private View mHeaderFlexibleSpace;
    private Button mTitle;
    private TextView mToolbarTitle;

    private View mFab;
    private Toolbar mToolbar;
    private S mScrollable;
    private TouchInterceptionFrameLayout mInterceptionLayout;

    private LinearLayout topLayout;

    // Fields that just keep constants like resource values
    private int mActionBarSize;
    private int mIntersectionHeight;
    private int mHeaderBarHeight;
    private int mSlidingSlop;
    private int mSlidingHeaderBlueSize;
    private int mColorPrimary;
    private int mFlexibleSpaceImageHeight;
    private int mToolbarColor;
    private int mFabMargin;

    // Fields that needs to saved
    private int mSlidingState;

    // Temporary states
    private boolean mFabIsShown;
    private boolean mMoved;
    private float mInitialY;
    private float mMovedDistanceY;
    private float mScrollYOnDownMotion;

    // These flags are used for changing header colors.
    private boolean mHeaderColorIsChanging;
    private boolean mHeaderColorChangedToBottom;
    private boolean mHeaderIsAtBottom;
    private boolean mHeaderIsNotAtBottom;

    private VideoView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        mActionBarSize = getActionBarSize();
        mColorPrimary = getResources().getColor(R.color.primary);
        mToolbarColor = getResources().getColor(R.color.primary);
        //mToolbar.setBackgroundColor(Color.TRANSPARENT);
        //mToolbar.setTitle("");

        float density = getResources().getDisplayMetrics().density;

        mFlexibleSpaceImageHeight = (int)(getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height) * density);
        mIntersectionHeight = (int)(getResources().getDimensionPixelSize(R.dimen.intersection_height) * density);
        mHeaderBarHeight = (int)(getResources().getDimensionPixelSize(R.dimen.header_bar_height) * density);
        mSlidingSlop = (int)(getResources().getDimensionPixelSize(R.dimen.sliding_slop) * density);

        mSlidingHeaderBlueSize = (int)(getResources().getDimensionPixelSize(R.dimen.sliding_overlay_blur_size) * density);

        mHeader = findViewById(R.id.header);
        mHeaderBar = findViewById(R.id.header_bar);
//        mHeaderOverlay = findViewById(R.id.header_overlay);
//        mHeaderFlexibleSpace = findViewById(R.id.header_flexible_space);
        mImageView = (VideoView) findViewById(R.id.image);

        topLayout = (LinearLayout) findViewById(R.id.top_layout);
        mFabMargin = (int)(getResources().getDimensionPixelSize(R.dimen.margin_standard) * density);

//        mInterceptionLayout = (TouchInterceptionFrameLayout) findViewById(R.id.scroll_wrapper);
        mTitle = (Button) findViewById(R.id.all_days);
        //mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ViewHelper.setScaleY(mToolbar, 0);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        mScrollable = createScrollable();

//        mFab = findViewById(R.id.fab);

        //topLayout.setBackgroundColor(Color.WHITE);


        mInterceptionLayout.setScrollInterceptionListener(mInterceptionListener);

        //mTitle.setText(getTitle());

        //mToolbarTitle.setText(mTitle.getText());
        //ViewHelper.setAlpha(mToolbarTitle, 0);
        ViewHelper.setTranslationY(mTitle, (mHeaderBarHeight - mActionBarSize) / 2);

        if (savedInstanceState == null) {
            mSlidingState = SLIDING_STATE_BOTTOM;
        }

        ScrollUtils.addOnGlobalLayoutListener(mInterceptionLayout, new Runnable() {
            @Override
            public void run() {
//                if (mFab != null) {
//                    ViewHelper.setTranslationX(mFab, mTitle.getWidth() - mFabMargin - mFab.getWidth());
//                    ViewHelper.setTranslationY(mFab, ViewHelper.getX(mTitle) - (mFab.getHeight() / 2));
//                }
                changeSlidingState(SLIDING_STATE_MIDDLE, false);
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // All the related temporary states can be restored by slidingState
        mSlidingState = savedInstanceState.getInt(STATE_SLIDING_STATE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SLIDING_STATE, mSlidingState);
        super.onSaveInstanceState(outState);
    }

    protected abstract int getLayoutResId();

    protected abstract S createScrollable();

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private TouchInterceptionFrameLayout.TouchInterceptionListener mInterceptionListener = new TouchInterceptionFrameLayout.TouchInterceptionListener() {
        @Override
        public boolean shouldInterceptTouchEvent(MotionEvent ev, boolean moving, float diffX, float diffY) {
            final int minInterceptionLayoutY = -mIntersectionHeight;
            return minInterceptionLayoutY < (int) ViewHelper.getY(mInterceptionLayout)
                    || (moving && mScrollable.getCurrentScrollY() - diffY < 0);
        }

        @Override
        public void onDownMotionEvent(MotionEvent ev) {
            mScrollYOnDownMotion = mScrollable.getCurrentScrollY();
            mInitialY = ViewHelper.getTranslationY(mInterceptionLayout);
        }

        @Override
        public void onMoveMotionEvent(MotionEvent ev, float diffX, float diffY) {
            mMoved = true;
            float translationY = ViewHelper.getTranslationY(mInterceptionLayout) - mScrollYOnDownMotion + diffY;
            if (translationY < -mIntersectionHeight) {
                translationY = -mIntersectionHeight;
            } else if (getScreenHeight() - mHeaderBarHeight < translationY) {
                translationY = getScreenHeight() - mHeaderBarHeight;
            }

            slideTo(translationY, true);

            mMovedDistanceY = ViewHelper.getTranslationY(mInterceptionLayout) - mInitialY;
        }

        @Override
        public void onUpOrCancelMotionEvent(MotionEvent ev) {
            if (!mMoved) {
                // Invoke slide animation only on header view
                Rect outRect = new Rect();
                mHeader.getHitRect(outRect);
                if (outRect.contains((int) ev.getX(), (int) ev.getY())) {
                   // slideOnClick();
                }
            } else {
                stickToAnchors();
            }
            mMoved = false;
        }
    };

    private void changeSlidingState(final int slidingState, boolean animated) {
        mSlidingState = slidingState;
        float translationY = 0;
        switch (slidingState) {
            case SLIDING_STATE_TOP:
                translationY = 0;
                break;
            case SLIDING_STATE_BELLOW_TOP:
                translationY = getAnchorYView();
                //Toast.makeText(this,translationY+"",Toast.LENGTH_LONG).show();
                break;
            case SLIDING_STATE_MIDDLE:
                translationY = getAnchorYImage();
                break;
            case SLIDING_STATE_BOTTOM:
                translationY = getAnchorYBottom();
                break;
        }
        if (animated) {
            slideWithAnimation(translationY);
        } else {
            slideTo(translationY, false);
        }
    }

//    private void slideOnClick() {
//        float translationY = ViewHelper.getTranslationY(mInterceptionLayout);
//        if (translationY == getAnchorYBottom()) {
//            changeSlidingState(SLIDING_STATE_MIDDLE, true);
//        } else if (translationY == getAnchorYImage()) {
//            changeSlidingState(SLIDING_STATE_BOTTOM, true);
//        } else if (translationY == getAnchorYView()) {
//            changeSlidingState(SLIDING_STATE_BELLOW_TOP, true);
//        }
//    }

    private void stickToAnchors() {
        // Slide to some points automatically
        if (0 < mMovedDistanceY) {
            // Sliding down
            if (mSlidingSlop < mMovedDistanceY) {
                // Sliding down to an anchor
                if (getAnchorYView() < ViewHelper.getTranslationY(mInterceptionLayout)) {
                    changeSlidingState(SLIDING_STATE_MIDDLE, true);
                } else {
                    changeSlidingState(SLIDING_STATE_BELLOW_TOP, true);
                }
            } else {
                // Sliding up(back) to an anchor
                if (getAnchorYView() < ViewHelper.getTranslationY(mInterceptionLayout)) {
                    changeSlidingState(SLIDING_STATE_MIDDLE, true);
                } else {
                    changeSlidingState(SLIDING_STATE_MIDDLE, true);
                }
            }
        } else if (mMovedDistanceY < 0) {
            // Sliding up
            if (mMovedDistanceY < -mSlidingSlop) {
                // Sliding up to an anchor
                if (getAnchorYView() < ViewHelper.getTranslationY(mInterceptionLayout)) {
                    changeSlidingState(SLIDING_STATE_BELLOW_TOP, true);

                } else if(getAnchorYView() > ViewHelper.getTranslationY(mInterceptionLayout)){
                    changeSlidingState(SLIDING_STATE_TOP, true);
                }
//                else {
//                    changeSlidingState(SLIDING_STATE_TOP,true);
//                }
            } else {
                // Sliding down(back) to an anchor
                if (getAnchorYImage() < ViewHelper.getTranslationY(mInterceptionLayout)) {
                    changeSlidingState(SLIDING_STATE_MIDDLE, true);
                } else {
                    changeSlidingState(SLIDING_STATE_MIDDLE, true);
                }
            }
        }
    }

    private void slideTo(float translationY, final boolean animated) {
        ViewHelper.setTranslationY(mInterceptionLayout, translationY);

        if (translationY < 0) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mInterceptionLayout.getLayoutParams();
            lp.height = (int) -translationY + getScreenHeight();
            mInterceptionLayout.requestLayout();
        }

        // Translate title
        float hiddenHeight = translationY < 0 ? -translationY : 0;
        ViewHelper.setTranslationY(mTitle, Math.min(mIntersectionHeight, (mHeaderBarHeight + hiddenHeight - mActionBarSize) / 2));

        // Translate image
        float imageAnimatableHeight = getScreenHeight() - mHeaderBarHeight;
        float imageTranslationScale = imageAnimatableHeight / (imageAnimatableHeight - mImageView.getHeight());
        float imageTranslationY = Math.max(0, imageAnimatableHeight - (imageAnimatableHeight - translationY) * imageTranslationScale);
        ViewHelper.setTranslationY(mImageView, imageTranslationY);


        /**
         * Show/hide FAB
         */
//        if (ViewHelper.getTranslationY(mInterceptionLayout) < mFlexibleSpaceImageHeight) {
//            hideFab(animated);
//        } else {
//            if (animated) {
//                ViewPropertyAnimator.animate(mToolbar).scaleY(0).setDuration(200).start();
//            } else {
//                ViewHelper.setScaleY(mToolbar, 0);
//            }
//            hideFab(animated);
//        }
        if (ViewHelper.getTranslationY(mInterceptionLayout) <= mFlexibleSpaceImageHeight) {
            if (animated) {
                ViewPropertyAnimator.animate(mToolbar).scaleY(1).setDuration(200).start();
            } else {
                ViewHelper.setScaleY(mToolbar, 1);
            }
            //mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, mToolbarColor));
        }

        //changeToolbarTitleVisibility();
       // changeHeaderBarColorAnimated(animated);
        changeHeaderOverlay();
    }

    private void slideWithAnimation(float toY) {
        float layoutTranslationY = ViewHelper.getTranslationY(mInterceptionLayout);
        if (layoutTranslationY != toY) {
            ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mInterceptionLayout), toY).setDuration(200);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    slideTo((float) animation.getAnimatedValue(), true);
                }
            });
            animator.start();
        }
    }

//    private void changeToolbarTitleVisibility() {
//        if (ViewHelper.getTranslationY(mInterceptionLayout) <= mIntersectionHeight) {
//            if (ViewHelper.getAlpha(mToolbarTitle) != 1) {
//                ViewPropertyAnimator.animate(mToolbarTitle).cancel();
//                ViewPropertyAnimator.animate(mToolbarTitle).alpha(1).setDuration(200).start();
//            }
//        } else if (ViewHelper.getAlpha(mToolbarTitle) != 0) {
//            ViewPropertyAnimator.animate(mToolbarTitle).cancel();
//            ViewPropertyAnimator.animate(mToolbarTitle).alpha(0).setDuration(200).start();
//        } else {
//            ViewHelper.setAlpha(mToolbarTitle, 0);
//        }
//    }

    private void changeHeaderBarColorAnimated(boolean animated) {
        if (mHeaderColorIsChanging) {
            return;
        }
        boolean shouldBeWhite = getAnchorYBottom() == ViewHelper.getTranslationY(mInterceptionLayout);
        if (!mHeaderIsAtBottom && !mHeaderColorChangedToBottom && shouldBeWhite) {
            mHeaderIsAtBottom = true;
            mHeaderIsNotAtBottom = false;
            if (animated) {
                ValueAnimator animator = ValueAnimator.ofFloat(0, 1).setDuration(100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float alpha = (float) animation.getAnimatedValue();
                        mHeaderColorIsChanging = (alpha != 1);
                        changeHeaderBarColor(alpha);
                    }
                });
                animator.start();
            } else {
                changeHeaderBarColor(1);
            }
        } else if (!mHeaderIsNotAtBottom && !shouldBeWhite) {
            mHeaderIsAtBottom = false;
            mHeaderIsNotAtBottom = true;
            if (animated) {
                ValueAnimator animator = ValueAnimator.ofFloat(1, 0).setDuration(100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float alpha = (float) animation.getAnimatedValue();
                        mHeaderColorIsChanging = (alpha != 0);
                        changeHeaderBarColor(alpha);
                    }
                });
                animator.start();
            } else {
                changeHeaderBarColor(0);
            }
        }
    }

    private void changeHeaderBarColor(float alpha) {
        mHeaderBar.setBackgroundColor(ScrollUtils.mixColors(mColorPrimary, Color.WHITE, alpha));
        //mTitle.setTextColor(ScrollUtils.mixColors(Color.WHITE, Color.BLACK, alpha));
        mHeaderColorChangedToBottom = (alpha == 1);
    }

    private void changeHeaderOverlay() {
        final float translationY = ViewHelper.getTranslationY(mInterceptionLayout);
        if (translationY <= mToolbar.getHeight() - mSlidingHeaderBlueSize) {
            mHeaderOverlay.setVisibility(View.VISIBLE);
            mHeaderFlexibleSpace.getLayoutParams().height = (int) (mToolbar.getHeight() - mSlidingHeaderBlueSize - translationY);
            mHeaderFlexibleSpace.requestLayout();
            mHeaderOverlay.requestLayout();
        } else {
            mHeaderOverlay.setVisibility(View.INVISIBLE);
        }
    }

//    private void showFab(boolean animated) {
//        if (mFab == null) {
//            return;
//        }
//        if (!mFabIsShown) {
//            if (animated) {
//                ViewPropertyAnimator.animate(mFab).cancel();
//                ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
//            } else {
//                ViewHelper.setScaleX(mFab, 1);
//                ViewHelper.setScaleY(mFab, 1);
//            }
//            mFabIsShown = true;
//        } else {
//            // Ensure that FAB is shown
//            ViewHelper.setScaleX(mFab, 1);
//            ViewHelper.setScaleY(mFab, 1);
//        }
//    }
//
//    private void hideFab(boolean animated) {
//        if (mFab == null) {
//            return;
//        }
//        if (mFabIsShown) {
//            if (animated) {
//                ViewPropertyAnimator.animate(mFab).cancel();
//                ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
//            } else {
//                ViewHelper.setScaleX(mFab, 0);
//                ViewHelper.setScaleY(mFab, 0);
//            }
//            mFabIsShown = false;
//        } else {
//            // Ensure that FAB is hidden
//            ViewHelper.setScaleX(mFab, 0);
//            ViewHelper.setScaleY(mFab, 0);
//        }
//    }

    private float getAnchorYBottom() {
        return getScreenHeight() - mHeaderBarHeight;
    }

    private float getAnchorYImage() {
        return mImageView.getHeight();
    }

    private float getAnchorYView() {
        return topLayout.getHeight() + mToolbar.getHeight();
    }
}
