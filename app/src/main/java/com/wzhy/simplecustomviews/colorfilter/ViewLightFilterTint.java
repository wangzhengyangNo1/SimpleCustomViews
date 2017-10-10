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

public class ViewLightFilterTint extends View {

    private boolean isClicked = false;
    private Paint mPaint;
    private LightingColorFilter mLightingColorFilter;
    private Bitmap mBmp;
    private int mScreenWidth;
    private int mScreenHeight;
    private int x;
    private int y;

    public ViewLightFilterTint(Context context) {
        this(context, null);
    }

    public ViewLightFilterTint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewLightFilterTint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        initPaint();
        //初始化资源
        initRsrc(context);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isClicked = !isClicked;
                LightingColorFilter lightingColorFilter = isClicked ? mLightingColorFilter : null;
                mPaint.setColorFilter(lightingColorFilter);// isClicked = true，设置颜色过滤为黄色，isClicked = false，还原。
                invalidate();//记得重绘

            }
        });


    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mLightingColorFilter = new LightingColorFilter(0xFFFFFFFF, 0x00666600);
    }

    /**
     * 初始画资源
     * @param context
     */
    private void initRsrc(Context context) {
        mBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_star);

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
        canvas.drawBitmap(mBmp, x, y, mPaint);
    }
}
