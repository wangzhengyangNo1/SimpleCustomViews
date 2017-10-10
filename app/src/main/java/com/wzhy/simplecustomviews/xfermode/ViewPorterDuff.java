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


/**
 * Created by Administrator on 2017-9-11 0011.
 */

public class ViewPorterDuff extends View {

    /**
     * PorterDuff模式常量
     * 更改不同的模式进行测试
     */
    private static final PorterDuff.Mode MODE = PorterDuff.Mode.XOR;
    private static final int RECT_SIZE_SMALL = 400;//上方左右两个示例渐变正方形的尺寸大小
    private static final int RECT_SIZE_LARGE= 800;//中间测试渐变正方形的尺寸大小

    private Paint mPaint;
    private int mScreenWidth;
    private int mScreenHeight;

    private PorterDuffBO porterDuffBO;//ViewPorterDuff的业务对象
    private PorterDuffXfermode porterDuffXfermode;//图形混合模式

    private int srcX, srcY;//左上方正方形的原点坐标
    private int dstX, dstY;//右上方正方形的原点坐标
    private int rectX, rectY;   //中间正方形的原点坐标

    public ViewPorterDuff(Context context) {
        this(context, null);
    }

    public ViewPorterDuff(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPorterDuff(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        initRsrc(context);
        //实例化业务对象
        porterDuffBO = new PorterDuffBO();
        //实例化混合模式
        porterDuffXfermode = new PorterDuffXfermode(MODE);

        //计算坐标
        calculate(context);
    }

    private void initPaint() {
        //实例化画笔并设置抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void initRsrc(Context context) {
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

    }

    private void calculate(Context context) {
        //左上方正方形原点坐标
        srcX = 0;
        srcY = 0;

        //计算右上方正方形原点坐标
        dstX = mScreenWidth - RECT_SIZE_SMALL;
        dstY = 0;

        //计算中间正方形的原点坐标
        rectX = mScreenWidth / 2 - RECT_SIZE_LARGE / 2;
        rectY = RECT_SIZE_SMALL + (mScreenHeight - RECT_SIZE_SMALL) / 2 - RECT_SIZE_LARGE / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置画布颜色为黑色，以便更好的观察
        canvas.drawColor(Color.BLACK);

        //设置业务对象尺寸值，计算生成左右上方的渐变方形
        porterDuffBO.setSize(RECT_SIZE_SMALL);

        //画出上方左右正方形，左边为src，右边为dst
        canvas.drawBitmap(porterDuffBO.initSrcBitmap(), srcX, srcY, mPaint);
        canvas.drawBitmap(porterDuffBO.initDstBitmap(), dstX, dstY, mPaint);

        /**
         * 将绘制操作保存到新的图层（更官方的说法就是离屏缓存）
         */
        int sc = canvas.saveLayer(0, 0, mScreenWidth, mScreenHeight, null, Canvas.ALL_SAVE_FLAG);

        //重新设置业务对象尺寸值，计算生成中间的渐变正方形
        porterDuffBO.setSize(RECT_SIZE_LARGE);

        //先绘制dst目标图
        canvas.drawBitmap(porterDuffBO.initDstBitmap(), rectX, rectY, mPaint);

        //设置混合模式
        mPaint.setXfermode(porterDuffXfermode);

        //再绘制src源图
        canvas.drawBitmap(porterDuffBO.initSrcBitmap(), rectX, rectY, mPaint);

        //还原混合模式
        mPaint.setXfermode(null);

        //还原画布
        canvas.restoreToCount(sc);
    }
}
