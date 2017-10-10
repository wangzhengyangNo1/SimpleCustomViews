package com.wzhy.simplecustomviews.colorfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.wzhy.simplecustomviews.R;

/**
 * Created by Administrator on 2017-9-11 0011.
 */

public class ViewLightColorFilter extends View {

    private Paint mPaint;
    private Bitmap mPic1;
    private int mScreenWidth;
    private int mScreenHeight;
    private int x;
    private int y;
    private LightingColorFilter mLightingColorFilter;

    public ViewLightColorFilter(Context context) {
        this(context, null);
    }

    public ViewLightColorFilter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewLightColorFilter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initRsrc(context);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);


        mLightingColorFilter = new LightingColorFilter(0xFFFF00FF, 0x00000000);

        mPaint.setColorFilter(mLightingColorFilter);
    }

    private void initRsrc(Context context) {
        //获取位图
        mPic1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_1);
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
        x = mScreenWidth / 2 - mPic1.getWidth() / 2;
        y = mScreenHeight / 2 - mPic1.getHeight() / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制位图
        canvas.drawBitmap(mPic1, x, y, mPaint);
    }
}
