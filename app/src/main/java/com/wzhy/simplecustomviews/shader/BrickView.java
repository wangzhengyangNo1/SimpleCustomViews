package com.wzhy.simplecustomviews.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wzhy.simplecustomviews.MyApp;
import com.wzhy.simplecustomviews.R;

/**
 * 聚光灯砖墙效果
 * Created by Administrator on 2017-9-29 0029.
 */

public class BrickView extends View {

    private Paint mStrokePaint;
    private Paint mFillPaint;
    private BitmapShader mBitmapShader;
    private float posX;
    private float posY;

    public BrickView(Context context) {
        this(context, null);
    }

    public BrickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    /**
     *  初始化
     */
    private void init() {
        /*实例化描边画笔，并设置参数*/
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mStrokePaint.setColor(0xFF000000);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(5);

        //实例化填充画笔
        mFillPaint = new Paint();

        /**生成BitmapShader*/
        Bitmap bitmap = BitmapFactory.decodeResource(MyApp.getAppContext().getResources(), R.drawable.img_brick_wall);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mFillPaint.setShader(mBitmapShader);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**手指移动时，获取触摸点坐标，并刷新视图*/
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            posX = event.getX();
            posY = event.getY();
            invalidate();
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置画布背景
        canvas.drawColor(Color.DKGRAY);
        /**绘制圆和描边*/
        canvas.drawCircle(posX, posY, 100, mFillPaint);
        canvas.drawCircle(posX, posY, 100, mStrokePaint);
    }
}
