package com.wzhy.simplecustomviews.colorfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017-9-11 0011.
 */

public class ViewColorMatrixFilter extends View {


    private Paint mPaint;
    private Bitmap mPic1;
    private int mScreenWidth;
    private int mScreenHeight;
    private int x;
    private int y;

    public ViewColorMatrixFilter(Context context) {
        this(context, null);
    }

    public ViewColorMatrixFilter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewColorMatrixFilter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        /**
         * 画笔样式分三种：
         *    1.Paint.Style.STROKE：描边
         *    2.Paint.Style.FILL_AND_STROKE：描边并填充
         *    3.Paint.Style.FILL：填充
         */
        //设置画笔样式为描边
        mPaint.setStyle(Paint.Style.FILL);

        //设置画笔颜色为自定义颜色
        mPaint.setColor(Color.argb(255, 255, 128, 103));

        /*
        设置描边的粗细，单位：px。
        注意：当setStrokeWidth(0)的时候，描边的宽度并不为0，而是只占一个像素。
        */
        mPaint.setStrokeWidth(10);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);

    }

    private void drawCircle(Canvas canvas) {
        //绘制圆形。
        int width = getWidth();
        int height = getHeight();

//        int left = getLeft();
//        int top = getTop();
        int centerX = width / 2;
        int centerY = height / 2;

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0.5f,   0f,   0f,   0f,   0f,
                  0f, 0.5f,   0f,   0f,   0f,
                  0f,   0f, 0.5f,   0f,   0f,
                  0f,   0f,   0f,   1f,   0f
        });

        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        canvas.drawCircle(centerX, centerY, 200, mPaint);
    }
}
