package Helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by kristian on 15-3-17.
 * Source: http://stackoverflow.com/questions/20042570/edittext-line-spacing-increase-issue-and-cursor-position-w-r-t-line
 * <p/>
 * Look and: http://stackoverflow.com/questions/11641997/how-to-change-edittext-cursor-height
 */
public class CustomEditText extends EditText {
    private Rect mRect;
    private Paint mPaint;

    // we need this constructor for LayoutInflater
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.BLACK); //SET YOUR OWN COLOR HERE
        setMinLines(15);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int line_height = getLineHeight();

        int count = height / line_height;
        if (getLineCount() > count) {
            count = getLineCount();
        }
        Rect r = mRect;
        Paint paint = mPaint;
        int baseline = getLineBounds(0, r);



        for (int i = 0; i < count; i++) {
            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
            baseline += getLineHeight();//next line
        }

        // Finishes up by calling the parent method
        super.onDraw(canvas);
    }
}
