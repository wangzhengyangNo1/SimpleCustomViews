package com.wzhy.simplecustomviews.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wzhy.simplecustomviews.R;


/**
 * Created by Administrator on 2017-9-13 0013.
 * {@link android.widget.ProgressBar}
 */

public class CustomProgressBar extends View {

    private Paint mPaint;
    private int mMax;//最大值默认为100
    private float mRadius;

    private int mProgress;//进度
    private RectF mRect;


    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);
        mMax = ta.getInt(R.styleable.CustomProgressBar_max, 100);
        mProgress = ta.getInt(R.styleable.CustomProgressBar_progress, 0);
        mRadius = ta.getDimension(R.styleable.CustomProgressBar_radius, 5);
        ta.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.GRAY);
        mRect = new RectF();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float strokeWidth = mPaint.getStrokeWidth();
        mRect.set(strokeWidth / 2, strokeWidth / 2, getWidth() - strokeWidth / 2, getHeight() - strokeWidth / 2);
        canvas.drawRoundRect(mRect, mRadius, mRadius, mPaint);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mRect.set(strokeWidth, strokeWidth, (getWidth() - 2 * strokeWidth) * mProgress / mMax + strokeWidth, getHeight() - strokeWidth);
        canvas.drawRoundRect(mRect, mRadius - strokeWidth / 2, mRadius - strokeWidth / 2, mPaint);
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {

        if (progress < 0) {
            progress = 0;
        }
        if (progress > mMax) {
            progress = mMax;
        }
        mProgress = progress;
        invalidate();
    }


    public int getProgress() {
        return mProgress;
    }

    public void setRadius(int radius) {
        mRadius = radius;
        invalidate();
    }


    private int getRadius() {
        return (int) mRadius;
    }


}
