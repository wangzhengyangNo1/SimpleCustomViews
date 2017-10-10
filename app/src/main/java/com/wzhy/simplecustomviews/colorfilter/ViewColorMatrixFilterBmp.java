package com.wzhy.simplecustomviews.colorfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.wzhy.simplecustomviews.R;

/**
 * Created by Administrator on 2017-9-11 0011.
 */

public class ViewColorMatrixFilterBmp extends View {


    private Paint mPaint;
    private Bitmap mPic1;
    private int mScreenWidth;
    private int mScreenHeight;
    private int x;
    private int y;
    private ColorMatrixColorFilter mColorFilter;

    public ViewColorMatrixFilterBmp(Context context) {
        this(context, null);
    }

    public ViewColorMatrixFilterBmp(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewColorMatrixFilterBmp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        initRsrc(context);
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

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


        mColorFilter = new ColorMatrixColorFilter(getColorMatrix());
    }

    @NonNull
    private ColorMatrix getColorMatrix() {
        ColorMatrix colorMatrix = null;

//        colorMatrix = new ColorMatrix(new float[]{
//                0.5f,   0f,   0f,   0f,   0f,
//                0f, 0.5f,   0f,   0f,   0f,
//                0f,   0f, 0.5f,   0f,   0f,
//                0f,   0f,   0f,   1f,   0f
//        });

//        colorMatrix = new ColorMatrix(new float[]{
//                0.33f, 0.59f, 0.11f,    0f,   0f,
//                0.33f, 0.59f, 0.11f,    0f,   0f,
//                0.33f, 0.59f, 0.11f,    0f,   0f,
//                   0f,    0f,    0f,    1f,   0f
//        });

//        colorMatrix = new ColorMatrix(new float[]{
//                -1f, 0f, 0f, 1f, 1f,
//                0f, -1f, 0f, 1f, 1f,
//                0f, 0f, -1f, 1f, 1f,
//                0f, 0f, 0f, 1f, 0f
//        });

//        colorMatrix = new ColorMatrix(new float[]{
//                0f, 0f, 1f, 0f, 0f,
//                0f, 1f, 0f, 0f, 0f,
//                1f, 0f, 0f, 0f, 0f,
//                0f, 0f, 0f, 1f, 0f
//        });

//        colorMatrix = new ColorMatrix(new float[]{
//                0.393f, 0.769f, 0.189f, 0f, 0f,
//                0.349f, 0.686f, 0.168f, 0f, 0f,
//                0.272f, 0.543f, 0.131f, 0f, 0f,
//                0f, 0f, 0f, 1f, 0f
//        });

//        colorMatrix = new ColorMatrix(new float[]{
//                1.5f, 1.5f, 1.5f, 0f, -1f,
//                1.5f, 1.5f, 1.5f, 0f, -1f,
//                1.5f, 1.5f, 1.5f, 0f, -1f,
//                0f, 0f, 0f, 1f, 0f
//        });

//        colorMatrix = new ColorMatrix(new float[]{
//                1.438F, -0.122F, -0.016F, 0, -0.03F,
//                -0.062F, 1.378F, -0.016F, 0, 0.05F,
//                -0.062F, -0.122F, 1.483F, 0, -0.02F,
//                0, 0, 0, 1, 0
//        });

        colorMatrix = new ColorMatrix();
//        colorMatrix.setRotate(0, 90);
//        colorMatrix.setRotate(1, 90);
//        colorMatrix.setRotate(2, 90);
        colorMatrix.setSaturation(0.6f);//饱和度

        return colorMatrix;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColorFilter(mColorFilter);

        canvas.drawBitmap(mPic1, x, y, mPaint);

    }

}
