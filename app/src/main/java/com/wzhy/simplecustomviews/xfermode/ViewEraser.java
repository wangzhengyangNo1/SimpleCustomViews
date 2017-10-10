package com.wzhy.simplecustomviews.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.wzhy.simplecustomviews.R;

/**
 * Created by Administrator on 2017-9-12 0012.
 */

public class ViewEraser extends View {

    public static final int MINI_MOVE_DST = 5;
    private int mScreenW;
    private int mScreenH;
    private Paint mPaint;
    private PorterDuffXfermode porterDuffXfermode;
    private Path mPath;
    private Bitmap fgBitmap;
    private Canvas mCanvas;
    private Bitmap bgBitmap;

    private float preX, preY;



    public ViewEraser(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewEraser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        prepareObject(context);

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


    private void prepareObject(Context context) {
        //实例化路径对象
        mPath = new Path();
        //实例化画笔并开启抗锯齿和抗抖动。
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        //设置画笔透明度为0是关键！我们要让绘制的路径是透明的，然后让该路径与前景的底色混合“抠”出绘制路径
        mPaint.setARGB(128, 255, 0, 0);


        //实例化混合模式，
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        //设置混合模式为DST_IN
        mPaint.setXfermode(porterDuffXfermode);

        //设置画笔风格为描边
        mPaint.setStyle(Paint.Style.STROKE);

        //设置路径结合处样式
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        //设置笔触类型
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        //设置描边宽度
        mPaint.setStrokeWidth(50);

        //生成前景图Bitmap
        fgBitmap = Bitmap.createBitmap(mScreenW, mScreenH, Bitmap.Config.ARGB_8888);

        //将其注入画布
        mCanvas = new Canvas(fgBitmap);

        //绘制画布背景为中性灰
        mCanvas.drawColor(0xFF808080);

        //获取背景底图Bitmap
        bgBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_1);

        //缩放背景底图至屏幕大小
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, mScreenW, mScreenH, true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        canvas.drawBitmap(bgBitmap, 0, 0, null);

        //绘制前景
        canvas.drawBitmap(fgBitmap, 0, 0, null);

        /**
         * 这里要注意：canvas和mCanvas是两个不同的画布对象
         * 当我们在屏幕上移动手指绘制路径时，会把路径通过
         */

        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 获取当前事件位置坐标
         */
        float x = event.getX();
        float y = event.getY();


        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();//手指接触屏幕，重置路径
                mPath.moveTo(x, y);
                preX = x;
                preY = y;
                break;

            case MotionEvent.ACTION_MOVE://手指移动时连接路径
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);

                if (dx >= MINI_MOVE_DST || dy >= MINI_MOVE_DST) {
                    mPath.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
                    preX = x;
                    preY = y;
                }
                break;
        }


        invalidate();//重绘视图
        return true;
    }
}
