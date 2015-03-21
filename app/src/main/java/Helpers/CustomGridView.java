package Helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by kristian on 15-3-19.
 */
public class CustomGridView extends GridView {

    private boolean expanded = false;
    private int height;
    private Activity activity;

    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // HACK! TAKE THAT ANDROID!

        if (isExpanded()) {
            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.
            int expandSpec = MeasureSpec.makeMeasureSpec(View.MEASURED_SIZE_MASK,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }


    public void setHeightElement(int height) {
        this.height = height;
    }

    public int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        return height;
    }

    public void setContext(Activity activity) {
        this.activity = activity;
    }

//    public void setGridViewHeightBasedOnChildren(int columns) {
//        ListAdapter listAdapter = this.getAdapter();
//        if (listAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = 0;
//        int items = listAdapter.getCount();
//        int rows = 0;
//
//        View listItem = listAdapter.getView(0, null, this);
//        listItem.measure(0, 0);
//        totalHeight = listItem.getMeasuredHeight();
//
//        float x = 1;
//        if (items > columns) {
//            x = items / columns;
//            rows = (int) (x + 1);
//            totalHeight *= rows;
//        }
//
//        ViewGroup.LayoutParams params = this.getLayoutParams();
//        params.height = totalHeight;
//        this.setLayoutParams(params);
//
//    }


    public int getHeightElement() {
        return height;
    }
}
