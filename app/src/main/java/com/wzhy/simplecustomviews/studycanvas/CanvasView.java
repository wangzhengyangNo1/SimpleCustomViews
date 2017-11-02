package com.wzhy.simplecustomviews.studycanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017-11-1 0001.
 */

public class CanvasView extends View {

//    private Paint mPaint;
    private Rect mRect;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
      /*  mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);*/

        mRect = new Rect(0, 0, 500, 500);

        //boolean intersect = mRect.intersect(250, 250, 750, 750);

        mRect.union(250, 250, 750, 750);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        /*canvas.clipRect(0, 0, 500, 500);
        canvas.drawColor(Color.RED);
        canvas.drawCircle(500, 600, 150, mPaint);*/

        canvas.clipRect(mRect);
        canvas.drawColor(Color.RED);
    }
}
