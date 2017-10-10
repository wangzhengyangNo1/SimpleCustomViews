package com.wzhy.simplecustomviews.maskfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.wzhy.simplecustomviews.R;

/**
 * Created by Administrator on 2017-9-21 0021.
 *
 */

public class BlurMaskFilterView extends View {

    private int mScreenW;
    private int mScreenH;
    private Paint mPaint;
    private Bitmap mSrcBitmap;
    private Bitmap mShadowBitmap;
    private int x;
    private int y;

    public BlurMaskFilterView(Context context) {
        this(context, null);
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        initRes(context);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.DKGRAY);

        //设置画笔遮罩滤镜
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));

        //取消硬件加速（使用SOFTWARE）
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

        //获取位图
        mSrcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_1);

        //获取位图的Alpha通道图
        mShadowBitmap = mSrcBitmap.extractAlpha();

        /**位图位于图片中心时的左上角坐标*/
        x = mScreenW / 2 - mSrcBitmap.getWidth() / 2;
        y = mScreenH / 2 - mSrcBitmap.getHeight() / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //先绘制阴影
        canvas.drawBitmap(mShadowBitmap, x, y, mPaint);

        //再绘制位图
        canvas.drawBitmap(mSrcBitmap, x, y, null);
    }
}
