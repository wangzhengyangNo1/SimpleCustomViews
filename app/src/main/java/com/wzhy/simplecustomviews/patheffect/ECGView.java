package com.wzhy.simplecustomviews.patheffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 *
 * 使用path实现心电图
 * Created by Administrator on 2017-9-28 0028.
 */

public class ECGView extends View {


    private Paint mPaint;//画笔
    private Path mPath;//路径
    private int transX, moveX;
    private boolean isCanvasMove;//画布是否需要平移

    private int screenW, screenH;//屏幕宽高
    private float x, y;//路径初始坐标
    private float initScreenW;//屏幕的初始宽度
    private float initX;//初始X轴坐标


    public ECGView(Context context) {
        this(context, null);
    }

    public ECGView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ECGView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        //初始化画笔并设置属性
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(7, 0, 0, Color.GREEN);

        //初始化路径
        mPath = new Path();
        transX = 0;
        isCanvasMove = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.i("onSizeChanged", "w = " + w + ", h = " + h);
        /**获取屏幕宽高*/
        screenW = w;
        screenH = h;
        /**设置起点坐标*/
        x = 0;
        y = (screenH / 2) + (screenH / 4) + (screenH / 10);

        //屏幕初始宽度
        initScreenW = screenW;

        //初始x轴坐标
        initX = (screenW / 2) + (screenW / 4);

        moveX = (screenW / 24);

        mPath.moveTo(x, y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);


        mPath.lineTo(x, y);

        //向左平移画布
        canvas.translate(-transX, 0);

        //计算坐标
        calCoors();

        //绘制路径
        canvas.drawPath(mPath, mPaint);
        invalidate();

    }

    /**
     * 计算坐标
     */
    private void calCoors() {
        if (isCanvasMove == true) {
            transX += 4;
        }

        Log.i("xxx", "calCoors: x = " + x);
        if (x < initX) {
            x += 8;
            return;
        }
        if (x < initX + moveX) {
            x += 2;
            y -= 8;
            return;
        }
        if (x < initX + (moveX * 2)) {
            x += 2;
            y += 14;
            return;
        }
        if (x < initX + (moveX * 3)) {
            x += 2;
            y -= 12;
            return;
        }
        if (x < initX + (moveX * 4)) {
            x += 2;
            y += 6;
            return;
        }

        if (x < initScreenW) {
            x += 8;
        } else {
            isCanvasMove = true;
            initX += initScreenW - moveX * 8;
        }
    }
}
