package com.wzhy.simplecustomviews.colorfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.wzhy.simplecustomviews.R;

/**
 * Created by Administrator on 2017-9-11 0011.
 */

public class ViewPorterDuffFilter extends View {

    private Paint mPaint;

    private Bitmap mBmp;

    private int x, y;

    private PorterDuffColorFilter mPorterDuffColorFilter;
    private int mScreenWidth;
    private int mScreenHeight;

    public ViewPorterDuffFilter(Context context) {
        this(context, null);
    }

    public ViewPorterDuffFilter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPorterDuffFilter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        initPaint();
        //初始化资源
        initRsrc(context);
    }

    private void initPaint() {
        //实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置颜色过滤
        mPorterDuffColorFilter = new PorterDuffColorFilter(0xFFFF0000, PorterDuff.Mode.DARKEN);
        mPaint.setColorFilter(mPorterDuffColorFilter);
    }

    private void initRsrc(Context context) {
        mBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_1);

        /**
         * 计算位图绘制时左上角的坐标使其位于屏幕中心
         * 屏幕坐标x轴向左偏移位图一半的宽度
         * 屏幕坐标y轴向上偏移位图一般的高度
         */
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        x = mScreenWidth / 2 - mBmp.getWidth() / 2;
        y = mScreenHeight / 2 - mBmp.getHeight() / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制位图
        canvas.drawBitmap(mBmp, x, y, mPaint);

    }
}
