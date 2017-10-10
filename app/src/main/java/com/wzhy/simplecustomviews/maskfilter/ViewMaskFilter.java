package com.wzhy.simplecustomviews.maskfilter;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017-9-21 0021.
 */

public class ViewMaskFilter extends View {

    private Paint mPaint;
    private int mScreenW;
    private int mScreenH;

    private static final int RECT_SIZE = 500;

    private Rect mRect;


    public ViewMaskFilter(Context context) {
        this(context, null);
    }

    public ViewMaskFilter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewMaskFilter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        initRes(context);

    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFF603811);

        //设置画笔遮罩滤镜
        mPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID));

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private void initRes(Context context) {
        /**
         * 获取屏幕宽高
         */
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //WindowManager windowManager = ((Activity) context).getWindow().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenW = metrics.widthPixels;
        mScreenH = metrics.heightPixels;

        /**计算图形绘制到屏幕中心时，图形的位置坐标*/
        int left = mScreenW / 2 - RECT_SIZE / 2;
        int top = mScreenH / 2 - RECT_SIZE / 2;
        int right = mScreenW / 2 + RECT_SIZE / 2;
        int bottom = mScreenH / 2 + RECT_SIZE / 2;

        mRect = new Rect(left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(Color.GRAY);

        //画一个矩形
        canvas.drawRect(mRect, mPaint);
    }
}
