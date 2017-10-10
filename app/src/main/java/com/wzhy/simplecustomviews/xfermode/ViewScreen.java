package com.wzhy.simplecustomviews.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.wzhy.simplecustomviews.R;

/**
 * Created by Administrator on 2017-9-12 0012.
 */

public class ViewScreen extends View {


    private Paint mPaint;
    private PorterDuffXfermode porterDuffXfermode;
    private int mScreenW;
    private int mScreenH;

    private int x, y;
    private Bitmap mBmpSrc;

    public ViewScreen(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewScreen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initPaint();
        initRsrc(context);
    }

    private void init(Context context) {
        /**
         * 获取屏幕宽高
         */
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //WindowManager windowManager = ((Activity) context).getWindow().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenW = metrics.widthPixels;
        mScreenH = metrics.heightPixels;
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //实例化混合模式
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
    }

    private void initRsrc(Context context) {
        mBmpSrc = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_dst);

        //计算图片位于屏幕中心时，左上角的坐标
        x = mScreenW / 2 - mBmpSrc.getWidth() / 2;
        y = mScreenH / 2 - mBmpSrc.getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        //将绘制操作保存到新的图层（更官方的说法是离屏缓存）
        int sc = canvas.saveLayer(0, 0, mScreenW, mScreenH, null, Canvas.ALL_SAVE_FLAG);

        //先绘制一层带透明度的颜色
        canvas.drawColor(0xcc1c093e);
        //设置混合模式
        mPaint.setXfermode(porterDuffXfermode);

        //绘制源图
        canvas.drawBitmap(mBmpSrc, x, y, mPaint);

        //还原混合模式
        mPaint.setXfermode(null);
        //还原画布
        canvas.restoreToCount(sc);
    }
}
