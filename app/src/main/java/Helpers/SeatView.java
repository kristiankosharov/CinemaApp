package Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import Models.Seat;
import mycinemaapp.com.mycinemaapp.R;

/**
 * Source:http://stackoverflow.com/questions/13864480/android-how-to-circular-zoom-magnify-part-of-image
 * http://stackoverflow.com/questions/11442934/magnifying-part-of-the-canvas-when-touched
 */
public class SeatView extends View {

    private ShapeDrawable mSeatDrawable;
    private static int width = 10;
    private static int height = 10;
    private static int x = 13;
    private static int y = 13;


    int w = 20, h = 20;

//    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ticket);


    Matrix matrix = new Matrix();
    Paint shaderPaint = new Paint();
    boolean zooming;
    boolean isFirst = true;
    static final PointF zoomPos = new PointF(0, 0);

    BitmapShader shader = null;

    List<Seat> seatList = new ArrayList<Seat>();

    public SeatView(Context context, List<Seat> seatList) {
        super(context);
        this.seatList = seatList;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Seat seat : seatList) {
            switch (seat.getState()) {
                case 0:
                    drawCanvas(canvas, seat, getResources().getColor(R.color.seat_empty));
                    break;
                case 1:
                    drawCanvas(canvas, seat, getResources().getColor(R.color.seat_selected));
                    break;
                case 2:
                    break;
            }
        }

        if (zooming) {
            matrix.reset();
            matrix.postScale(2f, 2f, zoomPos.x, zoomPos.y);
            shaderPaint.getShader().setLocalMatrix(matrix);
//            Toast.makeText(getContext(), "draw", Toast.LENGTH_SHORT).show();
            canvas.drawCircle(zoomPos.x, zoomPos.y, 100, shaderPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isFirst) {
            shader = new BitmapShader(getBitmapFromView(this), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            isFirst = false;
        }
        int action = event.getAction();
        shaderPaint.setShader(shader);

        Log.d("touchhhhhhhhhhhhhhhh", "touch" + zoomPos.x + "," + zoomPos.y);
        matrix.reset();
        matrix.postScale(2f, 2f);
        matrix.postTranslate(-zoomPos.x, -zoomPos.y);
        shader.setLocalMatrix(matrix);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                zoomPos.x = event.getX();
                zoomPos.y = event.getY();
                zooming = true;
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                zooming = false;
                this.invalidate();
                break;
        }

        return true;
    }


    private void drawCanvas(Canvas canvas, Seat seat, int color) {
        int row = seat.getRow();
        int column = seat.getColumn();

        int new_x = x * row;
        int new_y = y * column;

        mSeatDrawable = new ShapeDrawable(new RectShape());
        mSeatDrawable.getPaint().setColor(color);
        mSeatDrawable.setBounds(new_x, new_y, new_x + width, new_y + height);
        mSeatDrawable.draw(canvas);
    }

    /**
     * http://stackoverflow.com/questions/5536066/convert-view-to-bitmap-on-android
     *
     * @param view
     * @return bitmap
     */
    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
}