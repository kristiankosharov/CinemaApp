package Helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import Models.SaveTempMovieModel;

/**
 * Created by kristian on 15-3-19.
 */
public class CustomGridView extends GridView {

    boolean expanded = false;

    public CustomGridView(Context paramContext) {
        super(paramContext);
    }

    public CustomGridView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public CustomGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void onMeasure(int paramInt1, int paramInt2) {
        if (isExpanded()) {
            super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec((SaveTempMovieModel.getHeight() ), MeasureSpec.AT_MOST));
            getLayoutParams().height = getMeasuredHeight();
            return;
        }
        super.onMeasure(paramInt1, paramInt2);
    }

    public void setExpanded(boolean paramBoolean) {
        this.expanded = paramBoolean;
    }
}
