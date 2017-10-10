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

import static android.R.attr.maxWidth;
import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by Administrator on 2017-9-12 0012.
 */

public class ViewDstOut extends View {
    private int mScreenW;
    private int mScreenH;
    private Paint mPaint;
    private PorterDuffXfermode mXfermode;
    private Bitmap mBmpSrc;

    private int x, y;

    public ViewDstOut(Context context) {
        this(context, null);
    }

    public ViewDstOut(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDstOut(Context context, AttributeSet attrs, int defStyleAttr) {
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

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //实例化混合模式
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    /**
     * 初始化资源
     */
    private void initRsrc(Context context) {
        //获取位图
        mBmpSrc = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_src);

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

        //先绘制一层颜色
        canvas.drawColor(0xFF8f66da);
        //设置混合模式
        mPaint.setXfermode(mXfermode);

        //再绘制源图
        canvas.drawBitmap(mBmpSrc, x, y, mPaint);

        //还原混合模式
        mPaint.setXfermode(null);
        //还原画布
        canvas.restoreToCount(sc);
    }
}
