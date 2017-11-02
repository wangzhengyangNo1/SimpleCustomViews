package com.wzhy.simplecustomviews.studypath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017-11-1 0001.
 */

public class PathView extends View {

    private Paint mPaint;
    private Path mPath;

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);

        /*实例化画笔并设置属性*/
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(10);

        //实例化路径
        mPath = new Path();

        //lineToAndClosePath();

        //贝塞尔曲线
        bzlCurve();

    }

    private void bzlCurve() {

        mPath.moveTo(100, 100);

        //链接路径到点
        mPath.quadTo(200, 200, 300, 100);
    }

    private void lineToAndClosePath() {
        mPath.moveTo(100, 100);
        //链接路径到点[100, 100]
        mPath.lineTo(300, 100);
        mPath.lineTo(400, 200);
        mPath.lineTo(200, 200);
        //mPath.lineTo(100, 100);
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制路径
        canvas.drawPath(mPath, mPaint);
    }
}
